package org.omnaest.svg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.svg.model.RawSVGRoot;
import org.omnaest.svg.utils.XMLHelper;

/**
 * @see SVGUtils
 * @author omnaest
 */
public class SVGDrawer
{
	private RawSVGRoot			rawSVGRoot;
	private List<SVGElement>	elements			= new ArrayList<>();
	private AtomicInteger		embedReloadTimer	= new AtomicInteger(0);

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
			return rawSVGRoot;
		}

		public String getAsSVG()
		{
			return XMLHelper.serialize(this.rawSVGRoot);
		}

		public void writeToFile(File file) throws IOException
		{
			FileUtils.writeStringToFile(file, getAsSVG(), "utf-8");
		}

	}

	protected SVGDrawer(int width, int height)
	{
		super();

		this.rawSVGRoot = createRawSVGRoot(width, height);
	}

	protected SVGDrawer(RawSVGRoot rawSVGRoot)
	{
		super();

		this.rawSVGRoot = rawSVGRoot;
	}

	public boolean isEmbedReloadTimer()
	{
		return embedReloadTimer.get() > 0;
	}

	public SVGDrawer setEmbedReloadTimer(boolean embedReloadTimer)
	{
		this.embedReloadTimer.set(1000);
		;
		return this;
	}

	public SVGDrawer setEmbedReloadTimer(int time, TimeUnit timeUnit)
	{
		this.embedReloadTimer.set((int) timeUnit.toMillis(time));
		;
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

		List<RawSVGElement> svgElements = new ArrayList<>();
		if (svgRoot.getElements() != null)
		{
			svgElements.addAll(svgRoot.getElements());
		}
		svgRoot.setElements(svgElements);
		for (SVGElement element : this.elements)
		{
			svgElements.add(element.render());
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

	private RawSVGRoot createRawSVGRoot(int width, int height)
	{
		RawSVGRoot svgRoot;
		svgRoot = new RawSVGRoot();
		svgRoot.setVersion("1.1");
		svgRoot.setBaseProfile("full");
		svgRoot.setWidth("1200px");
		svgRoot.setHeight("700px");
		svgRoot.setViewBox("0 0 " + width + " " + height);
		return svgRoot;
	}

	public RawSVGRoot getRawSVGRoot()
	{
		return rawSVGRoot;
	}

	/**
	 * @see SVGElement
	 * @param element
	 * @return
	 */
	public SVGDrawer add(SVGElement element)
	{
		this.elements.add(element);
		return this;
	}

	public SVGDrawer addAll(Iterable<SVGElement> elements)
	{
		if (elements != null)
		{
			for (SVGElement element : elements)
			{
				this.add(element);
			}
		}
		return this;
	}

	public SVGDrawer modifyRawElements(UnaryOperator<Stream<RawSVGElement>> rawElementModifier)
	{
		this.rawSVGRoot.setElements(rawElementModifier	.apply(this.rawSVGRoot	.getElements()
																				.stream())
														.collect(Collectors.toList()));

		return this;
	}

	public SVGDrawer addRawElements(Collection<RawSVGElement> rawElements)
	{
		List<RawSVGElement> elements = new ArrayList<>();
		if (this.rawSVGRoot.getElements() != null)
		{
			elements.addAll(this.rawSVGRoot.getElements());
		}
		if (rawElements != null)
		{
			elements.addAll(rawElements);
		}
		this.rawSVGRoot.setElements(elements);
		return this;
	}

	public SVGDrawer addRawElements(SVGDrawer svgDrawer)
	{
		this.addRawElements(svgDrawer	.renderAsResult()
										.getRawSVGRoot()
										.getElements());
		return this;
	}
}
