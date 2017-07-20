package org.omnaest.svg.chart.line;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.omnaest.svg.chart.common.AbstractCartesianCoordinateChart;
import org.omnaest.svg.chart.common.DataPoint;
import org.omnaest.svg.chart.common.LineRenderer;

public class SVGLineChart extends AbstractCartesianCoordinateChart
{
	public SVGLineChart(int width, int height)
	{
		super(width, height);
	}

	@Override
	protected void renderData(Stream<Stream<DataPoint>> data, Map<String, Integer> horizontalAxis, Map<String, Integer> verticalAxis, Iterator<String> colors)
	{
		data.forEach(points ->
		{
			String color = colors.next();
			this.drawer.addAll(new LineRenderer(pixelFactor).renderLines(color, points	.map(point ->
			{
				String horizontalAxisId = point.getX();
				String verticalAxisId = point.getY();
				Integer rasterXPosition = horizontalAxis.get(horizontalAxisId);
				Integer rasterYPosition = verticalAxis.get(verticalAxisId);
				if (rasterXPosition != null && rasterYPosition != null)
				{
					int x = this.calculateHorizontalPosition(rasterXPosition);
					int y = this.calculateVerticalPosition(rasterYPosition);

					return new Vector(x, y);
				}
				else
				{
					return null;
				}
			})
																						.filter(vector -> vector != null)
																						.sorted((v1, v2) -> Integer.compare(v1.getX(), v2.getX()))));
		});

	}

}
