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

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGRectangle;

public class SVGTextBoxTest
{
	@Test
	@Ignore
	public void testSVGTextBox() throws Exception
	{
		SVGDrawer drawer = SVGUtils.getDrawer(200, 100);

		drawer.add(new SVGRectangle(10, 10, 150 - 10, 80 - 10).setFillOpacity(0.1));
		drawer.add(new SVGCircle(150, 80, 5));
		drawer.add(new SVGTextBox(10, 10, 150, 80, "Test").setRotation(0));

		drawer	.renderAsResult()
				.writeToFile(new File("C:/Temp/svgTextBoxTest.svg"));
	}
}
