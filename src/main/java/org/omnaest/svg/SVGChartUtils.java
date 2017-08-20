package org.omnaest.svg;

import org.omnaest.svg.chart.CoordinateChart;
import org.omnaest.svg.chart.types.SVGBarChart;
import org.omnaest.svg.chart.types.SVGClockChart;
import org.omnaest.svg.chart.types.SVGLineChart;

/**
 * SVG Utils to create simple charts
 * 
 * @see SVGUtils
 * @author omnaest
 */
public class SVGChartUtils
{
	public static CoordinateChart newLineChart(int width, int height)
	{
		return new SVGLineChart(width, height);
	}

	public static CoordinateChart newBarChart(int width, int height)
	{
		return new SVGBarChart(width, height);
	}

	public static CoordinateChart newClockChart(int width, int height)
	{
		return new SVGClockChart(width, height);
	}
}
