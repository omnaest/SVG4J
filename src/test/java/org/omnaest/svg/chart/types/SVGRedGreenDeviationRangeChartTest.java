package org.omnaest.svg.chart.types;

import java.io.File;

import org.junit.Test;
import org.omnaest.svg.SVGChartUtils;
import org.omnaest.svg.chart.RedGreenDeviationRangeChart;

public class SVGRedGreenDeviationRangeChartTest
{
    private RedGreenDeviationRangeChart chart = SVGChartUtils.newRedGreenDeviationRangeChart(1000, 500);

    @Test
    public void testSVGRedGreenDeviationRangeChart() throws Exception
    {
        this.chart.setHorizontalScale(0.0, 15.0)
                  .doNotRenderScalePoints()
                  .addGreenRange(6.0, 4.0)
                  .addYellowRangeBox(2.0, 10.0)
                  .setLabel("Label")
                  .addPoint(5.0)
                  .renderAsResult()
                  .writeToFile(new File("C:/Temp/svgRedGreenDeviationChartTest.svg"));
    }

}
