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

import java.util.stream.Stream;

import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;
import org.omnaest.svg.elements.base.SVGCompositeElement;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.vector.Vector;

public class SVGTextBox implements SVGCompositeElement
{
	private double	x1;
	private double	y1;
	private double	x2;
	private double	y2;
	private int		rotation;
	private String	text;
	private String	textColor	= "black";

	private int		borderSize			= 0;
	private String	backgroundColor		= "white";
	private double	backgroundOpacity	= 0.0;
	private String	borderColor			= "white";

	public SVGTextBox(double x1, double y1, double x2, double y2, String text)
	{
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.text = text;
	}

	public SVGTextBox setRotation(int rotation)
	{
		this.rotation = rotation;
		return this;
	}

	@Override
	public Stream<SVGElement> getElements()
	{
		Vector leftUpper = Vector.of(this.x1, this.y1);
		Vector rightLower = Vector.of(this.x2, this.y2);
		Vector rightUpper = Vector.of(this.x2, this.y1);

		Vector regularTextFlow = Vector	.of(this.x2, this.y2)
										.subtract(Vector.of(this.x1, this.y2));
		Vector rotatedTextFlow = regularTextFlow.rotateZ(-this.rotation);

		Vector c = rotatedTextFlow.subtract(regularTextFlow	.normVector()
															.multiply(rotatedTextFlow.absolute()));

		Vector c1 = rightUpper	.subtract(rightLower)
								.subtract(c);
		Vector c1Rotated = c1.rotateZ(-this.rotation);

		double fontSize = Math.min(rotatedTextFlow.absolute() * 2.5 / this.text.length(), c1Rotated.absolute());

		Vector textLocation = leftUpper	.add(c.inverse())
										.add(c1Rotated.inverse());

		double border = 0.1 * c1.absolute();
		double length = regularTextFlow.absolute();
		SVGText svgText = new SVGText((int) textLocation.getX(), (int) (textLocation.getY() - border), this.text)	.setRotation(-this.rotation)
																													.setColor(this.textColor)
																													.setFontSize((int) fontSize)
																													.setLength(length);

		SVGRectangle svgRectangle = new SVGRectangle(	(int) this.x1, (int) this.y1, (int) (this.x2 - this.x1),
														(int) (this.y2 - this.y1))	.setStrokeWidth(this.borderSize)
																					.setStrokeColor(this.borderColor)
																					.setFillColor(this.backgroundColor)
																					.setFillOpacity(this.backgroundOpacity);
		return Stream.of(svgRectangle, svgText);
	}

	public SVGTextBox setBorderSize(int borderSize)
	{
		this.borderSize = borderSize;
		return this;
	}

	public SVGTextBox setTextColor(String textColor)
	{
		this.textColor = textColor;
		return this;
	}

	public SVGTextBox setBorderColor(String borderColor)
	{
		this.borderColor = borderColor;
		return this;
	}

	public SVGTextBox setBackgroundColor(String backgroundColor)
	{
		this.backgroundColor = backgroundColor;
		return this;
	}

	public SVGTextBox setBackgroundOpacity(double backgroundOpacity)
	{
		this.backgroundOpacity = backgroundOpacity;
		return this;
	}

}
