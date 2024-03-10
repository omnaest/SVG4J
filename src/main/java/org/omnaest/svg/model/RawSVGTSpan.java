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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "tspan")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGTSpan implements RawSVGElementWithContent
{
    @XmlValue
    private String content;

    @XmlAttribute
    private String x;

    @XmlAttribute
    private String y;

    @XmlAttribute
    private String dx;

    @XmlAttribute
    private String dy;

    @XmlAttribute(name = "class")
    private String cssClass;

    public String getX()
    {
        return this.x;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public String getY()
    {
        return this.y;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public String getDx()
    {
        return this.dx;
    }

    public void setDx(String dx)
    {
        this.dx = dx;
    }

    public String getDy()
    {
        return this.dy;
    }

    public void setDy(String dy)
    {
        this.dy = dy;
    }

    public String getCssClass()
    {
        return this.cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    @Override
    public String getContent()
    {
        return this.content;
    }

    public RawSVGTSpan setContent(String content)
    {
        this.content = content;
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGTSpan [content=" + this.content + ", x=" + this.x + ", y=" + this.y + ", dx=" + this.dx + ", dy=" + this.dy + ", cssClass="
                + this.cssClass + "]";
    }

}
