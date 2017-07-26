package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "circle")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGCircle extends RawSVGElement
{
	@XmlAttribute
	private String cx;

	@XmlAttribute
	private String cy;

	@XmlAttribute
	private String r;

	@XmlAttribute
	private String stroke;

	@XmlAttribute
	private String fill;

	@XmlAttribute(name = "fill-opacity")
	private String fillOpacity;

	@XmlAttribute(name = "stroke-opacity")
	private String strokeOpacity;

	@XmlAttribute
	private String style;

	public String getCx()
	{
		return cx;
	}

	public RawSVGCircle setCx(String cx)
	{
		this.cx = cx;
		return this;
	}

	public String getCy()
	{
		return cy;
	}

	public RawSVGCircle setCy(String cy)
	{
		this.cy = cy;
		return this;
	}

	public String getR()
	{
		return r;
	}

	public RawSVGCircle setR(String r)
	{
		this.r = r;
		return this;
	}

	public String getStroke()
	{
		return stroke;
	}

	public RawSVGCircle setStroke(String stroke)
	{
		this.stroke = stroke;
		return this;
	}

	public String getFill()
	{
		return fill;
	}

	public RawSVGCircle setFill(String fill)
	{
		this.fill = fill;
		return this;
	}

	public String getFillOpacity()
	{
		return fillOpacity;
	}

	public RawSVGCircle setFillOpacity(String fillOpacity)
	{
		this.fillOpacity = fillOpacity;
		return this;
	}

	public String getStrokeOpacity()
	{
		return strokeOpacity;
	}

	public RawSVGCircle setStrokeOpacity(String strokeOpacity)
	{
		this.strokeOpacity = strokeOpacity;
		return this;
	}

	public String getStyle()
	{
		return style;
	}

	public RawSVGCircle setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGCircle [cx=" + cx + ", cy=" + cy + ", r=" + r + ", stroke=" + stroke + ", fill=" + fill + ", fillOpacity=" + fillOpacity
				+ ", strokeOpacity=" + strokeOpacity + ", style=" + style + "]";
	}

}
