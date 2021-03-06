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

import java.util.stream.Stream;

import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;
import org.omnaest.svg.elements.SVGText.TextAnchor;
import org.omnaest.svg.elements.base.SVGCompositeElement;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.vector.Vector;

public class SVGTextBox implements SVGCompositeElement
{
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private int    rotation;
    private String text;
    private String textColor = "black";

    private int    borderSize        = 0;
    private String backgroundColor   = "white";
    private double backgroundOpacity = 0.0;
    private String borderColor       = "white";
    private double borderOpacity     = 1.0;

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
        int margin = this.borderSize * 2;
        Vector leftUpper = Vector.of(this.x1 + margin, this.y1 + margin);
        Vector leftLower = Vector.of(this.x1 + margin, this.y2 - margin);
        Vector rightLower = Vector.of(this.x2 - margin, this.y2 - margin);
        Vector rightUpper = Vector.of(this.x2 - margin, this.y1 + margin);

        Vector regularTextFlow = rightLower.subtract(leftLower);
        Vector orthogonalTextFlow = leftUpper.subtract(leftLower);

        Vector center = leftLower.add(regularTextFlow.multiply(0.5))
                                 .add(orthogonalTextFlow.multiply(0.5));

        Vector diagonalTextFlow;
        {
            Vector rotatedTextFlow = regularTextFlow.rotateZ(this.rotation);
            Vector diagonalTextFlowFromBottom = rotatedTextFlow.normVector()
                                                               .multiply(rotatedTextFlow.absolute() * regularTextFlow.absolute()
                                                                       / rotatedTextFlow.multiplyScalar(regularTextFlow.normVector()));

            Vector diagonalTextFlowFromLeft = rotatedTextFlow.normVector()
                                                             .multiply(rotatedTextFlow.absolute() * orthogonalTextFlow.absolute()
                                                                     / rotatedTextFlow.multiplyScalar(orthogonalTextFlow.normVector()));

            if (Double.isNaN(diagonalTextFlowFromLeft.absolute()))
            {
                diagonalTextFlow = diagonalTextFlowFromBottom;
            }
            else if (Double.isNaN(diagonalTextFlowFromBottom.absolute()))
            {
                diagonalTextFlow = diagonalTextFlowFromLeft;
            }
            else if (diagonalTextFlowFromBottom.absolute() < diagonalTextFlowFromLeft.absolute())
            {
                diagonalTextFlow = diagonalTextFlowFromBottom;
            }
            else
            {
                diagonalTextFlow = diagonalTextFlowFromLeft;
            }
        }

        //
        Vector textLocation = center;
        double length = diagonalTextFlow.absolute() * 0.8;

        double referenceFontSize = length;
        double fontSize = referenceFontSize / Math.round(this.text.length() / 2.0);
        SVGText svgText = new SVGText((int) textLocation.getX(), (int) textLocation.getY(), this.text).setRotation(-this.rotation)
                                                                                                      .setColor(this.textColor)
                                                                                                      .setFontSize((int) Math.round(fontSize))
                                                                                                      .setLength(length)
                                                                                                      .setTextAnchor(TextAnchor.MIDDLE);

        SVGRectangle svgRectangle = new SVGRectangle((int) this.x1, (int) this.y1, (int) (this.x2 - this.x1),
                                                     (int) (this.y2 - this.y1)).setStrokeWidth(this.borderSize)
                                                                               .setStrokeColor(this.borderColor)
                                                                               .setStrokeOpacity(this.borderOpacity)
                                                                               .setFillColor(this.backgroundColor)
                                                                               .setFillOpacity(this.backgroundOpacity);
        //        SVGRectangle innerSvgRectangle = new SVGRectangle((int) (leftUpper.getX() + regularTextFlow.absolute() * 0.1), (int) leftUpper.getY(), (int) (length),
        //                                                          (int) (orthogonalTextFlow.absolute())).setStrokeWidth(2)
        //                                                                                                .setStrokeColor("green")
        //                                                                                                .setFillOpacity(0.0);
        //        SVGCircle svgCircle = new SVGCircle((int) textLocation.getX(), (int) textLocation.getY(), 5);
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

    public SVGTextBox setBorderOpacity(double borderOpacity)
    {
        this.borderOpacity = borderOpacity;
        return this;
    }

}
