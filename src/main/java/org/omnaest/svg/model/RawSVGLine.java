package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "line")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGLine extends RawSVGElement
{
	@XmlAttribute
	private String x1;

	@XmlAttribute
	private String y1;

	@XmlAttribute
	private String x2;

	@XmlAttribute
	private String y2;

	@XmlAttribute
	private String stroke;

	@XmlAttribute(name = "stroke-width")
	private String strokeWidth;

	@XmlAttribute
	private String style;

	public String getX1()
	{
		return x1;
	}

	public RawSVGLine setX1(String x1)
	{
		this.x1 = x1;
		return this;
	}

	public String getY1()
	{
		return y1;
	}

	public RawSVGLine setY1(String y1)
	{
		this.y1 = y1;
		return this;
	}

	public String getX2()
	{
		return x2;
	}

	public RawSVGLine setX2(String x2)
	{
		this.x2 = x2;
		return this;
	}

	public String getY2()
	{
		return y2;
	}

	public RawSVGLine setY2(String y2)
	{
		this.y2 = y2;
		return this;
	}

	public String getStroke()
	{
		return stroke;
	}

	public RawSVGLine setStroke(String stroke)
	{
		this.stroke = stroke;
		return this;
	}

	public String getStrokeWidth()
	{
		return strokeWidth;
	}

	public RawSVGLine setStrokeWidth(String strokeWidth)
	{
		this.strokeWidth = strokeWidth;
		return this;
	}

	public String getStyle()
	{
		return style;
	}

	public RawSVGLine setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGLine [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + ", stroke=" + stroke + ", strokeWidth=" + strokeWidth + ", style=" + style
				+ "]";
	}

}
