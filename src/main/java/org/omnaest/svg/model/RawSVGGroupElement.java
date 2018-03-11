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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "g")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGGroupElement extends RawSVGDefinitionElement implements RawSVGElementWithChildren
{

    @XmlAttribute
    private String transform;

    @XmlAttribute
    private String opacity;

    @XmlElementRef
    private List<RawSVGElement> elements;

    @XmlElement(name = "desc")
    private String description;

    @XmlElement
    private String title;

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return this.description;
    }

    public RawSVGGroupElement setDescription(String description)
    {
        this.description = description;
        return this;
    }

    @Override
    public List<RawSVGElement> getElements()
    {
        return this.elements;
    }

    public RawSVGGroupElement setElements(List<RawSVGElement> elements)
    {
        this.elements = elements;
        return this;
    }

    @Override
    public RawSVGGroupElement setTransform(String transform)
    {
        super.setTransform(transform);
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGGroupElement [id=" + this.getId() + ", transform=" + this.transform + ", elements=" + this.elements + "]";
    }

    public RawSVGGroupElement setOpacity(String opacity)
    {
        this.opacity = opacity;
        return this;
    }

    public String getOpacity()
    {
        return this.opacity;
    }

    @Override
    public RawSVGTransformer transformer()
    {
        return new IgnoringRawSVGTransformer(this);
    }

    public RawSVGGroupElement addElement(RawSVGElement element)
    {
        this.elements.add(element);
        return this;
    }

}
