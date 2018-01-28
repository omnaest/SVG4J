package org.omnaest.svg.chart.types;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.omnaest.svg.SVGChartUtils;
import org.omnaest.svg.chart.TableChart;
import org.omnaest.svg.chart.TableChart.CoordinateEntry;

public class SVGTableChartTest
{

    @Test
    public void testRenderAsResult() throws Exception
    {
        TableChart chart = SVGChartUtils.newTableChart()
                                        .withNegativeColor("white");

        chart.addData(this.generateDataEntries()
                          .stream())
             .renderAsResult()
             .writeToFile(new File("C:/Temp/svgTableChartTest.svg"));
    }

    private List<CoordinateEntry> generateDataEntries()
    {
        List<CoordinateEntry> retlist = new ArrayList<>();

        for (int ii = 0; ii < 10; ii++)
        {
            for (int jj = 0; jj < 10; jj++)
            {
                retlist.add(new CoordinateEntry("" + ii + ":" + jj, 1.0, "col" + ii, "row" + jj));
            }
        }
        return retlist;
    }

}
