/*******************************************************************************
 * Copyright 2021 Danny Kunz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.svg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.omnaest.svg.component.AbstractSVGElementConsumer;
import org.omnaest.svg.component.BoundedAreaImpl;
import org.omnaest.svg.component.SVGElementAndRawElementConsumer;
import org.omnaest.svg.component.TranslationAreaImpl;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGAnkerElement;
import org.omnaest.svg.model.RawSVGDefinition;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.svg.model.RawSVGElementWithChildren;
import org.omnaest.svg.model.RawSVGRoot;
import org.omnaest.svg.model.RawSVGStyle;
import org.omnaest.utils.ListUtils;
import org.omnaest.utils.XMLHelper;

/**
 * @see SVGUtils
 * @author omnaest
 */
public class SVGDrawer extends AbstractSVGElementConsumer<SVGDrawer>
{
    private RawSVGRoot       rawSVGRoot;
    private List<SVGElement> elements               = new ArrayList<>();
    private AtomicInteger    embedReloadTimer       = new AtomicInteger(0);
    private AtomicBoolean    enableCSSForAnkerLinks = new AtomicBoolean(false);

    public static interface ParentAccessor
    {
        public SVGElementAndRawElementConsumer<?> getConsumer();

        public double getWidth();

        public double getHeight();

        public double getTranslationX();

        public double getTranslationY();

        public ParentAccessor getParent();

        double getScaleX();

        double getScaleY();
    }

    public static interface GenericTranslationArea<TA extends GenericTranslationArea<TA>> extends SVGElementAndRawElementConsumer<TA>
    {
        /**
         * Translated the whole area in x direction
         * 
         * @see #withRelativeTranslationX(double)
         * @see #withTranslationY(double)
         * @param x
         * @return
         */
        TA withTranslationX(double x);

        /**
         * Translated the whole area in y direction
         * 
         * @see #withRelativeTranslationY(double)
         * @see #withTranslationX(double)
         * @param y
         * @return
         */
        TA withTranslationY(double y);

        /**
         * Translates the whole area in x direction in relation to the width of the (parent) area
         * 
         * @see #withTranslationX(double)
         * @see #withRelativeTranslationY(double)
         * @param x
         * @return
         */
        TA withRelativeTranslationX(double x);

        /**
         * Translates the whole area in y direction in realtion to the height of the (parent) area
         * 
         * @see #withTranslationY(double)
         * @see #withRelativeTranslationX(double)
         * @param y
         * @return
         */
        TA withRelativeTranslationY(double y);

        double getRawTranslationY();

        double getRawTranslationX();

        ParentAccessor getParent();
    }

    public static interface TranslationArea extends GenericTranslationArea<TranslationArea>
    {
    }

    public static interface BoundedArea extends GenericTranslationArea<BoundedArea>, ParentAccessor
    {
        public static interface CoordinatesTranslator
        {
            public double translateX(double x);

            public double translateY(double y);

            public CoordinatesTranslator relatedTo(BoundedArea boundedArea);

        }

        /**
         * Defines the height of the {@link BoundedArea}
         * 
         * @see #withHeight(double)
         * @see #withRelativeHeight(double)
         * @param height
         * @return
         */
        BoundedArea withHeight(double height);

        /**
         * Defines the width of the {@link BoundedArea}
         * 
         * @see #withWidth(double)
         * @see #withRelativeWidth(double)
         * @param width
         * @return
         */
        BoundedArea withWidth(double width);

        /**
         * Similar to {@link #withHeight(double)} using the relative height regarding to the parent
         * 
         * @see #withHeight(double)
         * @see #withRelativeWidth(double)
         * @param relativeHeight
         * @return
         */
        BoundedArea withRelativeHeight(double relativeHeight);

        /**
         * Similar to {@link #withWidth(double)} using the relative width regarding to the parent
         * 
         * @see #withWidth(double)
         * @see #withRelativeHeight(double)
         * @param relativeWidth
         * @return
         */
        BoundedArea withRelativeWidth(double relativeWidth);

