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
package org.omnaest.svg.chart.types;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang.ObjectUtils;
import org.omnaest.svg.chart.common.AbstractChart;
import org.omnaest.svg.chart.common.AxisPoint;
import org.omnaest.svg.chart.common.IdAndLabel;
import org.omnaest.svg.chart.common.LineRenderer;
import org.omnaest.svg.chart.common.Point;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;

public class SVGClockChart extends AbstractChart
{

    public SVGClockChart(int width, int height)
    {
        super(width, height);
    }

    protected int calculatePadding()
    {
        return this.pixelFactor * 20;
    }

    private Vector calculatePosition(int rasterX, int rasterY)
    {
        double normValueY = this.extractNormValueFromAxisPoint(rasterY, this.verticalAxisValues.get(rasterY));
        return this.calculatePosition(rasterX, normValueY);
    }

    private Vector calculatePosition(int rasterX, double normValueY)
    {
        int padding = this.calculatePadding();

        int minDimension = Math.min(this.height, this.width);
        int chartAreaHeight = minDimension - 3 * padding;

        int y = (int) Math.round(padding + normValueY * chartAreaHeight / 2);
        return this.calculateCenterPoint()
                   .add(this.calculateDirectionVector(rasterX, y));
    }

    private Vector calculateDirectionVector(int rasterX, int y)
    {
        int horizontalAxisSize = this.horizontalAxisValues != null ? this.horizontalAxisValues.size() : 0;
        double angleStep = (360 - 20) / Math.max(1, horizontalAxisSize);
        return new Vector(0, y).rotate(180)
                               .rotate(angleStep * rasterX);
    }

    @Override
    protected void renderData(Stream<Stream<? extends Point<?, ?>>> data, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis,
                              Iterator<String> colors)
    {
        double maxValue = verticalAxis.values()
                                      .stream()
                                      .mapToDouble(value -> value)
                                      .max()
                                      .getAsDouble();

        data.forEach(points ->
        {
            String color = colors.next();
            this.getBoundedArea()
                .addAll(new LineRenderer(this.pixelFactor).renderLines(color, points.map(point ->
                {
                    String horizontalAxisId = ObjectUtils.toString(point.getX());
                    Object verticalAxisId = point.getY();
                    Integer rasterXPosition = horizontalAxis.get(horizontalAxisId);
                    Double rasterYNormValue = Optional.ofNullable(verticalAxis.get(verticalAxisId))
                                                      .orElse(0.0)
                            / maxValue;
                    if (rasterXPosition != null && rasterYNormValue != null)
                    {
                        return this.calculatePosition(rasterXPosition, rasterYNormValue);
                    }
                    else
                    {
                        return null;
                    }
                })
                                                                                    .filter(vector -> vector != null)));
        });
    }

    private Vector calculateCenterPoint()
    {
        int x = this.width / 2;
        int y = this.height / 2;
        return new Vector(x, y);
    }

    @Override
    protected void renderVerticalAxis()
    {
        //
        Vector centerPoint = this.calculateCenterPoint();
        int padding = this.calculatePadding();

        //
        int size = this.verticalAxisValues.size();

        for (int ii = 0; ii < size; ii++)
        {
            Vector position = this.calculatePosition(0, ii);

            //circle layers
            int r = centerPoint.getY() - position.getY();
            this.getBoundedArea()
                .add(new SVGCircle(centerPoint.getX(), centerPoint.getY(), r).setStrokeWidth(this.pixelFactor / 2)
                                                                             .setStrokeColor("gray")
                                                                             .setFillOpacity(0.1));

        }

        //
        {
            int x = centerPoint.getX();
            int y = centerPoint.getY();
            this.getBoundedArea()
                .add(new SVGRectangle(x - padding * 5 / 3 / 2, 0, padding * 5 / 3 / 2, y - padding / 2).setStrokeColor("white")
                                                                                                       .setFillColor("white")
                                                                                                       .setFillOpacity(1.0)
                                                                                                       .setStrokeWidth(this.pixelFactor / 2));
            this.getBoundedArea()
                .add(new SVGLine(x, y - padding, x, padding).setStrokeColor("gray")
                                                            .setStrokeWidth(this.pixelFactor / 2));

        }

        for (int ii = 0; ii < size; ii++)
        {
            Vector position = this.calculatePosition(0, ii);

            //stroke
            int x = position.getX();
            int y = position.getY();
            this.getBoundedArea()
                .add(new SVGLine(x - 2 * this.pixelFactor, y, x + 2 * this.pixelFactor, y).setStrokeWidth(this.pixelFactor)
                                                                                          .setStrokeColor("black"));

            //text
            AxisPoint<?> axisPoint = this.verticalAxisValues.get(ii);
            String label = axisPoint.getLabel();
            int fontSize = SVGText.DEFAULT_FONTSIZE * this.pixelFactor / 2;
            this.getBoundedArea()
                .add(new SVGText(x - padding * 4 / 3 / 2, y + fontSize / 3, label).setColor("black")
                                                                                  .setFontSize(fontSize));
        }

    }

    @Override
    protected void renderHorizontalAxis()
    {
        //
        int size = this.horizontalAxisValues.size();
        int modulo = Math.max(1, size / 10);
        int rasterY = this.verticalAxisValues.size() - 1;
        for (int ii = 0; ii < size; ii++)
        {
            //
            boolean hasLargeStroke = ii % modulo == 0;

            //
            Vector position = this.calculatePosition(ii, rasterY);
            Vector directionVector = this.calculateDirectionVector(ii, (hasLargeStroke ? 4 : 2) * this.pixelFactor);
            Vector outerPoint = position.add(directionVector);

            int x = position.getX();
            int y = position.getY();

            //stroke
            this.getBoundedArea()
                .add(new SVGLine(x, y, outerPoint.getX(), outerPoint.getY()).setStrokeWidth(this.pixelFactor)
                                                                            .setStrokeColor("black"));

            //text
            if (hasLargeStroke)
            {
                Vector outerTextPoint = outerPoint.add(directionVector);

                IdAndLabel idAndLabel = this.horizontalAxisValues.get(ii);
                String label = idAndLabel.getLabel();
                int fontSize = SVGText.DEFAULT_FONTSIZE * this.pixelFactor / 2;
                this.getBoundedArea()
                    .add(new SVGText(outerTextPoint.getX() - fontSize / 3, outerTextPoint.getY(), label).setColor("black")
                                                                                                        .setFontSize(fontSize)
                                                                                                        .setRotation(0));
            }

        }

    }

}
