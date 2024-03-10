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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "polyline")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGPolyline extends RawSVGElement
{
    @XmlAttribute
    private String points;

    @XmlAttribute
    private String stroke;

    @XmlAttribute
    private String fill;

    @XmlAttribute
    private String opacity;

    public String getPoints()
    {
        return this.points;
    }

    public RawSVGPolyline setPoints(String points)
    {
        this.points = points;
        return this;
    }

    public String getStroke()
    {
        return this.stroke;
    }

    public RawSVGPolyline setStroke(String stroke)
    {
        this.stroke = stroke;
        return this;
    }

    public String getFill()
    {
        return this.fill;
    }

    public RawSVGPolyline setFill(String fill)
    {
        this.fill = fill;
        return this;
    }

    public String getOpacity()
    {
        return this.opacity;
    }

    public RawSVGPolyline setOpacity(String opacity)
    {
        this.opacity = opacity;
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGPolyline [points=" + this.points + ", stroke=" + this.stroke + ", fill=" + this.fill + ", opacity=" + this.opacity + "]";
    }

    @Override
    protected RawSVGTransformer transformer()
    {
        throw new UnsupportedOperationException();
    }

}
