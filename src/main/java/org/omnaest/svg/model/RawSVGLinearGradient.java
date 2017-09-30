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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "linearGradient")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGLinearGradient extends RawSVGDefinitionElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute(namespace = "http://www.w3.org/1999/xlink")
	private String href;

	@XmlAttribute
	private String gradientTransform;

	@XmlAttribute
	private String gradientUnits;

	@XmlAttribute
	private String x1;

	@XmlAttribute
	private String y1;

	@XmlAttribute
	private String x2;

	@XmlAttribute
	private String y2;

	@XmlElementRef
	private List<RawSVGStopElement> stops;

	public String getHref()
	{
		return this.href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getGradientTransform()
	{
		return this.gradientTransform;
	}

	public void setGradientTransform(String gradientTransform)
	{
		this.gradientTransform = gradientTransform;
	}

	public String getGradientUnits()
	{
		return this.gradientUnits;
	}

	public void setGradientUnits(String gradientUnits)
	{
		this.gradientUnits = gradientUnits;
	}

	public String getX1()
	{
		return this.x1;
	}

	public void setX1(String x1)
	{
		this.x1 = x1;
	}

	public String getY1()
	{
		return this.y1;
	}

	public void setY1(String y1)
	{
		this.y1 = y1;
	}

	public String getX2()
	{
		return this.x2;
	}

	public void setX2(String x2)
	{
		this.x2 = x2;
	}

	public String getY2()
	{
		return this.y2;
	}

	public void setY2(String y2)
	{
		this.y2 = y2;
	}

	public List<RawSVGStopElement> getStops()
	{
		return this.stops;
	}

	public void setStops(List<RawSVGStopElement> stops)
	{
		this.stops = stops;
	}

	@Override
	public String toString()
	{
		return "RawSVGLinearGradient [id=" + this.id + ", href=" + this.href + ", gradientTransform=" + this.gradientTransform + ", gradientUnits="
				+ this.gradientUnits + ", x1=" + this.x1 + ", y1=" + this.y1 + ", x2=" + this.x2 + ", y2=" + this.y2 + ", stops=" + this.stops + ", style="
				+ this.style + ", transform=" + this.transform + ", content=" + this.content + "]";
	}

}
