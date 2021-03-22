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

package org.omnaest.svg.chart.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.ObjectUtils;
import org.omnaest.svg.SVGDrawer.SVGColor;
import org.omnaest.svg.chart.common.AbstractChart2;
import org.omnaest.svg.chart.common.AxisPoint;
import org.omnaest.svg.chart.common.DataSeries;
import org.omnaest.svg.chart.common.IdAndLabel;
import org.omnaest.svg.chart.common.LineRenderer;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;
import org.omnaest.svg.elements.SVGText.TextAnchor;
import org.omnaest.utils.MapperUtils;
import org.omnaest.utils.element.bi.BiElement;
import org.omnaest.utils.math.MathUtils;
import org.omnaest.utils.math.domain.DataPoints;

public class SVGRadarChart extends AbstractChart2
{
    public SVGRadarChart(int width, int height)
    {
        super(width, height);
    }

    protected int calculateInnerCirclePadding()
    {
        return this.pixelFactor * 12;
    }

    private Vector calculatePosition(int rasterX, int rasterY)
    {
        double normValueY = this.extractNormValueFromAxisPoint(rasterY, this.verticalAxisValues.get(rasterY));
        return this.calculatePositionFromNormValue(rasterX, normValueY);
    }

    private Vector calculatePositionFromAbsolute(int rasterX, double valueY)
    {
        double normValueY = this.extractNormValueFromAxisPoint(0, valueY);
        return this.calculatePositionFromNormValue(rasterX, normValueY);
    }

    private Vector calculatePositionFromNormValue(int rasterX, double normValueY)
    {
        int padding = this.calculateInnerCirclePadding();

        int minDimension = Math.min(this.height, this.width);
        int chartAreaHeight = (int) Math.round(minDimension - 3.5 * padding);

        int y = (int) Math.round(padding + normValueY * (chartAreaHeight - padding) / 2);
        return this.calculateCenterPoint()
                   .add(this.calculateDirectionVector(rasterX, y));
    }

    private Vector calculateDirectionVector(int rasterX, int y)
    {
        int horizontalAxisSize = this.horizontalAxisValues != null ? this.horizontalAxisValues.size() : 0;
        double angleStep = (360 - 0) / Math.max(1.0, horizontalAxisSize);
        return new Vector(0, y).rotate(180)
                               .rotate(angleStep * rasterX);
    }

    @Override
    protected void renderData(List<DataSeries> dataSeries, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis, Iterator<String> colors)
    {
        double maxValue = verticalAxis.values()
                                      .stream()
                                      .mapToDouble(value -> value)
                                      .max()
                                      .orElse(0.0);

        List<BiElement<String, String>> labelAndColorList = new ArrayList<>();

        dataSeries.forEach(points ->
        {
            String color = colors.next();

            labelAndColorList.add(BiElement.of(points.getLabel(), color));

            this.getBoundedArea()
                .addAll(new LineRenderer(this.pixelFactor).renderLines(color, points.stream()
                                                                                    .map(point ->
                                                                                    {
                                                                                        String horizontalAxisId = ObjectUtils.toString(point.getX());
                                                                                        Object verticalAxisId = point.getY();
                                                                                        Integer rasterXPosition = horizontalAxis.get(horizontalAxisId);
                                                                                        Double rasterYNormValue = Optional.ofNullable(verticalAxis.get(verticalAxisId))
                                                                                                                          .orElse(0.0)
                                                                                                / maxValue;
                                                                                        if (rasterXPosition != null && rasterYNormValue != null)
                                                                                        {
                                                                                            return this.calculatePositionFromNormValue(rasterXPosition,
                                                                                                                                       rasterYNormValue);
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            return null;
                                                                                        }
                                                                                    })
                                                                                    .filter(vector -> vector != null)));
        });

        this.renderLegend(labelAndColorList);

    }