        /**
         * Returns the absolute width as seen within the parent area
         * 
         * @see #getRawHeight()
         * @see #getWidth()
         * @return
         */
        double getRawWidth();

        /**
         * Returns the absolute height as seen within the parent area
         * 
         * @see #getRawWidth()
         * @see #getHeight()
         * @return
         */
        double getRawHeight();

        /**
         * Returns the width of the current {@link BoundedArea} which can be used for {@link SVGElement}s added to it
         * 
         * @see #getRawWidth()
         * @see #getHeight()
         * @return
         */
        @Override
        double getWidth();

        /**
         * Return the height of the current {@link BoundedArea} which can be used for {@link SVGElement}s added to it.
         * 
         * @see #getRawHeight()
         * @see #getWidth()
         * @return
         */
        @Override
        double getHeight();

        /**
         * Defines the fixed width which is used then on by added {@link SVGElement}s. This implies a scaling operation between {@link #getWidth()} to
         * {@link #getRawWidth()}
         * 
         * @see #getWidth()
         * @see #getRawWidth()
         * @see #withScalingHeight(double)
         * @param scalingWidth
         * @return
         */
        BoundedArea withScalingWidth(double scalingWidth);

        /**
         * Defines the fixed height which is used then on by added {@link SVGElement}s. This implies a scaling operation between {@link #getHeight()} and
         * {@link #getRawHeight()}
         * 
         * @see #getHeight()
         * @see #getRawHeight()
         * @see #withScalingWidth(double)
         * @param scalingHeight
         * @return
         */
        BoundedArea withScalingHeight(double scalingHeight);

        BoundedArea withScalingWidth(Double scalingWidth);

        BoundedArea withScalingHeight(Double scalingHeight);

        /**
         * Returns a new sub {@link BoundedArea} with its own properties
         * 
         * @return
         */
        BoundedArea newSubArea();

        /**
         * @see #withPadding(double)
         * @param relativePaddingSize
         * @return
         */
        BoundedArea withRelativeSizedPadding(double relativePaddingSize);

        /**
         * Defines a padding which wraps the content, similar to the padding in CSS
         * 
         * @param paddingSize
         * @return
         */
        BoundedArea withPadding(double paddingSize);

        /**
         * Returns vertical slices (horizontally cut)
         * 
         * @param numberOfSlices
         * @return
         */
        List<BoundedArea> asVerticalSlices(int numberOfSlices);

        /**
         * Similar to {@link #asVerticalSlices(int)} with given proportions
         * 
         * @param numberOfSlices
         * @return
         */
        List<BoundedArea> asVerticalSlices(DoubleStream proportions);

        /**
         * Similar to {@link #asVerticalSlices(DoubleStream)}
         * 
         * @param proportions
         * @return
         */
        List<BoundedArea> asVerticalSlices(double... proportions);

        /**
         * Returns horizontal slices (vertically cut)
         * 
         * @param numberOfSlices
         * @return
         */
        List<BoundedArea> asHorizontalSlices(int numberOfSlices);

        CoordinatesTranslator getCoordinatesTranslator();

        /**
         * Merges two {@link BoundedArea}s into a single one, which represents the rectangle spanning over both {@link BoundedArea}s
         * 
         * @param boundedArea
         * @return
         */
        BoundedArea coverageMergeWith(BoundedArea boundedArea);

        List<BoundedArea> asHorizontalSlices(DoubleStream proportions);

        List<BoundedArea> asHorizontalSlices(double... proportions);
    }

    public static class SVGRenderResult
    {
        private RawSVGRoot rawSVGRoot;

        public SVGRenderResult(RawSVGRoot rawSVGRoot)
        {
            super();
            this.rawSVGRoot = rawSVGRoot;
        }

        @Override
        public String toString()
        {
            return this.getAsSVG();
        }

        public RawSVGRoot getRawSVGRoot()
        {
            return this.rawSVGRoot;
        }

        public String getAsSVG()
        {
            return XMLHelper.serialize(this.rawSVGRoot);
        }

