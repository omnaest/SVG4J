package org.omnaest.svg;

import java.util.ArrayList;
import java.util.List;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.elements.base.SVGRenderable;

/**
 * @see SVGUtils
 * @author omnaest
 */
public class SVGDrawer implements SVGRenderable
{
	private List<SVGElement> elements = new ArrayList<>();

	private int		width;
	private int		height;
	private boolean	embedReloadTimer	= false;

	protected SVGDrawer(int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
	}

	public boolean isEmbedReloadTimer()
	{
		return embedReloadTimer;
	}

	public SVGDrawer setEmbedReloadTimer(boolean embedReloadTimer)
	{
		this.embedReloadTimer = embedReloadTimer;
		return this;
	}

	/**
	 * Renders the svg xml into a {@link String}
	 */
	public String render()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("\n<svg xmlns=\"http://www.w3.org/2000/svg\"");
		sb.append(" version=\"1.1\" baseProfile=\"full\"");
		sb.append(" width=\"1000px\" height=\"600px\" viewBox=\"0 0 " + this.width + " " + this.height + "\">");

		for (SVGElement element : this.elements)
		{
			sb.append(element.render());
		}

		if (this.embedReloadTimer)
		{
			sb.append("\n<script type=\"text/javascript\">");
			sb.append("<![CDATA[");
			int reloadInterval = 1000;
			sb.append("    setTimeout(function(){ location.reload();  }, " + reloadInterval + ");");
			sb.append("]]>");
			sb.append("</script>");
		}

		sb.append("\n</svg>");
		return sb.toString();
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
}
