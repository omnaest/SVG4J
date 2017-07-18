package org.omnaest.svg;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.omnaest.svg.chart.bar.IdAndLabel;
import org.omnaest.svg.chart.bar.SVGBarChart;
import org.omnaest.svg.chart.bar.SVGBarChart.DataPoint;

public class SVGChartUtilsTest
{

	@Test
	public void testNewBarChart() throws Exception
	{
		SVGBarChart barChart = SVGChartUtils.newBarChart(1000, 1000);

		List<String> verticalLabels = Arrays.asList("A", "B", "C");
		List<String> horizontalLabels = Arrays.asList("1", "2", "3", "4");

		Stream<Stream<DataPoint>> data = Arrays	.asList(Arrays	.asList(this.generatePoint("1", "B"), this.generatePoint("2", "A"),
																		this.generatePoint("3", "C"),
																		this.generatePoint("4", "A"))
																.stream(),
														Arrays	.asList(this.generatePoint("1", "A"), this.generatePoint("2", "C"),
																		this.generatePoint("3", "C"),
																		this.generatePoint("4", "B"))
																.stream())
												.stream();
		barChart.addVerticalAxis(verticalLabels	.stream()
												.map(label -> new IdAndLabel(label, label))
												.collect(Collectors.toList()))
				.addHorizontalAxis(horizontalLabels	.stream()
													.map(label -> new IdAndLabel(label, label))
													.collect(Collectors.toList()))
				.addData(data);

		String svg = barChart.render();
		FileUtils.writeStringToFile(new File("C:/Temp/test.svg"), svg, "utf-8");
	}

	private DataPoint generatePoint(String x, String y)
	{
		return new DataPoint(x, y);
	}

}
