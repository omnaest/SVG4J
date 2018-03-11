package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "use")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGUse
{
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String href;

    @XmlAttribute
    private String transform;

    public String getHref()
    {
        return this.href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getTransform()
    {
        return this.transform;
    }

    public void setTransform(String transform)
    {
        this.transform = transform;
    }

    @Override
    public String toString()
    {
        return "RawSVGUse [href=" + this.href + ", transform=" + this.transform + "]";
    }

}
