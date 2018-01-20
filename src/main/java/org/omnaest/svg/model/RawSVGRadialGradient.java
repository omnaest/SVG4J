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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;
import org.omnaest.utils.NumberUtils;

@XmlRootElement(name = "radialGradient")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGRadialGradient extends RawSVGDefinitionElement
{
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String href;

    @XmlAttribute
    private String cx;

    @XmlAttribute
    private String cy;

    @XmlAttribute
    private String fx;

    @XmlAttribute
    private String fy;

    @XmlAttribute
    private String r;

    @XmlAttribute
    private String gradientTransform;

    @XmlAttribute
    private String gradientUnits;

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

    public String getCx()
    {
        return this.cx;
    }

    public void setCx(String cx)
    {
        this.cx = cx;
    }

    public String getCy()
    {
        return this.cy;
    }

    public void setCy(String cy)
    {
        this.cy = cy;
    }

    public String getFx()
    {
        return this.fx;
    }

    public void setFx(String fx)
    {
        this.fx = fx;
    }

    public String getFy()
    {
        return this.fy;
    }

    public void setFy(String fy)
    {
        this.fy = fy;
    }

    public String getR()
    {
        return this.r;
    }

    public void setR(String r)
    {
        this.r = r;
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
                RawSVGRadialGradient.this.setCx(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGRadialGradient.this.getCx());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGRadialGradient.this.setFx(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGRadialGradient.this.getFx());
            }
        }), Arrays.asList(new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGRadialGradient.this.setCy(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGRadialGradient.this.getCy());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGRadialGradient.this.setFy(NumberUtils.formatter()
                                                           .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGRadialGradient.this.getFy());
            }
        }), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public String toString()
    {
        return "RawSVGRadialGradient [href=" + this.href + ", cx=" + this.cx + ", cy=" + this.cy + ", fx=" + this.fx + ", fy=" + this.fy + ", r=" + this.r
                + ", gradientTransform=" + this.gradientTransform + ", gradientUnits=" + this.gradientUnits + ", stops=" + this.stops + "]";
    }

}
