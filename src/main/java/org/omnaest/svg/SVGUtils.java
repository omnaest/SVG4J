package org.omnaest.svg;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.omnaest.svg.model.RawSVGRoot;
import org.omnaest.svg.utils.XMLHelper;

/**
 * {@link SVGUtils}
 * 
 * @see #getDrawer(int, int)
 * @see SVGChartUtils
 * @author omnaest
 */
public class SVGUtils
{
	/**
	 * @see SVGDrawer
	 * @param width
	 * @param height
	 * @return
	 */
	public static SVGDrawer getDrawer(int width, int height)
	{
		return new SVGDrawer(width, height);
	}

	public static RawSVGRoot parse(File file) throws IOException
	{
		return parse(FileUtils.readFileToString(file, "utf-8"));
	}

	public static RawSVGRoot parse(String svg)
	{
		return XMLHelper.parse(svg, RawSVGRoot.class);
	}
}
