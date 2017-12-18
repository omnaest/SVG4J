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
import org.omnaest.svg.chart.EntryChart.Entry;

public class SVGBoxMapChartTest
{

	@Test
	public void testAddData() throws Exception
	{
		SVGBoxMapChart chart = SVGChartUtils.newBoxMapChart(1000, 500);

		List<Entry> entries = new ArrayList<>();
		for (int ii = 0; ii < 99; ii++)
		{
			entries.add(new Entry("text " + ii, Math.random()));
		}
		chart.addData(entries.stream());

		chart	.renderAsResult()
				.writeToFile(new File("C:/Temp/svgBoxMapChartTest.svg"));
	}

}
