package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGRectangle;

public class SVGRectangle implements SVGElement
{
	private int	x;
	private int	y;
	private int	width;
	private int	height;

	private int		strokeWidth		= 1;
	private String	strokeColor		= "black";
	private String	fillColor		= "gray";
	private double	strokeOpacity	= 0.95;
	private double	fillOpacity		= 0.9;

	public SVGRectangle(int x, int y, int width, int height)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public SVGRectangle setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	public SVGRectangle setStrokeWidth(int strokeWidth)
	{
		this.strokeWidth = strokeWidth;
		return this;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	@Override
	public RawSVGRectangle render()
	{
		return new RawSVGRectangle().setX("" + this.x)
									.setY("" + this.y)
									.setHeight("" + this.height)
									.setWidth("" + this.width)
									.setStyle("fill:" + this.fillColor + ";stroke-width:" + this.strokeWidth + ";stroke:" + this.strokeColor + ";fill-opacity:"
											+ this.fillOpacity + ";stroke-opacity:" + this.strokeOpacity);

		//		return "\n<rect x=\"" + this.x + "\" y=\"" + this.y + "\" width=\"" + this.width + "\" height=\"" + this.height + "\" style=\"fill:" + this.fillColor
		//				+ ";stroke-width:" + this.strokeWidth + ";stroke:" + this.strokeColor + ";fill-opacity:" + this.fillOpacity + ";stroke-opacity:"
		//				+ this.strokeOpacity + "\" />";
	}

	public SVGRectangle setStrokeOpacity(double opacity)
	{
		this.strokeOpacity = opacity;
		return this;
	}

	public SVGRectangle setFillOpacity(double opacity)
	{
		this.fillOpacity = opacity;
		return this;
	}

	public SVGRectangle setFillColor(String color)
	{
		this.fillColor = color;
		return this;
	}

}
