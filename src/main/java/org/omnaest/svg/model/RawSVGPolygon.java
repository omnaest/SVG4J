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

@XmlRootElement(name = "polygon")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGPolygon extends RawSVGElement
{
	@XmlAttribute
	private String points;

	@XmlAttribute
	private String stroke;

	@XmlAttribute(name = "stroke-width")
	private String strokeWidth;

	@XmlAttribute
	private String fill;

	public String getPoints()
	{
		return this.points;
	}

	public RawSVGPolygon setPoints(String points)
	{
		this.points = points;
		return this;
	}

	public String getStroke()
	{
		return this.stroke;
	}

	public RawSVGPolygon setStroke(String stroke)
	{
		this.stroke = stroke;
		return this;
	}

	public String getFill()
	{
		return this.fill;
	}

	public RawSVGPolygon setFill(String fill)
	{
		this.fill = fill;
		return this;
	}

	public String getStrokeWidth()
	{
		return this.strokeWidth;
	}

	public RawSVGPolygon setStrokeWidth(String strokeWidth)
	{
		this.strokeWidth = strokeWidth;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGPolygon [points=" + this.points + ", stroke=" + this.stroke + "]";
	}

	@Override
	protected RawSVGTransformer transformer()
	{
		throw new UnsupportedOperationException();
	}

}
