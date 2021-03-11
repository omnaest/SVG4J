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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.omnaest.svg.SVGChartUtils;
import org.omnaest.svg.chart.EntryChart;
import org.omnaest.svg.chart.EntryChart.Entry;
import org.omnaest.utils.ComparatorUtils;
import org.omnaest.utils.NumberUtils;

public class SVGBoxMapChartTest
{

    @Test
    public void testAddData() throws Exception
    {
        SVGBoxMapChart chart = SVGChartUtils.newBoxMapChart(1000, 500)
                                            .setThresholdPrimary(0.5)
                                            .setThresholdSecondary(1.55);

        List<Entry> entries = new ArrayList<>();
        for (int ii = 0; ii < 99; ii++)
        {
            double random = 2.0 * ii / 100.0;
            entries.add(new Entry("text " + ii + "(" + NumberUtils.formatter()
                                                                  .asPercentage()
                                                                  .format(random)
                    + ")", random));
        }
        chart.addData(entries.stream()
                             .sorted(ComparatorUtils.builder()
                                                    .of(EntryChart.Entry.class)
                                                    .with(entry -> entry.getValue())
                                                    .build()));

        chart.renderAsResult()
             .writeToFile(new File("C:/Temp/svgBoxMapChartTest.svg"));
    }

}
