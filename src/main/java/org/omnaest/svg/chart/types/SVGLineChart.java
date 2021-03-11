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
import java.util.Objects;
import java.util.stream.Stream;

import org.omnaest.svg.chart.common.AbstractCartesianCoordinateChart;
import org.omnaest.svg.chart.common.LineRenderer;
import org.omnaest.svg.chart.common.Point;

public class SVGLineChart extends AbstractCartesianCoordinateChart
{
    public SVGLineChart(int width, int height)
    {
        super(width, height);
    }

    @Override
    protected void renderData(Stream<Stream<? extends Point<?, ?>>> data, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis,
                              Iterator<String> colors)
    {
        data.forEach(points ->
        {
            String color = colors.next();
            this.getBoundedArea()
                .addAll(new LineRenderer(this.pixelFactor).renderLines(color, points.map(point ->
                {
                    String horizontalAxisId = Objects.toString(point.getX());
                    Object verticalAxisId = point.getY();
                    Integer rasterXPosition = horizontalAxis.get(horizontalAxisId);
                    Double rasterYPosition = verticalAxis.get(verticalAxisId);
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
                                                                                    .sorted((v1, v2) -> Integer.compare(v1.getX(), v2.getX()))));
        });

    }

}
