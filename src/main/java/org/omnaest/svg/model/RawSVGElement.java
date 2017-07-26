package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class RawSVGElement
{
	@XmlAttribute
	protected String style;

	@XmlAttribute
	protected String transform;

	@XmlValue
	protected String content;

	public String getStyle()
	{
		return style;
	}

	public RawSVGElement setStyle(String style)
	{
		this.style = style;
		return this;
	}

	public String getTransform()
	{
		return transform;
	}

	public RawSVGElement setTransform(String transform)
	{
		this.transform = transform;
		return this;

	}

	public String getContent()
	{
		return content;
	}

}
