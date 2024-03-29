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

import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;
import org.omnaest.utils.NumberUtils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "text")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGX1X2Y1Y2LocatedElement extends RawSVGElement
{
    @XmlAttribute
    protected String x1;

    @XmlAttribute
    protected String y1;

    @XmlAttribute
    protected String x2;

    @XmlAttribute
    protected String y2;

    public String getX1()
    {
        return this.x1;
    }

    public RawSVGX1X2Y1Y2LocatedElement setX1(String x1)
    {
        this.x1 = x1;
        return this;
    }

    public String getY1()
    {
        return this.y1;
    }

    public RawSVGX1X2Y1Y2LocatedElement setY1(String y1)
    {
        this.y1 = y1;
        return this;
    }

    public String getX2()
    {
        return this.x2;
    }

    public RawSVGX1X2Y1Y2LocatedElement setX2(String x2)
    {
        this.x2 = x2;
        return this;
    }

    public String getY2()
    {
        return this.y2;
    }

    public RawSVGX1X2Y1Y2LocatedElement setY2(String y2)
    {
        this.y2 = y2;
        return this;
    }

    @Override
    public RawSVGTransformer transformer()
    {
        return new DefaultRawSVGTransformer(this, Arrays.asList(new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGX1X2Y1Y2LocatedElement.this.setX1(NumberUtils.formatter()
                                                                   .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGX1X2Y1Y2LocatedElement.this.getX1());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGX1X2Y1Y2LocatedElement.this.setX2(NumberUtils.formatter()
                                                                   .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGX1X2Y1Y2LocatedElement.this.getX2());
            }
        }), Arrays.asList(new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGX1X2Y1Y2LocatedElement.this.setY1(NumberUtils.formatter()
                                                                   .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGX1X2Y1Y2LocatedElement.this.getY1());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGX1X2Y1Y2LocatedElement.this.setY2(NumberUtils.formatter()
                                                                   .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGX1X2Y1Y2LocatedElement.this.getY2());
            }
        }), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public String toString()
    {
        return "RawSVGX1X2Y1Y2LocatedElement [x1=" + this.x1 + ", y1=" + this.y1 + ", x2=" + this.x2 + ", y2=" + this.y2 + "]";
    }

}