    private void renderLegend(List<BiElement<String, String>> labelAndColorList)
    {
        this.getBoundedArea()
            .asVerticalSlices(4)
            .stream()
            .skip(3)
            .forEach(boundedArea ->
            {
                Iterator<BiElement<BiElement<String, String>, Integer>> labelAndColorAndIndexIterator = labelAndColorList.stream()
                                                                                                                         .map(MapperUtils.withIntCounter())
                                                                                                                         .iterator();
                boundedArea.asHorizontalSlices(40)
                           .stream()
                           .skip(4)
                           .filter(slice -> labelAndColorAndIndexIterator.hasNext())
                           .forEach(slice ->
                           {
                               BiElement<BiElement<String, String>, Integer> labelAndColorAndIndex = labelAndColorAndIndexIterator.next();

                               String label = labelAndColorAndIndex.getFirst()
                                                                   .getFirst();
                               String color = labelAndColorAndIndex.getFirst()
                                                                   .getSecond();
                               int paddingX = 5 * this.pixelFactor;
                               int y = this.pixelFactor;

                               slice.add(new SVGRectangle(0, 0, (int) slice.getWidth(), (int) slice.getHeight()).setFillOpacity(0.1)
                                                                                                                .setStrokeColor(SVGColor.WHITE.asSVGColorKey())
                                                                                                                .setFillColor(SVGColor.WHITE.asSVGColorKey()))
                                    .add(new SVGCircle(paddingX, y, this.pixelFactor).setStrokeColor(color)
                                                                                     .setStrokeWidth(2))
                                    .add(new SVGText(paddingX + this.pixelFactor * 2, y, label).setFontSize(this.pixelFactor * 2)
                                                                                               .setColor(color));
                           });
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

        double[] valuesY = MathUtils.analyze()
                                    .data(this.verticalAxisValues.stream()
                                                                 .map(AxisPoint::getId)
                                                                 .filter(id -> id instanceof Double)
                                                                 .mapToDouble(v -> (Double) v)
                                                                 .toArray())
                                    .splitIntoLinearBuckets(10)
                                    .stream()
                                    .mapToDouble(DataPoints::calculateAverage)
                                    .sorted()
                                    .toArray();

        //
        int size = valuesY.length; //this.verticalAxisValues.size();

        for (int ii = 0; ii < size; ii++)
        {
            double y = valuesY[ii];
            Vector position = this.calculatePositionFromAbsolute(0, y);

            //circle layers
            int r = centerPoint.getY() - position.getY();
            this.getBoundedArea()
                .add(new SVGCircle(centerPoint.getX(), centerPoint.getY(), r).setStrokeWidth(this.pixelFactor / 4)
                                                                             .setStrokeColor("gray")
                                                                             .setStrokeOpacity(0.3)
                                                                             .setFillOpacity(0.05));

        }

        {
            // inner black circle
            double y = 0.0;
            Vector position = this.calculatePositionFromAbsolute(0, y);
            int r = centerPoint.getY() - position.getY();
            this.getBoundedArea()
                .add(new SVGCircle(centerPoint.getX(), centerPoint.getY(), r).setStrokeWidth(this.pixelFactor / 4)
                                                                             .setStrokeColor("black")
                                                                             .setStrokeOpacity(1.0)
                                                                             .setFillOpacity(0.05));
        }

    }

    @Override
    protected void renderHorizontalAxis()
    {
        //
        int size = this.horizontalAxisValues.size();
        for (int ii = 0; ii < size; ii++)
        {
            //
            double normValueY = 1.0;
            Vector position = this.calculatePositionFromNormValue(ii, normValueY); //this.calculatePosition(ii, rasterY);
            Vector directionVector = this.calculateDirectionVector(ii, 4 * this.pixelFactor);
            Vector outerPoint = position.add(directionVector);

            int x = position.getX();
            int y = position.getY();

            Vector centerPoint = this.calculateCenterPoint();

            //stroke
            this.getBoundedArea()
                .add(new SVGLine(x, y, outerPoint.getX(), outerPoint.getY()).setStrokeWidth(this.pixelFactor)
                                                                            .setStrokeColor("black"));

            this.getBoundedArea()
                .add(new SVGLine(centerPoint.getX(), centerPoint.getY(), x, y).setStrokeWidth(1)
                                                                              .setStrokeColor("black"));
            //text
            Vector outerTextPoint = outerPoint.add(directionVector);

            IdAndLabel idAndLabel = this.horizontalAxisValues.get(ii);
            String label = idAndLabel.getLabel();
            int fontSize = SVGText.DEFAULT_FONTSIZE * this.pixelFactor / 3;

            boolean isTextInMiddle = Math.abs(1.0 * directionVector.getX()) / Math.max(1, Math.abs(1.0 * directionVector.getY())) < 0.1;
            boolean isTextOnRightSide = centerPoint.getX() < outerTextPoint.getX();
            TextAnchor textAnchor = isTextInMiddle ? TextAnchor.MIDDLE : isTextOnRightSide ? TextAnchor.START : TextAnchor.END;
            this.getBoundedArea()
                .add(new SVGText(outerTextPoint.getX() - fontSize / 3, outerTextPoint.getY(), label).setColor("black")
                                                                                                    .setFontSize(fontSize)
                                                                                                    .setRotation(0)
                                                                                                    .setTextAnchor(textAnchor));

        }

    }

}
