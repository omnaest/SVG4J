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
package org.omnaest.svg;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.omnaest.svg.chart.CoordinateChart;
import org.omnaest.svg.chart.common.DataSeries;
import org.omnaest.svg.chart.common.NumberPoint;
import org.omnaest.svg.other.DisplayResolution;

public class SVGChartUtilsTest2
{

    @Test
    //    @Ignore
    public void testNewLineChart() throws Exception
    {
        List<CoordinateChart> charts = Arrays.asList(SVGChartUtils.newLineChart(5000, 1000), SVGChartUtils.newBarChart(5000, 1000),
                                                     SVGChartUtils.newClockChart(1000, 1000), SVGChartUtils.newRadarChart(10000, 10000));

        for (CoordinateChart chart : charts)
        {

            List<Double> verticalLabels = Arrays.asList(0.0, 0.2, 0.4, 0.6, 0.8, 2.0);
            List<String> horizontalLabels = IntStream.range(0, 37)
                                                     .mapToObj(i -> StringUtils.repeat('a', 10) + i + StringUtils.repeat('z', 10))
                                                     .collect(Collectors.toList());

            List<DataSeries> data2 = Arrays.asList(DataSeries.of(this.generateDataPoints(horizontalLabels, verticalLabels))
                                                             .withLabel("Series1"),
                                                   DataSeries.of(this.generateDataPoints(horizontalLabels, verticalLabels))
                                                             .withLabel("Series2"));

            chart.addDataSeries(data2);

            String svg = chart.withScreenDimensions(DisplayResolution._800x600)
                              .render();
            FileUtils.writeStringToFile(new File("C:/Temp/charts/" + chart.getClass()
                                                                          .getSimpleName()
                    + ".svg"), svg, "utf-8");
        }
    }

    private Stream<NumberPoint> generateDataPoints(List<String> xLabels, List<Double> yLabels)
    {
        List<Double> shuffledYLabels = new ArrayList<>(yLabels);
        return xLabels.stream()
                      .map(x ->
                      {
                          Collections.shuffle(shuffledYLabels);
                          return new NumberPoint(x, shuffledYLabels.get(0));
                      });
    }

}
