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
package org.omnaest.svg.elements;

import java.util.ArrayList;
import java.util.List;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.elements.base.SVGVector;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.svg.model.RawSVGPolygon;

public class SVGPolygon implements SVGElement
{
	private List<SVGVector>	locations		= new ArrayList<>();
	private String			strokeColor		= "red";
	private String			fillColor		= "red";
	private int				strokeWidth		= 3;
	private double			fillOpacity		= 1.0;
	private double			strokeOpacity	= 1.0;

	public SVGPolygon(List<SVGVector> locations)
	{
		super();
		this.locations.addAll(locations);
	}

	public String getStrokeColor()
	{
		return this.strokeColor;
	}

	public SVGPolygon setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	public SVGPolygon setFillColor(String fillColor)
	{
		this.fillColor = fillColor;
		return this;
	}

	public SVGPolygon setStrokeWidth(int strokeWidth)
	{
		this.strokeWidth = strokeWidth;
		return this;
	}

	public SVGPolygon setFillOpacity(double fillOpacity)
	{
		this.fillOpacity = fillOpacity;
		return this;
	}

	public SVGPolygon setStrokeOpacity(double strokeOpacity)
	{
		this.strokeOpacity = strokeOpacity;
		return this;
	}

	@Override
	public RawSVGElement render()
	{
		StringBuilder sb = new StringBuilder();
		for (SVGVector vector : this.locations)
		{
			int x = (int) vector.getX();
			int y = (int) vector.getY();
			sb.append(" " + x + " " + y);
		}

		StringBuilder style = new StringBuilder();
		style.append("fill:" + this.fillColor + ";");
		style.append("stroke:" + this.strokeColor + ";");
		style.append("stroke-width:" + this.strokeWidth + ";");
		style.append("stroke-opacity:" + this.strokeOpacity + ";");
		style.append("fill-opacity:" + this.fillOpacity + ";");

		return new RawSVGPolygon()	.setPoints(sb	.toString()
													.trim())
									.setStroke(this.strokeColor)
									.setStyle(style.toString());
	}

}
