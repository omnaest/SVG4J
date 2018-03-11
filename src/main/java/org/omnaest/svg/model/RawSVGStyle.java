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

@XmlRootElement(name = "style")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGStyle extends RawSVGDefinitionElement implements RawSVGElementWithContent
{
    @XmlAttribute
    private String type = "text/css";

    public String getType()
    {
        return this.type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public RawSVGStyle setContent(String text)
    {
        super.setContent(text);
        return this;
    }

    @Override
    public RawSVGTransformer transformer()
    {
        return new IgnoringRawSVGTransformer(this);
    }

}
