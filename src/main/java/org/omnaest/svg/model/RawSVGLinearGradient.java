package org.omnaest.svg.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "linearGradient")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGLinearGradient extends RawSVGElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
	private String href;

	@XmlAttribute
	private String gradientTransform;

	@XmlAttribute
	private String gradientUnits;

	@XmlAttribute
	private String x1;

	@XmlAttribute
	private String y1;

	@XmlAttribute
	private String x2;

	@XmlAttribute
	private String y2;

	@XmlElementRef
	private List<RawSVGStopElement> stops;

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

	public String getGradientTransform()
	{
		return gradientTransform;
	}

	public void setGradientTransform(String gradientTransform)
	{
		this.gradientTransform = gradientTransform;
	}

	public String getGradientUnits()
	{
		return gradientUnits;
	}

	public void setGradientUnits(String gradientUnits)
	{
		this.gradientUnits = gradientUnits;
	}

	public String getX1()
	{
		return x1;
	}

	public void setX1(String x1)
	{
		this.x1 = x1;
	}

	public String getY1()
	{
		return y1;
	}

	public void setY1(String y1)
	{
		this.y1 = y1;
	}

	public String getX2()
	{
		return x2;
	}

	public void setX2(String x2)
	{
		this.x2 = x2;
	}

	public String getY2()
	{
		return y2;
	}

	public void setY2(String y2)
	{
		this.y2 = y2;
	}

	public List<RawSVGStopElement> getStops()
	{
		return stops;
	}

	public void setStops(List<RawSVGStopElement> stops)
	{
		this.stops = stops;
	}

	@Override
	public String toString()
	{
		return "RawSVGLinearGradient [id=" + id + ", href=" + href + ", gradientTransform=" + gradientTransform + ", gradientUnits=" + gradientUnits + ", x1="
				+ x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + ", stops=" + stops + ", style=" + style + ", transform=" + transform + ", content="
				+ content + "]";
	}

}
