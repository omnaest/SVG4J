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
import javax.xml.bind.annotation.XmlValue;

/**
 * Please register any subclass at {@link RawSVGRoot}
 * 
 * @see RawSVGRoot
 * @author omnaest
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class RawSVGElement implements RawSVGTransformer
{
    @XmlAttribute(name = "class")
    protected String cssClass;

    @XmlAttribute
    protected String style;

    @XmlAttribute
    protected String transform;

    @XmlValue
    protected String content;

    public String getStyle()
    {
        return this.style;
    }

    public RawSVGElement setStyle(String style)
    {
        this.style = style;
        return this;
    }

    public String getTransform()
    {
        return this.transform;
    }

    public RawSVGElement setTransform(String transform)
    {
        this.transform = transform;
        return this;

    }

    public String getContent()
    {
        return this.content;
    }

    public String getCssClass()
    {
        return this.cssClass;
    }

    public RawSVGElement setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
        return this;
    }

    protected abstract RawSVGTransformer transformer();

    @Override
    public RawSVGElement translate(double x, double y)
    {
        return this.transformer()
                   .translate(x, y);
    }

    @Override
    public RawSVGElement scale(double scaleX, double scaleY)
    {
        return this.transformer()
                   .scale(scaleX, scaleY);
    }

}
