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

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "a")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGAnkerElement extends RawSVGElement
{
    @XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
    private String href;

    @XmlElementRef
    private List<RawSVGElement> elements;

    public RawSVGAnkerElement()
    {
        super();
    }

    public List<RawSVGElement> getElements()
    {
        return this.elements;
    }

    public RawSVGAnkerElement setElements(List<RawSVGElement> elements)
    {
        this.elements = elements;
        return this;
    }

    public String getHref()
    {
        return this.href;
    }

    public RawSVGAnkerElement setHref(String href)
    {
        this.href = href;
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGAnkerElement [href=" + this.href + ", elements=" + this.elements + "]";
    }

    @Override
    public RawSVGTransformer transformer()
    {
        return new IgnoringRawSVGTransformer(this);
    }

}
