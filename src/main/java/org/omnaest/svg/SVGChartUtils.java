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

import org.omnaest.svg.chart.CoordinateChart;
import org.omnaest.svg.chart.RangeChart;
import org.omnaest.svg.chart.RedGreenDeviationRangeChart;
import org.omnaest.svg.chart.TableChart;
import org.omnaest.svg.chart.types.SVGBarChart;
import org.omnaest.svg.chart.types.SVGBoxMapChart;
import org.omnaest.svg.chart.types.SVGClockChart;
import org.omnaest.svg.chart.types.SVGLineChart;
import org.omnaest.svg.chart.types.SVGRangeChart;
import org.omnaest.svg.chart.types.SVGRedGreenDeviationRangeChart;
import org.omnaest.svg.chart.types.SVGTableChart;

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

    public static RangeChart newRangeChart(int width, int height)
    {
        return new SVGRangeChart(width, height);
    }

    public static RedGreenDeviationRangeChart newRedGreenDeviationRangeChart(int width, int height)
    {
        return new SVGRedGreenDeviationRangeChart(width, height);
    }

    public static SVGBoxMapChart newBoxMapChart(int width, int height)
    {
        return new SVGBoxMapChart(width, height);
    }

    public static TableChart newTableChart()
    {
        return new SVGTableChart();
    }

}
