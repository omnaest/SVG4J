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
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.omnaest.svg.chart.RedGreenDeviationRangeChart;

public class SVGRangeChartTest
{

	@Test
	public void testNewRangeChart() throws Exception
	{

		RedGreenDeviationRangeChart rangeChart = SVGChartUtils	.newRedGreenDeviationRangeChart(5000, 300)
																.setHorizontalScale(5.0, 90.0)
																.setLabel("homovanillate");

		//
		rangeChart.addGreenRange(25.0, 15.0);
		rangeChart.addPoint(33.0);

		String svg = rangeChart.render();
		FileUtils.write(new File("C:/Temp/rangeChart.svg"), svg, StandardCharsets.UTF_8);
	}

}
