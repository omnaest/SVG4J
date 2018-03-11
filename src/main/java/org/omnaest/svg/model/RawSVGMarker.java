package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "marker")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGMarker extends RawSVGDefinitionElement
{
    @XmlAttribute
    private String refX;

    @XmlAttribute
    private String orient;

    @XmlAttribute
    private String markerUnits;

    @XmlAttribute
    private String overflow;

    @XmlElementRef
    private RawSVGUse use;

    public RawSVGUse getUse()
    {
        return this.use;
    }

    public void setUse(RawSVGUse use)
    {
        this.use = use;
    }

    public String getRefX()
    {
        return this.refX;
    }

    public void setRefX(String refX)
    {
        this.refX = refX;
    }

    public String getOrient()
    {
        return this.orient;
    }

    public void setOrient(String orient)
    {
        this.orient = orient;
    }

    public String getMarkerUnits()
    {
        return this.markerUnits;
    }

    public void setMarkerUnits(String markerUnits)
    {
        this.markerUnits = markerUnits;
    }

    public String getOverflow()
    {
        return this.overflow;
    }

    public void setOverflow(String overflow)
    {
        this.overflow = overflow;
    }

    @Override
    public String toString()
    {
        return "RawSVGMarker [refX=" + this.refX + ", orient=" + this.orient + ", markerUnits=" + this.markerUnits + ", overflow=" + this.overflow + ", use="
                + this.use + "]";
    }

    @Override
    protected RawSVGTransformer transformer()
    {
        return new DefaultRawSVGTransformer(this);
    }

}
