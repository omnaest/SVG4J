package org.omnaest.svg.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "g")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGGroupElement extends RawSVGElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String transform;

	@XmlElementRef
	private List<RawSVGElement> elements;

	public List<RawSVGElement> getElements()
	{
		return elements;
	}

	public void setElements(List<RawSVGElement> elements)
	{
		this.elements = elements;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTransform()
	{
		return transform;
	}

	public void setTransform(String transform)
	{
		this.transform = transform;
	}

	@Override
	public String toString()
	{
		return "RawSVGGroupElement [id=" + id + ", transform=" + transform + ", elements=" + elements + "]";
	}

}
