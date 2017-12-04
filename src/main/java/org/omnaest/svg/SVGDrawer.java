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
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.omnaest.svg.component.AbstractSVGElementConsumer;
import org.omnaest.svg.component.BoundedAreaImpl;
import org.omnaest.svg.component.SVGElementAndRawElementConsumer;
import org.omnaest.svg.component.TranslationAreaImpl;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGAnkerElement;
import org.omnaest.svg.model.RawSVGDefinition;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.svg.model.RawSVGRoot;
import org.omnaest.svg.model.RawSVGStyle;
import org.omnaest.svg.utils.XMLHelper;

/**
 * @see SVGUtils
 * @author omnaest
 */
public class SVGDrawer extends AbstractSVGElementConsumer<SVGDrawer>
{
	private RawSVGRoot			rawSVGRoot;
	private List<SVGElement>	elements				= new ArrayList<>();
	private AtomicInteger		embedReloadTimer		= new AtomicInteger(0);
	private AtomicBoolean		enableCSSForAnkerLinks	= new AtomicBoolean(false);

	public static interface GenericTranslationArea<TA extends GenericTranslationArea<TA>> extends SVGElementAndRawElementConsumer<TA>
	{
		TA withTranslationY(double y);

		TA withTranslationX(double x);

		TA withRelativeTranslationX(double x);

		TA withRelativeTranslationY(double y);
	}

	public static interface TranslationArea extends GenericTranslationArea<TranslationArea>
	{
	}

	public static interface BoundedArea extends GenericTranslationArea<BoundedArea>
	{
		BoundedArea withHeight(double height);

		BoundedArea withWidth(double width);

		BoundedArea withRelativeHeight(double relativeHeight);

		BoundedArea withRelativeWidth(double relativeWidth);

		double getRawWidth();

		double getRawHeight();

		BoundedArea withScalingWidth(double scalingWidth);

		BoundedArea withScalingHeight(double scalingHeight);

		BoundedArea withScalingWidth(Double scalingWidth);

		BoundedArea withScalingHeight(Double scalingHeight);

		double getWidth();

		double getHeight();

		BoundedArea newSubArea();

		BoundedArea withRelativeSizedBorder(double relativeBorderSize);

		BoundedArea withBorder(double borderSize);

		List<BoundedArea> asVerticalSlices(int numberOfSlices);

		List<BoundedArea> asHorizontalSlices(int numberOfSlices);
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

		public void writeToFile(File file) throws IOException
		{
			if (file != null)
			{
				FileUtils.writeStringToFile(file, this.getAsSVG(), "utf-8");
			}
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
		return this	.renderAsResult()
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

	public SVGDrawer modifyRawElements(UnaryOperator<Stream<RawSVGElement>> rawElementModifier)
	{
		this.rawSVGRoot.setElements(rawElementModifier	.apply(this.rawSVGRoot	.getElements()
																				.stream())
														.collect(Collectors.toList()));

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
	}

	public SVGDrawer withScreenDimensions(ResolutionProvider displayResolution)
	{
		return this.withScreenDimensions(displayResolution.getWidth(), displayResolution.getHeight());
	}

	public SVGDrawer withScreenDimensions(int width, int height)
	{
		this.rawSVGRoot	.setWidth(width + "px")
						.setHeight(height + "px");
		return this;
	}

	public TranslationArea newTranslationArea()
	{
		return new TranslationAreaImpl(this, () -> this.getWidth(), () -> this.getHeight());
	}

	public BoundedArea newBoundedArea()
	{
		return new BoundedAreaImpl(this, () -> this.getWidth(), () -> this.getHeight());
	}

	public double getWidth()
	{
		return this.rawSVGRoot	.getParsedViewBox()
								.getWidth();
	}

	public double getHeight()
	{
		return this.rawSVGRoot	.getParsedViewBox()
								.getHeight();
	}
}
