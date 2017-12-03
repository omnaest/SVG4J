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
public class RawSVGRectangle extends RawSVGXYLocatedWithWidthAndHeightElement
{

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

	@Override
	public RawSVGRectangle setX(String x)
	{
		super.setX(x);
		return this;
	}

	@Override
	public RawSVGRectangle setY(String y)
	{
		super.setY(y);
		return this;
	}

	@Override
	public RawSVGRectangle setWidth(String width)
	{
		super.setWidth(width);
		return this;
	}

	@Override
	public RawSVGRectangle setHeight(String height)
	{
		super.setHeight(height);
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
		return "RawSVGRectangle [x=" + this.x + ", y=" + this.y + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", style=" + this.style + "]";
	}

}
