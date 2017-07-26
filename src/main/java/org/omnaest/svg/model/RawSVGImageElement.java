package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "image")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGImageElement extends RawSVGElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String width;

	@XmlAttribute
	private String height;

	@XmlAttribute
	private String x;

	@XmlAttribute
	private String y;

	@XmlAttribute
	private String style;

	@XmlAttribute
	private String transform;

	@XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
	private String href;

	public String getTransform()
	{
		return transform;
	}

	public RawSVGImageElement setTransform(String transform)
	{
		super.setTransform(transform);
		return this;
	}

	public String getStyle()
	{
		return style;
	}

	public RawSVGImageElement setStyle(String style)
	{
		super.setStyle(style);
		return this;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getX()
	{
		return x;
	}

	public void setX(String x)
	{
		this.x = x;
	}

	public String getY()
	{
		return y;
	}

	public void setY(String y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "RawSVGImageElement [id=" + id + ", width=" + width + ", height=" + height + ", x=" + x + ", y=" + y + ", style=" + style + ", transform="
				+ transform + ", href=" + href + "]";
	}

}
