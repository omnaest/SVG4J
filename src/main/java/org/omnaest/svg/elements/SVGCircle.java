package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;

public class SVGCircle implements SVGElement
{
	private int	x;
	private int	y;
	private int	r;

	private String	strokeColor	= "black";
	private String	fillColor	= "white";
	private double	opacity		= 1.0;
	private int		strokeWidth	= 1;

	public SVGCircle(int x, int y, int r)
	{
		super();
		this.x = x;
		this.y = y;
		this.r = r;
	}

	public SVGCircle setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	public SVGCircle setFillColor(String fillColor)
	{
		this.fillColor = fillColor;
		return this;
	}

	public SVGCircle setOpacity(double opacity)
	{
		this.opacity = opacity;
		return this;
	}

	@Override
	public String render()
	{
		return "\n<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" stroke=\"" + this.strokeColor + "\" fill=\"" + this.fillColor + "\" opacity=\""
				+ this.opacity + "\" style=\"stroke-width:" + this.strokeWidth + "\" />";

	}

	public SVGCircle setStrokeAndFillColor(String color)
	{
		return this	.setFillColor(color)
					.setStrokeColor(color);
	}

	public SVGCircle setStrokeWidth(int strokeWidth)
	{
		this.strokeWidth = strokeWidth;
		return this;
	}

}
