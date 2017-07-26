package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "path")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGPath extends RawSVGElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String style;

	@XmlAttribute
	private String d;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public String getD()
	{
		return d;
	}

	public void setD(String d)
	{
		this.d = d;
	}

	@Override
	public String toString()
	{
		return "RawSVGPath [id=" + id + ", style=" + style + ", d=" + d + "]";
	}

}
