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
package org.omnaest.svg.elements.composite;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.omnaest.svg.elements.SVGPolygon;
import org.omnaest.svg.elements.SVGText;
import org.omnaest.svg.elements.base.SVGCompositeElement;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.elements.base.SVGVector;
import org.omnaest.vector.Vector;

public class SVGFlowArrow implements SVGCompositeElement
{
	private double	x1;
	private double	y1;
	private double	x2;
	private double	y2;

	private Supplier<Double>	length			= () -> new Vector(this.x2, this.y2).subtract(new Vector(this.x1, this.y1))
																					.absolute();
	private Supplier<Double>	arrowWidth		= () -> this.length.get() * 0.1;
	private double				arrowFlatness	= 0.1;
	private Supplier<Double>	strokeWidth		= () -> this.arrowWidth.get() * 0.1;

	private String	fillColor	= "white";
	private String	strokeColor	= "black";
	private double	fillOpacity	= 0.5;

	private String text;

	public SVGFlowArrow(double x1, double y1, double x2, double y2)
	{
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public SVGFlowArrow setArrowFlatness(double arrowFlatness)
	{
		this.arrowFlatness = arrowFlatness;
		return this;
	}

	public SVGFlowArrow setFillOpacity(double fillOpacity)
	{
		this.fillOpacity = fillOpacity;
		return this;
	}

	public SVGFlowArrow setArrowWidth(double width)
	{
		this.arrowWidth = () -> width;
		return this;
	}

	public SVGFlowArrow setRelativeArrowWidth(double relativeWidth)
	{
		this.arrowWidth = () -> this.length.get() * relativeWidth;
		return this;
	}

	public SVGFlowArrow setStrokeWidth(double strokeWidth)
	{
		this.strokeWidth = () -> strokeWidth;
		return this;
	}

	public SVGFlowArrow setFillColor(String fillColor)
	{
		this.fillColor = fillColor;
		return this;
	}

	public SVGFlowArrow setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	@Override
	public Stream<SVGElement> getElements()
	{
		Vector sourcePosition = new Vector(this.x1, this.y1);
		Vector targetPosition = new Vector(this.x2, this.y2);
		Vector delta = targetPosition.subtract(sourcePosition);

		Double width = this.arrowWidth.get();

		Vector upperLeft = sourcePosition.add(delta	.normVector()
													.rotateZ(90)
													.multiply(width / 2));
		Vector lowerLeft = sourcePosition.add(delta	.normVector()
													.rotateZ(-90)
													.multiply(width / 2));
		double arrowStart = 1.0 - this.arrowFlatness;
		Vector upperRight = upperLeft.add(delta.multiply(arrowStart));
		Vector lowerRight = lowerLeft.add(delta.multiply(arrowStart));

		Vector middleRight = sourcePosition.add(delta);

		List<SVGVector> locations = Arrays.asList(	SVGVector.valueOf(upperLeft), SVGVector.valueOf(upperRight), SVGVector.valueOf(middleRight),
													SVGVector.valueOf(lowerRight), SVGVector.valueOf(lowerLeft));
		SVGPolygon svgPolygon = new SVGPolygon(locations)	.setStrokeColor(this.strokeColor)
															.setFillColor(this.fillColor)
															.setFillOpacity(this.fillOpacity)
															.setStrokeWidth(this.strokeWidth.get()
																							.intValue());

		Vector textPosition = upperLeft;
		SVGText svgText = new SVGText((int) textPosition.getX(), (int) textPosition.getY(), this.text)	.setFontSize((int) (width.intValue() * arrowStart))
																										.setRotation((int) delta.determineAngleToXAxis());
		return Stream.of(svgPolygon, svgText);
	}

	public SVGFlowArrow setText(String text)
	{
		this.text = text;
		return this;
	}

}