        public SVGRenderResult writeToFile(File file) throws IOException
        {
            if (file != null)
            {
                FileUtils.writeStringToFile(file, this.getAsSVG(), "utf-8");
            }
            return this;
        }

        public SVGRenderResult writeToFileSilently(File file)
        {
            try
            {
                return this.writeToFile(file);
            }
            catch (IOException e)
            {
                // do nothing
                return this;
            }
        }

        /**
         * Writes the svg to a {@link File} using the given mapper {@link Function}
         * 
         * @param file
         * @param byteMapper
         * @return
         * @throws IOException
         */
        public SVGRenderResult writeToFileByteMapped(File file, Function<String, byte[]> byteMapper) throws IOException
        {
            if (file != null)
            {
                FileUtils.writeByteArrayToFile(file, byteMapper.apply(this.getAsSVG()));
            }
            return this;
        }

    }

    protected SVGDrawer(int width, int height)
    {
        super();

        this.rawSVGRoot = this.createRawSVGRoot(width, height);
    }

    protected SVGDrawer(int originX, int originY, int width, int height)
    {
        super();

        this.rawSVGRoot = this.createRawSVGRoot(originX, originY, width, height);
    }

    protected SVGDrawer(RawSVGRoot rawSVGRoot)
    {
        super();

        this.rawSVGRoot = rawSVGRoot;
    }

    public boolean isEmbedReloadTimer()
    {
        return this.embedReloadTimer.get() > 0;
    }

    public SVGDrawer setEmbedReloadTimer(boolean embedReloadTimer)
    {
        this.embedReloadTimer.set(1000);
        return this;
    }

    public SVGDrawer setEmbedReloadTimer(int time, TimeUnit timeUnit)
    {
        this.embedReloadTimer.set((int) timeUnit.toMillis(time));
        return this;
    }

    /**
     * Renders the svg xml into a {@link String}
     */
    public String render()
    {
        return this.renderAsResult()
                   .getAsSVG();
    }

    /**
     * Renders the svg into a {@link SVGRenderResult}
     */
    public SVGRenderResult renderAsResult()
    {
        RawSVGRoot svgRoot = this.rawSVGRoot;

        Map<String, Object> context = new HashMap<>();

        List<RawSVGElement> svgElements = new ArrayList<>();
        if (svgRoot.getElements() != null)
        {
            svgElements.addAll(svgRoot.getElements());
        }
        svgRoot.setElements(svgElements);
        for (SVGElement element : this.elements)
        {
            RawSVGElement rawSVGElement = element.render();
            this.visitRenderElement(rawSVGElement, svgElements, context);
            svgElements.add(rawSVGElement);
        }

        if (this.isEmbedReloadTimer())
        {
            StringBuilder sb = new StringBuilder();

            //sb.append("<![CDATA[");
            int reloadInterval = this.embedReloadTimer.get();
            sb.append("    setTimeout(function(){ location.reload();  }, " + reloadInterval + ");");
            //sb.append("]]>");

            this.rawSVGRoot.addScript(sb.toString());
        }

        return new SVGRenderResult(svgRoot);

    }

    private void visitRenderElement(RawSVGElement rawSVGElement, List<RawSVGElement> svgElements, Map<String, Object> context)
    {
        if (rawSVGElement instanceof RawSVGAnkerElement)
        {
            if (this.enableCSSForAnkerLinks.get())
            {
                this.addCSSForAnkerLinks(svgElements, context);
            }
        }

    }

    private RawSVGRoot createRawSVGRoot(int width, int height)
    {
        return this.createRawSVGRoot(0, 0, width, height);
    }

    private RawSVGRoot createRawSVGRoot(int originX, int originY, int width, int height)
    {
        RawSVGRoot svgRoot;
        svgRoot = new RawSVGRoot();
        svgRoot.setVersion("1.1");
        svgRoot.setBaseProfile("full");
        svgRoot.setWidth(width + "px");
        svgRoot.setHeight(width + "px");
        svgRoot.setViewBox("" + originX + " " + originY + " " + width + " " + height);
        return svgRoot;
    }

