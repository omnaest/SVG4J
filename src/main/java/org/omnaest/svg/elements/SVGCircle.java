package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;

public class SVGCircle implements SVGElement
{
	private int x;
	private int y;
	private int r;

	private String strokeColor = "red";
	private String fillColor = "white";
	private double opacity = 1.0;

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
		return "\n<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + r + "\" stroke=\"" + this.strokeColor + "\" fill=\""
				+ this.fillColor + "\" opacity=\"" + this.opacity + "\" />";

	}

	public SVGElement setStrokeAndFillColor(String color)
	{
		return this.setFillColor(color).setStrokeColor(color);
	}

}
