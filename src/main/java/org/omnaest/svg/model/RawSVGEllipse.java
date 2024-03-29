/*******************************************************************************
 * Copyright 2021 Danny Kunz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.omnaest.svg.model;

import org.apache.commons.lang.math.NumberUtils;
import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

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
