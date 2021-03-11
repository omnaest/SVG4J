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
package org.omnaest.svg.elements;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.other.DisplayResolution;

public class SVGTextTest
{

	@Test
	@Ignore
	public void testSVGText() throws Exception
	{
		SVGDrawer drawer = SVGUtils	.getDrawer(1000, 600)
									.withScreenDimensions(DisplayResolution._1280x800);

		drawer	.add(new SVGRectangle(10, 10, 599, 399))
				.add(new SVGText(10, 410, "Test")	.setFontSize(400)
													.setLength(600));

		drawer	.renderAsResult()
				.writeToFile(new File("C:/Temp/svgTextText.svg"));
	}

}
