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

	public static SVGDrawer getDrawer(File svgFile) throws IOException
	{
		return new SVGDrawer(parse(svgFile));
	}

	public static SVGDrawer getDrawer(String svg)
	{
		return new SVGDrawer(parse(svg));
	}

	protected static RawSVGRoot parse(File file) throws IOException
	{
		return parse(FileUtils.readFileToString(file, "utf-8"));
	}

	protected static RawSVGRoot parse(String svg)
	{
		return XMLHelper.parse(svg, RawSVGRoot.class);
	}
}
