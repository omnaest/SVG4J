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
package org.omnaest.svg.chart.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractChart2 extends AbstractChart
{

    public AbstractChart2(int width, int height)
    {
        super(width, height);
    }

    @Override
    protected void renderDataSeries(List<DataSeries> dataSeries, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis, Iterator<String> colors)
    {
        this.renderData(dataSeries, horizontalAxis, verticalAxis, colors);
    }

    @Override
    protected void renderData(Stream<Stream<? extends Point<?, ?>>> data, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis,
                              Iterator<String> colors)
    {
        this.renderData(data.map(points -> DataSeries.of(points.collect(Collectors.toList())))
                            .collect(Collectors.toList()),
                        horizontalAxis, verticalAxis, colors);

    }

    protected abstract void renderData(List<DataSeries> dataSeries, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis,
                                       Iterator<String> colors);
}
