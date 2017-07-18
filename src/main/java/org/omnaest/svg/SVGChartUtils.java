package org.omnaest.svg;

import org.omnaest.svg.chart.bar.SVGBarChart;

/**
 * SVG Utils to create simple charts
 * 
 * @see SVGUtils
 * @author omnaest
 */
public class SVGChartUtils
{
	public static SVGBarChart newBarChart(int width, int height)
	{
		return new SVGBarChart(width, height);
	}
}
