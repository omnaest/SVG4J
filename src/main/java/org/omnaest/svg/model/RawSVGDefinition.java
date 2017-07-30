package org.omnaest.svg.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "defs")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGDefinition extends RawSVGElement
{
	@XmlAttribute
	private String id;

	@XmlElementRef
	private List<RawSVGLinearGradient> elements;

	public List<RawSVGLinearGradient> getElements()
	{
		return elements;
	}

	public void setElements(List<RawSVGLinearGradient> elements)
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

	@Override
	public String toString()
	{
		return "RawSVGDefinitionElement [id=" + id + ", elements=" + elements + "]";
	}

}
