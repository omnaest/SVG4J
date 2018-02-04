package org.omnaest.svg.chart.types;

import java.io.File;

import org.junit.Test;
import org.omnaest.svg.SVGChartUtils;
import org.omnaest.svg.chart.RedGreenDeviationTableChart;

public class SVGRedGreenDeviationTableChartTest
{
    private RedGreenDeviationTableChart chart = SVGChartUtils.newRedGreenDeviationTableChart(1000, 500);

    @Test
    public void testCreateRows() throws Exception
    {
        this.chart.createRows(5)
                  .forEach(chart ->
                  {
                      chart.setHorizontalScale(0.0, 15.0)
                           .doNotRenderScalePoints()
                           .addGreenRange(6.0, 4.0)
                           .addYellowRangeBox(2.0, 10.0)
                           .setLabel("Label")
                           .addPoint(5.0);
                  });
        this.chart.renderAsResult()
                  .writeToFile(new File("C:/Temp/svgRedGreenDeviationTableChartTest.svg"));
    }

}
