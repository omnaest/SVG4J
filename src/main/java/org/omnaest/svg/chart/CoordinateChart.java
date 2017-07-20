package org.omnaest.svg.chart;

import java.util.List;
import java.util.stream.Stream;

import org.omnaest.svg.chart.common.AxisOptions;
import org.omnaest.svg.chart.common.DataPoint;
import org.omnaest.svg.chart.common.IdAndLabel;

public interface CoordinateChart
{

	CoordinateChart addVerticalAxis(List<IdAndLabel> values);

	CoordinateChart addHorizontalAxis(List<IdAndLabel> values);

	CoordinateChart addHorizontalAxis(List<IdAndLabel> values, AxisOptions options);

	CoordinateChart setColors(List<String> colors);

	String render();

	CoordinateChart addData(Stream<Stream<DataPoint>> data);

}