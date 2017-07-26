package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "text")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGText extends RawSVGElement
{
	@XmlAttribute
	private String x;

	@XmlAttribute
	private String y;

	@XmlAttribute
	private String fill;

	public RawSVGText setX(String x)
	{
		this.x = x;
		return this;
	}

	public String getX()
	{
		return x;
	}

	public String getY()
	{
		return y;
	}

	public RawSVGText setY(String y)
	{
		this.y = y;
		return this;
	}

	public String getFill()
	{
		return fill;
	}

	public RawSVGText setFill(String fill)
	{
		this.fill = fill;
		return this;
	}

	public RawSVGText setStyle(String style)
	{
		super.setStyle(style);
		return this;
	}

	public RawSVGText setTransform(String transform)
	{
		super.setTransform(transform);
		return this;
	}

	public String getText()
	{
		return this.content;
	}

	public RawSVGText setText(String text)
	{
		this.content = text;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGText [x=" + x + ", y=" + y + ", fill=" + fill + ", style=" + style + ", transform=" + transform + ", content=" + content + "]";
	}

}
