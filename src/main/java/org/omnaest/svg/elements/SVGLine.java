package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.svg.model.RawSVGLine;

public class SVGLine implements SVGElement
{
	private int	x1;
	private int	y1;
	private int	x2;
	private int	y2;

	private int		strokeWidth	= 1;
	private String	strokeColor	= "gray";
	private double	opacity		= 1.0;

	public SVGLine(int x1, int y1, int x2, int y2)
	{
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public SVGLine setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	public SVGLine setStrokeWidth(int strokeWidth)
	{
		this.strokeWidth = strokeWidth;
		return this;
	}

	public int getX1()
	{
		return x1;
	}

	public int getY1()
	{
		return y1;
	}

	public int getX2()
	{
		return x2;
	}

	public int getY2()
	{
		return y2;
	}

	@Override
	public RawSVGElement render()
	{
		return new RawSVGLine()	.setX1("" + this.x1)
								.setX2("" + this.x2)
								.setY1("" + this.y1)
								.setY2("" + this.y2)
								.setStroke(this.strokeColor)
								.setStrokeWidth("" + this.strokeWidth + "px")
								.setStyle("stroke-opacity:" + this.opacity);
		//		return "\n<line x1=\"" + this.x1 + "\" y1=\"" + this.y1 + "\" x2=\"" + this.x2 + "\" y2=\"" + this.y2 + "\" stroke=\"" + this.strokeColor
		//				+ "\" stroke-width=\"" + this.strokeWidth + "px\" style=\"stroke-opacity:" + this.opacity + "\"/>";
	}

	public SVGLine setOpacity(double opacity)
	{
		this.opacity = opacity;
		return this;
	}

}
