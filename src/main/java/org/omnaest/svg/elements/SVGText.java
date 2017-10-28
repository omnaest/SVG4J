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
package org.omnaest.svg.elements;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGText;

public class SVGText implements SVGElement
{
	public static final int DEFAULT_FONTSIZE = 10;

	private int		x;
	private int		y;
	private String	text;
	private int		fontSize	= DEFAULT_FONTSIZE;
	private String	color		= "black";
	private int		rotation	= 0;
	private double	opacity		= 1.0;

	public SVGText(int x, int y, String text)
	{
		super();
		this.x = x;
		this.y = y;
		this.text = text;
	}

	public String getColor()
	{
		return this.color;
	}

	public SVGText setColor(String color)
	{
		this.color = color;
		return this;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public String getText()
	{
		return this.text;
	}

	public SVGText setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
		return this;
	}

	public int getFontSize()
	{
		return this.fontSize;
	}

	@Override
	public RawSVGText render()
	{
		return new RawSVGText()	.setX("" + this.x)
								.setY("" + this.y)
								.setFill(this.color)
								.setOpacity(this.opacity)
								.setStyle("font-size:" + this.fontSize + "px")
								.setTransform("rotate(" + this.rotation + "," + (this.x + this.fontSize / 2) + "," + (this.y + this.fontSize / 2) + ")")
								.setText(this.text);
	}

	public SVGText setRotation(int rotation)
	{
		this.rotation = rotation;
		return this;
	}

	public SVGElement setOpacity(double opacity)
	{
		this.opacity = opacity;
		return this;
	}

}
