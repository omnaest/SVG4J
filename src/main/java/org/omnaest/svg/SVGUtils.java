package org.omnaest.svg;

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
}
