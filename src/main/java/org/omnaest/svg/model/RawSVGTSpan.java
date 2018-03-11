package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "tspan")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGTSpan implements RawSVGElementWithContent
{
    @XmlValue
    private String content;

    @XmlAttribute
    private String x;

    @XmlAttribute
    private String y;

    @XmlAttribute
    private String dx;

    @XmlAttribute
    private String dy;

    @XmlAttribute(name = "class")
    private String cssClass;

    public String getX()
    {
        return this.x;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public String getY()
    {
        return this.y;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public String getDx()
    {
        return this.dx;
    }

    public void setDx(String dx)
    {
        this.dx = dx;
    }

    public String getDy()
    {
        return this.dy;
    }

    public void setDy(String dy)
    {
        this.dy = dy;
    }

    public String getCssClass()
    {
        return this.cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    @Override
    public String getContent()
    {
        return this.content;
    }

    public RawSVGTSpan setContent(String content)
    {
        this.content = content;
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGTSpan [content=" + this.content + ", x=" + this.x + ", y=" + this.y + ", dx=" + this.dx + ", dy=" + this.dy + ", cssClass="
                + this.cssClass + "]";
    }

}
