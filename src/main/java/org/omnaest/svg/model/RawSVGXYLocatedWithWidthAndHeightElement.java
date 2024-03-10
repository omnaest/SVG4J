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

import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;
import org.omnaest.utils.NumberUtils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "text")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGXYLocatedWithWidthAndHeightElement extends RawSVGXYLocatedElement
{
    @XmlAttribute
    private String width;

    @XmlAttribute
    private String height;

    public String getWidth()
    {
        return this.width;
    }

    public RawSVGXYLocatedWithWidthAndHeightElement setWidth(String width)
    {
        this.width = width;
        return this;
    }

    public String getHeight()
    {
        return this.height;
    }

    public RawSVGXYLocatedWithWidthAndHeightElement setHeight(String height)
    {
        this.height = height;
        return this;
    }

    @Override
    public RawSVGXYLocatedWithWidthAndHeightElement setX(String x)
    {
        this.x = x;
        return this;
    }

    @Override
    public String getX()
    {
        return this.x;
    }

    @Override
    public String getY()
    {
        return this.y;
    }

    @Override
    public RawSVGXYLocatedWithWidthAndHeightElement setY(String y)
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
                RawSVGXYLocatedWithWidthAndHeightElement.this.setX(NumberUtils.formatter()
                                                                              .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGXYLocatedWithWidthAndHeightElement.this.getX());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGXYLocatedWithWidthAndHeightElement.this.setY(NumberUtils.formatter()
                                                                              .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGXYLocatedWithWidthAndHeightElement.this.getY());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGXYLocatedWithWidthAndHeightElement.this.setWidth(NumberUtils.formatter()
                                                                                  .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGXYLocatedWithWidthAndHeightElement.this.getWidth());
            }
        }, new SupplierConsumer()
        {
            @Override
            public void accept(Double value)
            {
                RawSVGXYLocatedWithWidthAndHeightElement.this.setHeight(NumberUtils.formatter()
                                                                                   .format(value));
            }

            @Override
            public Double get()
            {
                return NumberUtils.toDouble(RawSVGXYLocatedWithWidthAndHeightElement.this.getHeight());
            }
        });
    }

    @Override
    public String toString()
    {
        return "RawSVGXYLocatedWithWidthAndHeightElement [width=" + this.width + ", height=" + this.height + "]";
    }

}
