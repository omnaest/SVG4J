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

@XmlRootElement(name = "rect")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGRectangle extends RawSVGElement
{
	@XmlAttribute
	private String x;

	@XmlAttribute
	private String y;

	@XmlAttribute
	private String width;

	@XmlAttribute
	private String height;

	@XmlAttribute
	private String style;

	@XmlAttribute
	private String rx;

	@XmlAttribute
	private String ry;

	public String getRx()
	{
		return this.rx;
	}

	public void setRx(String rx)
	{
		this.rx = rx;
	}

	public String getRy()
	{
		return this.ry;
	}

	public void setRy(String ry)
	{
		this.ry = ry;
	}

	public String getX()
	{
		return this.x;
	}

	public RawSVGRectangle setX(String x)
	{
		this.x = x;
		return this;
	}

	public String getY()
	{
		return this.y;
	}

	public RawSVGRectangle setY(String y)
	{
		this.y = y;
		return this;
	}

	public String getWidth()
	{
		return this.width;
	}

	public RawSVGRectangle setWidth(String width)
	{
		this.width = width;
		return this;
	}

	public String getHeight()
	{
		return this.height;
	}

	public RawSVGRectangle setHeight(String height)
	{
		this.height = height;
		return this;
	}

	@Override
	public String getStyle()
	{
		return this.style;
	}

	@Override
	public RawSVGRectangle setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGRectangle [x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", style=" + this.style + "]";
	}

}
