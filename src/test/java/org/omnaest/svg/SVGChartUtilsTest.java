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
import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.svg.chart.CoordinateChart;
import org.omnaest.svg.chart.common.AxisOptions;
import org.omnaest.svg.chart.common.DataPoint;
import org.omnaest.svg.chart.common.IdAndLabel;

public class SVGChartUtilsTest
{

    @Test
    @Ignore
    public void testNewLineChart() throws Exception
    {
        List<CoordinateChart> charts = Arrays.asList(SVGChartUtils.newLineChart(5000, 1000), SVGChartUtils.newBarChart(5000, 1000),
                                                     SVGChartUtils.newClockChart(1000, 1000));

        for (CoordinateChart chart : charts)
        {

            List<String> verticalLabels = Arrays.asList("0%", "20%", "40%", "60%", "80%", "100%");
            List<String> horizontalLabels = IntStream.range(0, 37)
                                                     .mapToObj(i -> "Label " + i)
                                                     .collect(Collectors.toList());

            Stream<Stream<DataPoint>> data = Arrays.asList(this.generateDataPoints(horizontalLabels, verticalLabels),
                                                           this.generateDataPoints(horizontalLabels, verticalLabels))
                                                   .stream();
            chart.addVerticalAxis(verticalLabels.stream()
                                                .map(label -> new IdAndLabel(label, label))
                                                .collect(Collectors.toList()))
                 .addHorizontalAxis(horizontalLabels.stream()
                                                    .map(label -> new IdAndLabel(label, label))
                                                    .collect(Collectors.toList()))
                 .setHorizontalAxisOptions(new AxisOptions().setRotation(-45))
                 .addData(data);

            String svg = chart.render();
            FileUtils.writeStringToFile(new File("C:/Temp/charts/" + chart.getClass()
                                                                          .getSimpleName()
                    + ".svg"), svg, "utf-8");
        }
    }

    private Stream<DataPoint> generateDataPoints(List<String> xLabels, List<String> yLabels)
    {
        List<String> shuffledYLabels = new ArrayList<>(yLabels);
        return xLabels.stream()
                      .map(x ->
                      {
                          Collections.shuffle(shuffledYLabels);
                          return new DataPoint(x, shuffledYLabels.get(0));
                      });
    }

}