    public RawSVGRoot getRawSVGRoot()
    {
        return this.rawSVGRoot;
    }

    /**
     * @see SVGElement
     * @param element
     * @return
     */
    @Override
    public SVGDrawer add(SVGElement element)
    {
        this.elements.add(element);
        return this;
    }

    public SVGDrawer visitAndModifyRawElements(UnaryOperator<Stream<RawSVGElement>> rawElementModifier)
    {
        this.rawSVGRoot.setElements(rawElementModifier.apply(this.rawSVGRoot.getElements()
                                                                            .stream())
                                                      .collect(Collectors.toList()));

        return this;
    }

    public SVGDrawer visitRawElements(Consumer<Stream<RawSVGElement>> rawElementModifier)
    {
        rawElementModifier.accept(this.rawSVGRoot.getElements()
                                                 .stream());
        return this;
    }

    public SVGDrawer visitRawElementsTransitively(Consumer<Stream<RawSVGElement>> rawElementModifier)
    {
        rawElementModifier.accept(this.rawSVGRoot.getElements()
                                                 .stream()
                                                 .flatMap(new Function<RawSVGElement, Stream<RawSVGElement>>()
                                                 {
                                                     @Override
                                                     public Stream<RawSVGElement> apply(RawSVGElement element)
                                                     {
                                                         Stream<RawSVGElement> retval = Stream.of(element);
                                                         if (element instanceof RawSVGElementWithChildren)
                                                         {
                                                             List<RawSVGElement> children = ((RawSVGElementWithChildren) element).getElements();
                                                             retval = Stream.concat(retval, ListUtils.defaultIfNull(children)
                                                                                                     .stream()
                                                                                                     .collect(Collectors.toList())
                                                                                                     .stream()
                                                                                                     .flatMap(e -> this.apply(e)));
                                                         }
                                                         return retval;
                                                     }
                                                 }));
        return this;
    }

    @Override
    public SVGDrawer addRawElement(RawSVGElement rawElement)
    {
        List<RawSVGElement> elements = new ArrayList<>();
        if (this.rawSVGRoot.getElements() != null)
        {
            elements.addAll(this.rawSVGRoot.getElements());
        }
        if (rawElement != null)
        {
            elements.add(rawElement);
        }
        this.rawSVGRoot.setElements(elements);
        return this;
    }

    public SVGDrawer enableCSSForAnkerLinks()
    {
        this.enableCSSForAnkerLinks.set(true);
        return this;
    }

