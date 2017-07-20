package org.omnaest.svg.chart.bar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.chart.common.AbstractCartesianCoordinateChart;
import org.omnaest.svg.chart.common.DataPoint;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.base.SVGElement;

public class SVGBarChart extends AbstractCartesianCoordinateChart
{
	public SVGBarChart(int width, int height)
	{
		super(width, height);
	}

	private List<SVGElement> getLayer(int index, Map<Integer, List<SVGElement>> layers)
	{
		return layers.computeIfAbsent(index, (id) -> new ArrayList<>());
	}

	@Override
	protected void renderData(Stream<Stream<DataPoint>> data, Map<String, Integer> horizontalAxis, Map<String, Integer> verticalAxis, Iterator<String> colors)
	{
		List<Stream<DataPoint>> dataStreams = data.collect(Collectors.toList());
		int numberOfDataLines = dataStreams.size();

		int padding = this.calculatePadding();
		int sliceWidth = (this.width - 2 * padding) / this.horizontalAxisValues.size() / numberOfDataLines;

		AtomicInteger dataStreamIndexCounter = new AtomicInteger(0);
		dataStreams.forEach(points ->
		{
			int dataStreamIndex = dataStreamIndexCounter.getAndIncrement();

			String color = colors.next();

			Map<Integer, List<SVGElement>> layers = new TreeMap<>();

			points	.map(point ->
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
					.sorted((v1, v2) -> Integer.compare(v1.getX(), v2.getX()))
					.forEach(vector ->
					{
						int x = vector.getX() + (sliceWidth * dataStreamIndex);
						int y = vector.getY();
						int width = sliceWidth;
						int height = this.height - padding - y;

						getLayer(1, layers).add(new SVGRectangle(x, y, width, height)	.setFillColor(color)
																						.setStrokeColor("black")
																						.setStrokeWidth(this.pixelFactor / 2));
					});

			//
			this.drawer.addAll(layers	.values()
										.stream()
										.flatMap(layer -> layer.stream())
										.collect(Collectors.toList()));

		});

	}

}
