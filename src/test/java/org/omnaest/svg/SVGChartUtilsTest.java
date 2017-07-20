package org.omnaest.svg;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.omnaest.svg.chart.CoordinateChart;
import org.omnaest.svg.chart.common.AxisOptions;
import org.omnaest.svg.chart.common.DataPoint;
import org.omnaest.svg.chart.common.IdAndLabel;

public class SVGChartUtilsTest
{

	@Test
	public void testNewLineChart() throws Exception
	{
		CoordinateChart chart = SVGChartUtils.newClockChart(5000, 5000);

		List<String> verticalLabels = Arrays.asList("20%", "40%", "60%", "80%", "100%");
		List<String> horizontalLabels = IntStream	.range(0, 37)
													.mapToObj(i -> "" + i)
													.collect(Collectors.toList());

		Stream<Stream<DataPoint>> data = Arrays	.asList(this.generateDataPoints(horizontalLabels, verticalLabels),
														this.generateDataPoints(horizontalLabels, verticalLabels))
												.stream();
		chart	.addVerticalAxis(verticalLabels	.stream()
												.map(label -> new IdAndLabel(label, label))
												.collect(Collectors.toList()))
				.addHorizontalAxis(	horizontalLabels.stream()
													.map(label -> new IdAndLabel(label, label))
													.collect(Collectors.toList()),
									new AxisOptions().setRotation(-45))
				.addData(data);

		String svg = chart.render();
		FileUtils.writeStringToFile(new File("C:/Temp/test.svg"), svg, "utf-8");
	}

	private Stream<DataPoint> generateDataPoints(List<String> xLabels, List<String> yLabels)
	{
		List<String> shuffledYLabels = new ArrayList<>(yLabels);
		return xLabels	.stream()
						.map(x ->
						{
							Collections.shuffle(shuffledYLabels);
							return new DataPoint(x, shuffledYLabels.get(0));
						});
	}

}
