package org.omnaest.svg;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.omnaest.svg.elements.SVGLine;

public class SVGUtilsTest
{

	@Test
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

}
