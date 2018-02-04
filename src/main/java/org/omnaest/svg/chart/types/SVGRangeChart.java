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
package org.omnaest.svg.chart.types;

import java.util.Locale;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.SVGDrawer.SVGRenderResult;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.chart.RangeChart;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;

public class SVGRangeChart implements RangeChart
{
    private SVGDrawer drawer;

    private double min;
    private double max;

    private DrawBox labelDrawBox;
    private DrawBox bodyDrawBox;
    private DrawBox scaleDrawBox;

    public static class DrawBox
    {
        private BoundedArea drawer;

        private int left;
        private int top;
        private int right;
        private int bottom;

        public DrawBox(BoundedArea drawer, int left, int top, int right, int bottom)
        {
            super();
            this.drawer = drawer;
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public SVGLine addLine(int x1, int y1, int x2, int y2)
        {
            SVGLine svgLine = new SVGLine(x1 + this.left, y1 + this.top, x2 + this.left, y2 + this.top);
            this.drawer.add(svgLine);
            return svgLine;
        }

        public SVGRectangle addRectangle(int x, int y, int width, int height)
        {
            SVGRectangle svgRectangle = new SVGRectangle(x + this.left, y + this.top, width, height);
            this.drawer.add(svgRectangle);
            return svgRectangle;
        }

        public SVGText addText(int x, int y, String text)
        {
            SVGText svgText = new SVGText(x + this.left, y + this.top, text);
            this.drawer.add(svgText);
            return svgText;
        }

        public SVGCircle addCircle(int x, int y, int r)
        {
            SVGCircle svgCircle = new SVGCircle(x + this.left, y + this.top, r);
            this.drawer.add(svgCircle);
            return svgCircle;

        }

        public int getLeft()
        {
            return this.left;
        }

        public int getTop()
        {
            return this.top;
        }

        public int getRight()
        {
            return this.right;
        }

        public int getBottom()
        {
            return this.bottom;
        }

        public int getHeight()
        {
            return this.bottom - this.top;
        }

        public int getWidth()
        {
            return this.right - this.left;
        }

    }

    public SVGRangeChart(int width, int height)
    {
        super();
        this.drawer = SVGUtils.getDrawer(width, height)
                              .withScreenDimensions(width, height);

        BoundedArea boundedArea = this.drawer.newBoundedArea();
        this.initByBoundedArea(boundedArea);
    }

    public SVGRangeChart(BoundedArea boundedArea)
    {
        super();

        this.initByBoundedArea(boundedArea);
    }

    private void initByBoundedArea(BoundedArea boundedArea)
    {
        double bodyRelativeHeight = 0.5;
        int width = (int) boundedArea.getWidth();
        int height = (int) boundedArea.getHeight();
        this.labelDrawBox = new DrawBox(boundedArea, 0, 0, (int) (0.2 * width), (int) (height * bodyRelativeHeight));
        this.bodyDrawBox = new DrawBox(boundedArea, this.labelDrawBox.getRight(), 0, (int) (width * 0.95), (int) (height * bodyRelativeHeight));
        this.scaleDrawBox = new DrawBox(boundedArea, this.labelDrawBox.getRight(), (int) (height * bodyRelativeHeight), (int) (width * 0.95), height);
    }

    @Override
    public String render()
    {
        return this.drawer.render();
    }

    @Override
    public SVGRenderResult renderAsResult()
    {
        return this.drawer.renderAsResult();
    }

    @Override
    public RangeChart setHorizontalScale(double min, double max)
    {
        this.min = min;
        this.max = max;

        return this;
    }

