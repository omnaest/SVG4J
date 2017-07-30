package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "script")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGScript
{
	@XmlAttribute
	private String type = "text/javascript";

	@XmlValue
	private String content;

	public String getContent()
	{
		return content;
	}

	public RawSVGScript setContent(String content)
	{
		this.content = content;
		return this;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
