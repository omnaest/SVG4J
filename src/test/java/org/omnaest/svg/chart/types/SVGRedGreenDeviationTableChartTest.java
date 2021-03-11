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

import java.io.File;

import org.junit.Test;
import org.omnaest.svg.SVGChartUtils;
import org.omnaest.svg.chart.RedGreenDeviationTableChart;

public class SVGRedGreenDeviationTableChartTest
{
    private RedGreenDeviationTableChart chart = SVGChartUtils.newRedGreenDeviationTableChart(1000, 500);

    @Test
    public void testCreateRows() throws Exception
    {
        this.chart.createRows(5)
                  .forEach(chart ->
                  {
                      chart.setHorizontalScale(0.0, 15.0)
                           .doNotRenderScalePoints()
                           .addGreenRange(6.0, 4.0)
                           .addYellowRangeBox(2.0, 10.0)
                           .setLabel("Label")
                           .addPoint(5.0);
                  });
        this.chart.renderAsResult()
                  .writeToFile(new File("C:/Temp/svgRedGreenDeviationTableChartTest.svg"));
    }

}
