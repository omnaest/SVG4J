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
import org.omnaest.svg.model.RawSVGRectangle;
import org.omnaest.utils.NumberUtils;

public class SVGRectangle implements SVGElement
{
	private int	x;
	private int	y;
	private int	width;
	private int	height;

	private int		strokeWidth		= 1;
	private String	strokeColor		= "black";
	private String	fillColor		= "gray";
	private double	strokeOpacity	= 0.95;
	private double	fillOpacity		= 0.9;

	public SVGRectangle(int x, int y, int width, int height)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public SVGRectangle setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	public SVGRectangle setStrokeWidth(int strokeWidth)
	{
		this.strokeWidth = strokeWidth;
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

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	private String format(double value)
	{
		return NumberUtils	.formatter()
							.format(value);
	}

	@Override
	public RawSVGRectangle render()
	{
		return new RawSVGRectangle().setX("" + this.x)
									.setY("" + this.y)
									.setHeight("" + this.height)
									.setWidth("" + this.width)
									.setStyle("fill:" + this.fillColor + ";stroke-width:" + this.strokeWidth + ";stroke:" + this.strokeColor + ";fill-opacity:"
											+ this.format(this.fillOpacity) + ";stroke-opacity:" + this.format(this.strokeOpacity));

	}

	public SVGRectangle setStrokeOpacity(double opacity)
	{
		this.strokeOpacity = opacity;
		return this;
	}

	public SVGRectangle setFillOpacity(double opacity)
	{
		this.fillOpacity = opacity;
		return this;
	}

	public SVGRectangle setFillColor(String color)
	{
		this.fillColor = color;
		return this;
	}

}
