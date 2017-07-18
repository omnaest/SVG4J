package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;

public class SVGText implements SVGElement
{
	public static final int DEFAULT_FONTSIZE = 10;

	private int		x;
	private int		y;
	private String	text;
	private int		fontSize	= DEFAULT_FONTSIZE;
	private String	color		= "black";

	public SVGText(int x, int y, String text)
	{
		super();
		this.x = x;
		this.y = y;
		this.text = text;
	}

	public String getColor()
	{
		return color;
	}

	public SVGText setColor(String color)
	{
		this.color = color;
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

	public String getText()
	{
		return text;
	}

	public SVGText setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
		return this;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	@Override
	public String render()
	{
		return "<text x=\"" + this.x + "\" y=\"" + this.y + "\" fill=\"" + color + "\" style=\"font-size:" + fontSize + "px\" >" + this.text + "</text>";
	}

}
