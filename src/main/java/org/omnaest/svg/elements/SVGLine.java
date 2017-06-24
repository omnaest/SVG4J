package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;

public class SVGLine implements SVGElement
{
	private int x1;
	private int y1;
	private int x2;
	private int y2;

	public SVGLine(int x1, int y1, int x2, int y2)
	{
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
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
	public String render()
	{
		return "\n<line x1=\"" + this.x1 + "\" y1=\"" + this.y1 + "\" x2=\"" + this.x2 + "\" y2=\"" + this.y2
				+ "\" stroke=\"gray\" stroke-width=\"1px\"/>";
	}

}
