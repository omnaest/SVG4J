package org.omnaest.svg.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ RawSVGImageElement.class, RawSVGGroupElement.class })
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "svg")
public class RawSVGRoot
{
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String width;

	@XmlAttribute
	private String height;

	@XmlAttribute
	private String version;

	@XmlElementRef
	private List<RawSVGElement> elements;

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

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public List<RawSVGElement> getElements()
	{
		return elements;
	}

	public void setElements(List<RawSVGElement> elements)
	{
		this.elements = elements;
	}

	@Override
	public String toString()
	{
		return "RawSVGRoot [id=" + id + ", width=" + width + ", height=" + height + ", version=" + version + ", elements=" + elements + "]";
	}

}
