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
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.omnaest.svg.SVGChartUtils;
import org.omnaest.svg.chart.TableChart;
import org.omnaest.svg.chart.TableChart.CoordinateEntry;

public class SVGTableChartTest
{

    @Test
    public void testRenderAsResult() throws Exception
    {
        TableChart chart = SVGChartUtils.newTableChart()
                                        .withNegativeColor("white");

        chart.addData(this.generateDataEntries()
                          .stream())
             .renderAsResult()
             .writeToFile(new File("C:/Temp/svgTableChartTest.svg"));
    }

    private List<CoordinateEntry> generateDataEntries()
    {
        List<CoordinateEntry> retlist = new ArrayList<>();

        for (int ii = 0; ii < 10; ii++)
        {
            for (int jj = 0; jj < 10; jj++)
            {
                retlist.add(new CoordinateEntry("" + ii + ":" + jj, 1.0, "col" + ii, "row" + jj));
            }
        }
        return retlist;
    }

}
