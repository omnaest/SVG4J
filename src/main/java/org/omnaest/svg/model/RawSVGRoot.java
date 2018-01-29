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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.omnaest.utils.NumberUtils;

@XmlSeeAlso({ RawSVGImageElement.class,
              RawSVGGroupElement.class,
              RawSVGLine.class,
              RawSVGCircle.class,
              RawSVGPath.class,
              RawSVGPolygon.class,
              RawSVGPolyline.class,
              RawSVGRectangle.class,
              RawSVGText.class,
              RawSVGDefinition.class,
              RawSVGAnkerElement.class,
              RawSVGStyle.class,
              RawSVGEllipse.class })
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "svg")
public class RawSVGRoot
{
    @XmlAttribute
    private String id;

    @XmlAttribute
    private String width;

    @XmlAttribute
    private String height;

    @XmlAttribute
    private String version;

    @XmlElementRef
    private List<RawSVGScript> scripts;

    @XmlElementRef
    private List<RawSVGElement> elements;

    @XmlAttribute
    private String baseProfile;

    @XmlAttribute
    private String viewBox;

    @XmlAttribute(name = "class")
    private String cssClass;

    public String getCssClass()
    {
        return this.cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getWidth()
    {
        return this.width;
    }

    public RawSVGRoot setWidth(String width)
    {
        this.width = width;
        return this;
    }

    public String getHeight()
    {
        return this.height;
    }

    public RawSVGRoot setHeight(String height)
    {
        this.height = height;
        return this;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public List<RawSVGElement> getElements()
    {
        return this.elements;
    }

    public void setElements(List<RawSVGElement> elements)
    {
        this.elements = elements;
    }

    @Override
    public String toString()
    {
        return "RawSVGRoot [id=" + this.id + ", width=" + this.width + ", height=" + this.height + ", version=" + this.version + ", elements=" + this.elements
                + ", baseProfile=" + this.baseProfile + ", viewBox=" + this.viewBox + "]";
    }

    public void setBaseProfile(String baseProfile)
    {
        this.baseProfile = baseProfile;

    }

    public String getBaseProfile()
    {
        return this.baseProfile;
    }

    public void setViewBox(String viewBox)
    {
        this.viewBox = viewBox;

    }

    public String getViewBox()
    {
        return this.viewBox;
    }

    public void addScript(String script)
    {
        if (this.scripts == null)
        {
            this.scripts = new ArrayList<>();
        }

        this.scripts.add(new RawSVGScript().setContent(script));

    }

    public List<RawSVGScript> getScripts()
    {
        return this.scripts;
    }

    public void setScripts(List<RawSVGScript> scripts)
    {
        this.scripts = scripts;
    }

    public static class ViewBox
    {
        private double x;
        private double y;
        private double width;
        private double height;

        public ViewBox(String viewBox)
        {
            super();

            String[] tokens = StringUtils.splitPreserveAllTokens(viewBox, " ");
            this.x = NumberUtils.toDouble(tokens[0]);
            this.y = NumberUtils.toDouble(tokens[1]);
            this.width = NumberUtils.toDouble(tokens[2]);
            this.height = NumberUtils.toDouble(tokens[3]);
        }

        public double getX()
        {
            return this.x;
        }

        public double getY()
        {
            return this.y;
        }

        public double getWidth()
        {
            return this.width;
        }

        public double getHeight()
        {
            return this.height;
        }

    }

    @XmlTransient
    public ViewBox getParsedViewBox()
    {
        return new ViewBox(this.viewBox);
    }
}
