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
/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.svg.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;
import org.omnaest.utils.NumberUtils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "linearGradient")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGLinearGradient extends RawSVGDefinitionElement
{
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
        return this.href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getGradientTransform()
    {
        return this.gradientTransform;
    }

    public void setGradientTransform(String gradientTransform)
    {
        this.gradientTransform = gradientTransform;
    }

    public String getGradientUnits()
    {
        return this.gradientUnits;
    }

    public void setGradientUnits(String gradientUnits)
    {
        this.gradientUnits = gradientUnits;
    }

    public String getX1()
    {
        return this.x1;
    }

    public void setX1(String x1)
    {
        this.x1 = x1;
    }

    public String getY1()
    {
        return this.y1;
    }

    public void setY1(String y1)
    {
        this.y1 = y1;
    }

    public String getX2()
    {
        return this.x2;
    }

    public void setX2(String x2)
    {
        this.x2 = x2;
    }

    public String getY2()
    {
        return this.y2;
    }

    public void setY2(String y2)
    {
        this.y2 = y2;
    }

    public List<RawSVGStopElement> getStops()
    {
        return this.stops;
    }

    public void setStops(List<RawSVGStopElement> stops)
    {
        this.stops = stops;
    }

    @Override
    public RawSVGTransformer transformer()
    {
        return new DefaultRawSVGTransformer(this, Arrays.asList(new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGLinearGradient.this.setX1(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGLinearGradient.this.getX1());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGLinearGradient.this.setX2(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGLinearGradient.this.getX2());
            }
        }), Arrays.asList(new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGLinearGradient.this.setY1(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGLinearGradient.this.getY1());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGLinearGradient.this.setY2(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGLinearGradient.this.getY2());
            }
        }), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public String toString()
    {
        return "RawSVGLinearGradient [id=" + this.getId() + ", href=" + this.href + ", gradientTransform=" + this.gradientTransform + ", gradientUnits="
                + this.gradientUnits + ", x1=" + this.x1 + ", y1=" + this.y1 + ", x2=" + this.x2 + ", y2=" + this.y2 + ", stops=" + this.stops + ", style="
                + this.style + ", transform=" + this.transform + ", content=" + this.rawContent + "]";
    }

}
