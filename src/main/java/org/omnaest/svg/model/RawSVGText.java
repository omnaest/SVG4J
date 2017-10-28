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

@XmlRootElement(name = "text")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGText extends RawSVGElement
{
	@XmlAttribute
	private String x;

	@XmlAttribute
	private String y;

	@XmlAttribute
	private String fill;

	@XmlAttribute
	private double opacity;

	public RawSVGText setX(String x)
	{
		this.x = x;
		return this;
	}

	public String getX()
	{
		return this.x;
	}

	public String getY()
	{
		return this.y;
	}

	public RawSVGText setY(String y)
	{
		this.y = y;
		return this;
	}

	public String getFill()
	{
		return this.fill;
	}

	public RawSVGText setFill(String fill)
	{
		this.fill = fill;
		return this;
	}

	@Override
	public RawSVGText setStyle(String style)
	{
		super.setStyle(style);
		return this;
	}

	@Override
	public RawSVGText setTransform(String transform)
	{
		super.setTransform(transform);
		return this;
	}

	public String getText()
	{
		return this.content;
	}

	public RawSVGText setText(String text)
	{
		this.content = text;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGText [x=" + this.x + ", y=" + this.y + ", fill=" + this.fill + ", style=" + this.style + ", transform=" + this.transform + ", content="
				+ this.content + "]";
	}

	public RawSVGText setOpacity(double opacity)
	{
		this.opacity = opacity;
		return this;
	}

	public double getOpacity()
	{
		return this.opacity;
	}

}
