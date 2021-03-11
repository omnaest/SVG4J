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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "marker")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGMarker extends RawSVGDefinitionElement
{
    @XmlAttribute
    private String refX;

    @XmlAttribute
    private String orient;

    @XmlAttribute
    private String markerUnits;

    @XmlAttribute
    private String overflow;

    @XmlElementRef
    private RawSVGUse use;

    public RawSVGUse getUse()
    {
        return this.use;
    }

    public void setUse(RawSVGUse use)
    {
        this.use = use;
    }

    public String getRefX()
    {
        return this.refX;
    }

    public void setRefX(String refX)
    {
        this.refX = refX;
    }

    public String getOrient()
    {
        return this.orient;
    }

    public void setOrient(String orient)
    {
        this.orient = orient;
    }

    public String getMarkerUnits()
    {
        return this.markerUnits;
    }

    public void setMarkerUnits(String markerUnits)
    {
        this.markerUnits = markerUnits;
    }

    public String getOverflow()
    {
        return this.overflow;
    }

    public void setOverflow(String overflow)
    {
        this.overflow = overflow;
    }

    @Override
    public String toString()
    {
        return "RawSVGMarker [refX=" + this.refX + ", orient=" + this.orient + ", markerUnits=" + this.markerUnits + ", overflow=" + this.overflow + ", use="
                + this.use + "]";
    }

    @Override
    protected RawSVGTransformer transformer()
    {
        return new DefaultRawSVGTransformer(this);
    }

}
