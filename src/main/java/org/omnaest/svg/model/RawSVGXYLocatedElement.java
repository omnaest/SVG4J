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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;
import org.omnaest.utils.NumberUtils;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class RawSVGXYLocatedElement extends RawSVGElement
{
    @XmlAttribute
    protected String x;

    @XmlAttribute
    protected String y;

    public RawSVGXYLocatedElement setX(String x)
    {
        this.x = x;
        return this;
    }

    public String getX()
    {
        return this.x;
    }

    public String getY()
    {
        return this.y;
    }

    public RawSVGXYLocatedElement setY(String y)
    {
        this.y = y;
        return this;
    }

    @Override
    public RawSVGTransformer transformer()
    {
        return new DefaultRawSVGTransformer(this, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGXYLocatedElement.this.setX(NumberUtils.formatter()
                                                            .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGXYLocatedElement.this.getX());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGXYLocatedElement.this.setY(NumberUtils.formatter()
                                                            .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGXYLocatedElement.this.getY());
            }
        });
    }
}
