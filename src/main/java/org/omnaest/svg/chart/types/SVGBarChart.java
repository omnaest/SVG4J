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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.chart.common.AbstractCartesianCoordinateChart;
import org.omnaest.svg.chart.common.Point;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.base.SVGElement;

public class SVGBarChart extends AbstractCartesianCoordinateChart
{
    public SVGBarChart(int width, int height)
    {
        super(width, height);
    }

    private List<SVGElement> getLayer(int index, Map<Integer, List<SVGElement>> layers)
    {
        return layers.computeIfAbsent(index, (id) -> new ArrayList<>());
    }

    @Override
    protected void renderData(Stream<Stream<? extends Point<?, ?>>> data, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis,
                              Iterator<String> colors)
    {
        List<Stream<? extends Point<?, ?>>> dataStreams = data.collect(Collectors.toList());
        int numberOfDataLines = dataStreams.size();

        int padding = this.calculatePadding();
        int sliceWidth = (this.width - 2 * padding) / Math.max(1, this.horizontalAxisValues.size()) / Math.max(1, numberOfDataLines);

        AtomicInteger dataStreamIndexCounter = new AtomicInteger(0);
        dataStreams.forEach(points ->
        {
            int dataStreamIndex = dataStreamIndexCounter.getAndIncrement();

            String color = colors.next();

            Map<Integer, List<SVGElement>> layers = new TreeMap<>();

            points.map(point ->
            {
                String horizontalAxisId = Objects.toString(point.getX());
                Object verticalAxisId = point.getY();
                Integer rasterXPosition = horizontalAxis.get(horizontalAxisId);
                Double rasterYPosition = verticalAxisId instanceof Double ? (Double) verticalAxisId : verticalAxis.get(verticalAxisId);
                if (rasterXPosition != null && rasterYPosition != null)
                {
                    int x = this.calculateHorizontalPosition(rasterXPosition);
                    int y = this.calculateVerticalPosition(rasterYPosition);

                    return new Vector(x, y);
                }
                else
                {
                    return null;
                }
            })
                  .filter(vector -> vector != null)
                  .sorted((v1, v2) -> Integer.compare(v1.getX(), v2.getX()))
                  .forEach(vector ->
                  {
                      int x = vector.getX() + (sliceWidth * dataStreamIndex);
                      int y = vector.getY();
                      int width = sliceWidth;
                      int height = this.height - padding - y;

                      this.getLayer(1, layers)
                          .add(new SVGRectangle(x, y, width, height).setFillColor(color)
                                                                    .setStrokeColor("black")
                                                                    .setStrokeWidth(this.pixelFactor / 2));
                  });

            //
            this.getBoundedArea()
                .addAll(layers.values()
                              .stream()
                              .flatMap(layer -> layer.stream())
                              .collect(Collectors.toList()));

        });

    }

}
