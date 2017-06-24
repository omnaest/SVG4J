package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;

public class SVGText implements SVGElement
{
	private int x;
	private int y;
	private String text;

	public SVGText(int x, int y, String text)
	{
		super();
		this.x = x;
		this.y = y;
		this.text = text;
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

	@Override
	public String render()
	{
		return "<text x=\"" + this.x + "\" y=\"" + this.y + "\" fill=\"red\" style=\"font-size:10px\" >" + this.text
				+ "</text>";
	}

}
