package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rect")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGRectangle extends RawSVGElement
{
	@XmlAttribute
	private String x;

	@XmlAttribute
	private String y;

	@XmlAttribute
	private String width;

	@XmlAttribute
	private String height;

	@XmlAttribute
	private String style;

	public String getX()
	{
		return x;
	}

	public RawSVGRectangle setX(String x)
	{
		this.x = x;
		return this;
	}

	public String getY()
	{
		return y;
	}

	public RawSVGRectangle setY(String y)
	{
		this.y = y;
		return this;
	}

	public String getWidth()
	{
		return width;
	}

	public RawSVGRectangle setWidth(String width)
	{
		this.width = width;
		return this;
	}

	public String getHeight()
	{
		return height;
	}

	public RawSVGRectangle setHeight(String height)
	{
		this.height = height;
		return this;
	}

	public String getStyle()
	{
		return style;
	}

	public RawSVGRectangle setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGRectangle [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", style=" + style + "]";
	}

}
