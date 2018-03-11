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

@XmlRootElement(name = "path")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGPath extends RawSVGElement
{

    @XmlAttribute
    private String style;

    @XmlAttribute
    private String d;

    @XmlAttribute
    private String stroke;

    @Override
    public String getStyle()
    {
        return this.style;
    }

    @Override
    public RawSVGPath setStyle(String style)
    {
        super.setStyle(style);
        return this;
    }

    public String getD()
    {
        return this.d;
    }

    public void setD(String d)
    {
        this.d = d;
    }

    @Override
    public String toString()
    {
        return "RawSVGPath [id=" + this.getId() + ", style=" + this.style + ", d=" + this.d + "]";
    }

    @Override
    protected RawSVGTransformer transformer()
    {
        throw new UnsupportedOperationException();
    }

    public RawSVGPath setStroke(String stroke)
    {
        this.stroke = stroke;
        return this;
    }

    public String getStroke()
    {
        return this.stroke;
    }

}
