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
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.svg.elements.SVGAnker;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;

public class SVGUtilsTest
{

	@Test
	@Ignore
	public void testParseFile() throws Exception
	{
		File sourceFile = new File("C:\\Z\\anatomy images\\Female_shadow_anatomy_without_labels.svg");
		String svg = SVGUtils	.getDrawer(sourceFile)
								.modifyRawElements((elements) -> elements	.skip(0)
																			.limit(1))
								.addRawElements(SVGUtils.getDrawer(sourceFile)
														.modifyRawElements((elements) -> elements	.skip(1)
																									.limit(10)))
								.add(new SVGLine(640, 0, 640, 2000).setStrokeWidth(4))
								.render();

		System.out.println(svg);
		FileUtils.writeStringToFile(new File("C:/Temp/parse.svg"), svg, "utf-8");
	}

	@Test
	@Ignore
	public void testAnker() throws IOException
	{
		SVGUtils.getDrawer(100, 100)
				.setScreenDimensions(200, 200)
				.enableCSSForAnkerLinks()
				.add(new SVGAnker("http://www.google.de")	.addElement(new SVGRectangle(50, 50, 20, 20).setFillColor("blue"))
															.addElement(new SVGText(10, 10, "Google")))
				.renderAsResult()
				.writeToFile(new File("C:/Temp/svgAnkerTest.svg"));

	}
}
