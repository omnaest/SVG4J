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
package org.omnaest.svg.elements.composite;

import java.io.File;

import org.junit.Test;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.other.DisplayResolution;

public class SVGFlowArrowTest
{

	@Test
	public void testGetElements() throws Exception
	{
		double x1 = 10;
		double y1 = 10;
		double x2 = 200;
		double y2 = 100;
		double x3 = 300;
		double y3 = 50;
		SVGUtils.getDrawer(1000, 300)
				.withScreenDimensions(DisplayResolution._1280x800)
				.add(new SVGFlowArrow(x1, y1, x2, y2)	.setRelativeWidth(0.4)
														.setText("Flow 1"))
				.add(new SVGFlowArrow(x2, y2, x3, y3)	.setRelativeWidth(0.4)
														.setText("Flow 2"))
				.renderAsResult()
				.writeToFile(new File("C:/Temp/arrowChartTest.svg"));

	}

}
