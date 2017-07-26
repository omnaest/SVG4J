package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "polygon")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGPolygon extends RawSVGElement
{
	@XmlAttribute
	private String points;

	@XmlAttribute
	private String stroke;

	public String getPoints()
	{
		return points;
	}

	public RawSVGPolygon setPoints(String points)
	{
		this.points = points;return this;
	}

	public String getStroke()
	{
		return stroke;
	}

	public RawSVGPolygon setStroke(String stroke)
	{
		this.stroke = stroke;return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGPolygon [points=" + points + ", stroke=" + stroke + "]";
	}

}
