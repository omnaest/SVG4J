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
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "image")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGImageElement extends RawSVGXYLocatedWithWidthAndHeightElement
{

    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String href;

    @Override
    public String getTransform()
    {
        return this.transform;
    }

    @Override
    public RawSVGImageElement setTransform(String transform)
    {
        super.setTransform(transform);
        return this;
    }

    @Override
    public String getStyle()
    {
        return this.style;
    }

    @Override
    public RawSVGImageElement setStyle(String style)
    {
        super.setStyle(style);
        return this;
    }

    public String getHref()
    {
        return this.href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    @Override
    public RawSVGImageElement setWidth(String width)
    {
        super.setWidth(width);
        return this;
    }

    @Override
    public RawSVGImageElement setHeight(String height)
    {
        super.setHeight(height);
        return this;
    }

    @Override
    public String getX()
    {
        return this.x;
    }

    @Override
    public RawSVGImageElement setX(String x)
    {
        super.setX(x);
        return this;
    }

    @Override
    public RawSVGImageElement setY(String y)
    {
        super.setY(y);
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGImageElement [id=" + this.getId() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", x=" + this.x + ", y=" + this.y
                + ", style=" + this.style + ", transform=" + this.transform + ", href=" + this.href + "]";
    }

}
