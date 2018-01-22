package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.math.NumberUtils;
import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;

@XmlRootElement(name = "ellipse")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGEllipse extends RawSVGElement
{
    @XmlAttribute
    private String cx;

    @XmlAttribute
    private String cy;

    @XmlAttribute
    private String rx;

    @XmlAttribute
    private String ry;

    public String getCx()
    {
        return this.cx;
    }

    public RawSVGEllipse setCx(String cx)
    {
        this.cx = cx;
        return this;
    }

    public String getCy()
    {
        return this.cy;
    }

    public RawSVGEllipse setCy(String cy)
    {
        this.cy = cy;
        return this;
    }

    public String getRx()
    {
        return this.rx;
    }

    public RawSVGEllipse setRx(String rx)
    {
        this.rx = rx;
        return this;
    }

    public String getRy()
    {
        return this.ry;
    }

    public RawSVGEllipse setRy(String ry)
    {
        this.ry = ry;
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGEllipse [cx=" + this.cx + ", cy=" + this.cy + ", rx=" + this.rx + ", ry=" + this.ry + "]";
    }

    @Override
    protected RawSVGTransformer transformer()
    {

        return new DefaultRawSVGTransformer(this).addLocationXSupplierConsumer(new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGEllipse.this.cx = org.omnaest.utils.NumberUtils.formatter()
                                                                     .format(value);
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGEllipse.this.cx);
            }
        })
                                                 .addLocationYSupplierConsumer(new SupplierConsumer()
                                                 {
                                                     @Override
                                                     public void accept(Double value)
                                                     {
                                                         RawSVGEllipse.this.cy = org.omnaest.utils.NumberUtils.formatter()
                                                                                                              .format(value);
                                                     }

                                                     @Override
                                                     public Double get()
                                                     {
                                                         return NumberUtils.toDouble(RawSVGEllipse.this.cy);
                                                     }
                                                 })
                                                 .addHeightSupplierConsumer(new SupplierConsumer()
                                                 {
                                                     @Override
                                                     public void accept(Double value)
                                                     {
                                                         RawSVGEllipse.this.ry = org.omnaest.utils.NumberUtils.formatter()
                                                                                                              .format(value);
                                                     }

                                                     @Override
                                                     public Double get()
                                                     {
                                                         return NumberUtils.toDouble(RawSVGEllipse.this.ry);
                                                     }
                                                 })
                                                 .addWidthSupplierConsumer(new SupplierConsumer()
                                                 {
                                                     @Override
                                                     public void accept(Double value)
                                                     {
                                                         RawSVGEllipse.this.rx = org.omnaest.utils.NumberUtils.formatter()
                                                                                                              .format(value);
                                                     }

                                                     @Override
                                                     public Double get()
                                                     {
                                                         return NumberUtils.toDouble(RawSVGEllipse.this.rx);
                                                     }
                                                 });
    }

}
