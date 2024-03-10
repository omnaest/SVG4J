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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 * Please register any subclass at {@link RawSVGRoot}
 * 
 * @see RawSVGRoot
 * @author omnaest
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class RawSVGElement implements RawSVGTransformer
{
    @XmlAttribute
    private String id;

    @XmlAttribute(name = "class")
    protected String cssClass;

    @XmlAttribute
    protected String style;

    @XmlAttribute
    protected String transform;

    //    @XmlValue
    //    protected String content;

    @XmlElementRef(type = RawSVGTSpan.class)
    @XmlMixed
    protected List<Object> rawContent = new ArrayList<>();

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

    @XmlTransient
    public String getContent()
    {
        return this.rawContent.stream()
                              .map(value ->
                              {
                                  if (value instanceof String)
                                  {
                                      return (String) value;
                                  }
                                  else if (value instanceof RawSVGElementWithContent)
                                  {
                                      return ((RawSVGElementWithContent) value).getContent();
                                  }
                                  else
                                  {
                                      return "";
                                  }
                              })
                              .collect(Collectors.joining());
    }

    public RawSVGElement setContent(String text)
    {
        this.rawContent.clear();
        if (text != null)
        {
            this.rawContent.add(text);
        }
        return this;
    }

    public RawSVGElement addTSpan(RawSVGTSpan span)
    {
        this.rawContent.add(span);
        return this;
    }

    //    public String getContent()
    //    {
    //        return this.content;
    //    }

    public String getCssClass()
    {
        return this.cssClass;
    }

    public List<Object> getRawContent()
    {
        return this.rawContent;
    }

    public void setRawContent(List<Object> rawContent)
    {
        this.rawContent = rawContent;
    }

    public RawSVGElement setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
        return this;
    }

    public String getId()
    {
        return this.id;
    }

    public RawSVGElement setId(String id)
    {
        this.id = id;
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