    public void addCSSForAnkerLinks(List<RawSVGElement> svgElements, Map<String, Object> context)
    {
        String contextAlreadyAddedMarkerKey = "cssForAnkerAdded";
        if (!context.containsKey(contextAlreadyAddedMarkerKey))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("a use {fill: #09c; stroke: #09c;}\n");
            sb.append("a:hover use {fill: skyblue; stroke: skyblue;}\n");
            sb.append("a text {fill: #09c; text-decoration: underline;}\n");
            sb.append("a:hover text {fill: skyblue; }\n");
            svgElements.add(new RawSVGDefinition().addElement(new RawSVGStyle().setContent(sb.toString())));
            context.put(contextAlreadyAddedMarkerKey, true);
        }
    }

    public static interface ResolutionProvider
    {

        public int getWidth();

        public int getHeight();

        public static ResolutionProvider of(int width, int height)
        {
            return new CustomResolutionProvider(height, width);
        }
    }

    public static class CustomResolutionProvider implements ResolutionProvider
    {
        private final int height;
        private final int width;

        public CustomResolutionProvider(int height, int width)
        {
            this.height = height;
            this.width = width;
        }

        @Override
        public int getWidth()
        {
            return this.width;
        }

        @Override
        public int getHeight()
        {
            return this.height;
        }
    }

    public SVGDrawer withScreenDimensions(ResolutionProvider displayResolution)
    {
        if (displayResolution != null)
        {
            this.withScreenDimensions(displayResolution.getWidth(), displayResolution.getHeight());
        }
        return this;
    }

    public SVGDrawer withScreenDimensions(int width, int height)
    {
        this.rawSVGRoot.setWidth(width + "px")
                       .setHeight(height + "px");
        return this;
    }

    public TranslationArea newTranslationArea()
    {
        return new TranslationAreaImpl(this.asParentAccessor());
    }

    public BoundedArea newBoundedArea()
    {
        return new BoundedAreaImpl(this.asParentAccessor());
    }

    private ParentAccessor asParentAccessor()
    {
        return new ParentAccessor()
        {
            @Override
            public double getWidth()
            {
                return SVGDrawer.this.getWidth();
            }

            @Override
            public double getTranslationY()
            {
                return 0;
            }

            @Override
            public double getTranslationX()
            {
                return 0;
            }

            @Override
            public double getHeight()
            {
                return SVGDrawer.this.getHeight();
            }

            @Override
            public SVGElementAndRawElementConsumer<?> getConsumer()
            {
                return SVGDrawer.this;
            }

            @Override
            public ParentAccessor getParent()
            {
                //root returns null
                return null;
            }

            @Override
            public double getScaleX()
            {
                return 1.0;
            }

            @Override
            public double getScaleY()
            {
                return 1.0;
            }

        };
    }

    public double getWidth()
    {
        return this.rawSVGRoot.getParsedViewBox()
                              .getWidth();
    }

    public double getHeight()
    {
        return this.rawSVGRoot.getParsedViewBox()
                              .getHeight();
    }

    public static enum SVGColor
    {
        RED, GREEN, BLUE, YELLOW, GREY, LIGHTGREEN, MAGENTA, DARKBLUE, DARKGREEN, DARKRED, DARKGREY, WHITE;

        public String asSVGColorKey()
        {
            return this.name()
                       .toLowerCase();
        }
    }

    public static interface DrawerMatrixCell
    {
        public DrawerMatrixCell fillWith(String color);

        public DrawerMatrixCell fillWith(SVGColor svgColor);

        public DrawerMatrixCell fillWith(SVGColor green, double opacity);

        public DrawerMatrixCell fillWith(String color, double opacity);

        public DrawerMatrixCell setText(String text);
    }

    public static interface DrawerMatrix
    {
        public DrawerMatrixCell getCell(int x, int y);
    }

    public DrawerMatrix newMatrix(int horizontalSlices, int verticalSlices)
    {
        double cellWidth = this.getWidth() / horizontalSlices;
        double cellHeight = this.getHeight() / verticalSlices;
        return new DrawerMatrix()
        {
            @Override
            public DrawerMatrixCell getCell(int x, int y)
            {
                int cellX = (int) Math.round(x * cellWidth);
                int cellY = (int) Math.round(y * cellHeight);

                return new DrawerMatrixCell()
                {
                    @Override
                    public DrawerMatrixCell fillWith(String color)
                    {
                        double opacity = 1.0;
                        return this.fillWith(color, opacity);
                    }

                    @Override
                    public DrawerMatrixCell fillWith(SVGColor svgColor)
                    {
                        return this.fillWith(svgColor.asSVGColorKey());
                    }

                    @Override
                    public DrawerMatrixCell fillWith(String color, double opacity)
                    {
                        SVGDrawer.this.add(new SVGRectangle(cellX, cellY, (int) Math.round(cellWidth), (int) Math.round(cellHeight)).setStrokeColor(color)
                                                                                                                                    .setFillColor(color)
                                                                                                                                    .setFillOpacity(opacity)
                                                                                                                                    .setStrokeOpacity(opacity));
                        return this;
                    }

                    @Override
                    public DrawerMatrixCell fillWith(SVGColor svgColor, double opacity)
                    {
                        return this.fillWith(svgColor.asSVGColorKey(), opacity);
                    }

                    @Override
                    public DrawerMatrixCell setText(String text)
                    {
                        SVGDrawer.this.add(new SVGText(cellX, cellY, text).setFontSize((int) cellHeight));
                        return this;
                    }
                };
            }
        };
    }

}
