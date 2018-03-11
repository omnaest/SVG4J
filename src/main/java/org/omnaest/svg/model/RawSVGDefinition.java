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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "defs")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ RawSVGLinearGradient.class, RawSVGRadialGradient.class, RawSVGStyle.class, RawSVGMarker.class, RawSVGGroupElement.class })
public class RawSVGDefinition extends RawSVGElement
{
    @XmlElementRef
    private List<RawSVGDefinitionElement> elements = new ArrayList<>();

    public List<RawSVGDefinitionElement> getElements()
    {
        return this.elements;
    }

    @SuppressWarnings("unchecked")
    public void setElements(List<? extends RawSVGDefinitionElement> elements)
    {
        this.elements = (List<RawSVGDefinitionElement>) elements;
    }

    @Override
    public String toString()
    {
        return "RawSVGDefinitionElement [id=" + this.getId() + ", elements=" + this.elements + "]";
    }

    public RawSVGDefinition addElement(RawSVGStyle element)
    {
        if (element != null)
        {
            this.elements.add(element);
        }
        return this;
    }

    @Override
    public RawSVGTransformer transformer()
    {
        return new IgnoringRawSVGTransformer(this);
    }
}