    @Override
    public RangeChart addRange(Color color, double min, double max, double center, double fadeOutOpacity)
    {

        this.bodyDrawBox.addRectangle((int) (-this.bodyDrawBox.getHeight() * 0.1), 0,
                                      (int) (this.bodyDrawBox.getWidth() + (this.bodyDrawBox.getHeight() * 0.1 * 2)), this.bodyDrawBox.getHeight())
                        .setFillOpacity(0.0)
                        .setStrokeColor(Color.BLACK.name());

        {
            double minRelativeX = this.calculateHorizontalRelativePosition(min);
            double maxRelativeX = this.calculateHorizontalRelativePosition(max);
            double centerRelativeX = this.calculateHorizontalRelativePosition(center);

            double partitions = 1000;
            double relativeXRange = maxRelativeX - minRelativeX;
            for (int ii = 0; ii < partitions; ii++)
            {
                double partitionMinRelativeX = minRelativeX + relativeXRange * ii / partitions;
                double partitionMaxRelativeX = minRelativeX + relativeXRange * (ii + 1) / partitions;

                double relativeOpacity = Math.signum(centerRelativeX - partitionMinRelativeX) > 0.0
                        ? Math.abs(centerRelativeX - partitionMinRelativeX) / Math.abs(centerRelativeX - minRelativeX)
                        : Math.abs(centerRelativeX - partitionMinRelativeX) / Math.abs(maxRelativeX - centerRelativeX);
                double opacity = fadeOutOpacity + (1 - fadeOutOpacity) * (1.0 - relativeOpacity);

                int x = (int) (partitionMinRelativeX * this.bodyDrawBox.getWidth());
                int width = (int) (partitionMaxRelativeX * this.bodyDrawBox.getWidth()) - x;
                int y = (int) (0.05 * this.bodyDrawBox.getHeight());
                int height = (int) (0.9 * this.bodyDrawBox.getHeight());
                this.bodyDrawBox.addRectangle(x, y, width, height)
                                .setFillColor(color.name())
                                .setFillOpacity(opacity)
                                .setStrokeOpacity(0.0);
            }
        }

        return this;
    }

    @Override
    public RangeChart addRangeBox(Color color, double min, double max, double opacity)
    {
        double minRelativeX = this.calculateHorizontalRelativePosition(min);
        double maxRelativeX = this.calculateHorizontalRelativePosition(max);

        int height = (int) (0.9 * this.bodyDrawBox.getHeight());
        int y = (int) (0.05 * this.bodyDrawBox.getHeight());
        int reducedY = (int) (y + height * 0.25);
        int reducedHeight = (int) (height * 0.5);

        this.bodyDrawBox.addRectangle((int) (minRelativeX * this.bodyDrawBox.getWidth()), reducedY,
                                      (int) ((maxRelativeX - minRelativeX) * this.bodyDrawBox.getWidth()), reducedHeight)
                        .setStrokeColor(color.name())
                        .setStrokeOpacity(1.0)
                        .setStrokeWidth(3)
                        .setFillColor(color.name())
                        .setFillOpacity(opacity);

        return this;
    }

    private double calculateHorizontalRelativePosition(double value)
    {
        double range = this.max - this.min;
        return (value - this.min) / range;
    }

    @Override
    public RangeChart setLabel(String label)
    {
        this.labelDrawBox.addText(5, this.labelDrawBox.getHeight() / 2, label)
                         .setColor(Color.BLACK.name())
                         .setFontSize((int) (this.labelDrawBox.getHeight() * 0.3));
        return this;
    }

    @Override
    public RangeChart addPoint(Color color, double value)
    {
        double relativeXPosition = this.calculateHorizontalRelativePosition(value);

        int x = (int) (this.bodyDrawBox.getWidth() * relativeXPosition);
        int y = (int) (this.bodyDrawBox.getHeight() * 0.5);
        int r = (int) (this.bodyDrawBox.getHeight() * 0.3);
        this.bodyDrawBox.addCircle(x, y, r)
                        .setFillColor(color.name());
        return this;
    }

    @Override
    public RangeChart addScalePoint(double value, ScalePosition position)
    {
        double relativeX = this.calculateHorizontalRelativePosition(value);
        int someLeft = (int) (relativeX * this.scaleDrawBox.getWidth());
        int y = (int) (this.scaleDrawBox.getHeight() * 0.3);
        this.scaleDrawBox.addLine(someLeft, 0, someLeft, (int) (y * 0.75))
                         .setStrokeWidth(10);

        int fontSize = (int) (this.scaleDrawBox.getHeight() * 0.3);
        String text = String.format(Locale.US, "%3.2f", value);

        if (ScalePosition.LOW.equals(position))
        {
            y = (int) (y * 1.5);
        }

        this.scaleDrawBox.addText(someLeft - fontSize * text.length() / 4, (int) (1.75 * y), text)
                         .setFontSize(fontSize);

        return this;
    }

}
