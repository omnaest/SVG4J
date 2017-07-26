package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "polyline")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGPolyline extends RawSVGElement
{
	@XmlAttribute
	private String points;

	@XmlAttribute
	private String stroke;

	@XmlAttribute
	private String fill;

	@XmlAttribute
	private String opacity;

	public String getPoints()
	{
		return points;
	}

	public RawSVGPolyline setPoints(String points)
	{
		this.points = points;
		return this;
	}

	public String getStroke()
	{
		return stroke;
	}

	public RawSVGPolyline setStroke(String stroke)
	{
		this.stroke = stroke;
		return this;
	}

	public String getFill()
	{
		return fill;
	}

	public RawSVGPolyline setFill(String fill)
	{
		this.fill = fill;
		return this;
	}

	public String getOpacity()
	{
		return opacity;
	}

	public RawSVGPolyline setOpacity(String opacity)
	{
		this.opacity = opacity;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGPolyline [points=" + points + ", stroke=" + stroke + ", fill=" + fill + ", opacity=" + opacity + "]";
	}

}
