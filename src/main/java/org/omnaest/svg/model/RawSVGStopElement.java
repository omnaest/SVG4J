package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stop")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGStopElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String offset;

	@XmlAttribute
	private String style;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOffset()
	{
		return offset;
	}

	public void setOffset(String offset)
	{
		this.offset = offset;
	}

	public String getStyle()
	{
		return style;
	}

	public RawSVGStopElement setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGStopElement [id=" + id + ", offset=" + offset + ", style=" + style + "]";
	}

}
