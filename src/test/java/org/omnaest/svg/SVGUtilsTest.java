package org.omnaest.svg;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.omnaest.svg.model.RawSVGRoot;
import org.omnaest.svg.utils.XMLHelper;

public class SVGUtilsTest
{

	@Test
	public void testParseFile() throws Exception
	{
		RawSVGRoot svgRoot = SVGUtils.parse(new File("C:\\Z\\anatomy images\\Female_shadow_anatomy_without_labels.svg"));

		//System.out.println(svgRoot);
		String xml = XMLHelper.serialize(svgRoot);
		System.out.println(xml);
		FileUtils.writeStringToFile(new File("C:/Temp/parse.svg"), xml, "utf-8");
	}

}
