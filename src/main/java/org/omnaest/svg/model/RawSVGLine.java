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

@XmlRootElement(name = "line")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGLine extends RawSVGX1X2Y1Y2LocatedElement
{
	@XmlAttribute
	private String stroke;

	@XmlAttribute(name = "stroke-width")
	private String strokeWidth;

	@XmlAttribute
	private String style;

	@Override
	public RawSVGLine setX1(String x1)
	{
		super.setX1(x1);
		return this;
	}

	@Override
	public RawSVGLine setY1(String y1)
	{
		super.setY1(y1);
		return this;
	}

	@Override
	public RawSVGLine setX2(String x2)
	{
		super.setX2(x2);
		return this;
	}

	@Override
	public RawSVGLine setY2(String y2)
	{
		super.setY2(y2);
		return this;
	}

	public String getStroke()
	{
		return this.stroke;
	}

	public RawSVGLine setStroke(String stroke)
	{
		this.stroke = stroke;
		return this;
	}

	public String getStrokeWidth()
	{
		return this.strokeWidth;
	}

	public RawSVGLine setStrokeWidth(String strokeWidth)
	{
		this.strokeWidth = strokeWidth;
		return this;
	}

	@Override
	public String getStyle()
	{
		return this.style;
	}

	@Override
	public RawSVGLine setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGLine [stroke=" + this.stroke + ", strokeWidth=" + this.strokeWidth + ", style=" + this.style + ", x1=" + this.x1 + ", y1=" + this.y1
				+ ", x2=" + this.x2 + ", y2=" + this.y2 + ", transform=" + this.transform + ", content=" + this.content + "]";
	}

}
