package org.omnaest.svg.chart.bar;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGText;

public class SVGBarChart
{
	private SVGDrawer			drawer;
	private int					width;
	private int					height;
	private int					pixelFactor	= 1;
	private List<IdAndLabel>	horizontalAxisValues;
	private List<IdAndLabel>	verticalAxisValues;
	private List<String>		colors		= Arrays.asList("red", "blue", "green", "yellow", "brown", "purple");

	public SVGBarChart(int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
		this.drawer = SVGUtils.getDrawer(width, height);
		this.pixelFactor = Math.min(width, height) / 100;
	}

	public SVGBarChart addVerticalAxis(List<IdAndLabel> values)
	{
		//
		this.verticalAxisValues = values;

		//
		int padding = calculatePadding();
		this.drawer.add(new SVGLine(padding, padding, padding, this.height - padding)	.setStrokeColor("gray")
																						.setStrokeWidth(this.pixelFactor / 2));

		//
		int size = values.size();
		for (int ii = 0; ii < size; ii++)
		{
			//stroke
			int y = calculateVerticalPosition(ii);
			this.drawer.add(new SVGLine(padding - 2 * pixelFactor, y, padding + 2 * pixelFactor, y)	.setStrokeWidth(this.pixelFactor)
																									.setStrokeColor("black"));

			//text
			IdAndLabel idAndLabel = values.get(ii);
			String label = idAndLabel.getLabel();
			int fontSize = SVGText.DEFAULT_FONTSIZE * pixelFactor / 2;
			this.drawer.add(new SVGText(0, y + fontSize / 3, label)	.setColor("black")
																	.setFontSize(fontSize));
		}

		//
		return this;
	}

	private int calculateVerticalPosition(int ii)
	{
		int padding = this.calculatePadding();
		int verticalAxisSize = this.verticalAxisValues.size();
		int chartAreaHeight = this.height - 2 * padding;
		int spanHeight = chartAreaHeight / (verticalAxisSize - 1);
		int y = this.height - padding - ii * spanHeight;
		return y;
	}

	private int calculatePadding()
	{
		return Math.max(this.width, this.height) / 10;
	}

	public SVGBarChart addHorizontalAxis(List<IdAndLabel> values)
	{
		//
		this.horizontalAxisValues = values;

		//
		int padding = this.calculatePadding();
		this.drawer.add(new SVGLine(padding, this.height - padding, this.width - padding, this.height - padding).setStrokeColor("gray")
																												.setStrokeWidth(this.pixelFactor / 2));

		//
		int size = this.horizontalAxisValues.size();
		for (int ii = 0; ii < size; ii++)
		{
			//stroke
			int x = calculateHorizontalPosition(ii);
			this.drawer.add(new SVGLine(x, this.height - padding + 2 * pixelFactor, x, this.height - padding - 2 * pixelFactor)
																																.setStrokeWidth(this.pixelFactor)
																																.setStrokeColor("black"));

			//text
			IdAndLabel idAndLabel = values.get(ii);
			String label = idAndLabel.getLabel();
			int fontSize = SVGText.DEFAULT_FONTSIZE * pixelFactor / 2;
			this.drawer.add(new SVGText(x - fontSize / 3, this.height, label)	.setColor("black")
																				.setFontSize(fontSize));
		}

		//
		return this;
	}

	private int calculateHorizontalPosition(int rasterPosition)
	{
		int padding = this.calculatePadding();
		int size = this.horizontalAxisValues.size();
		int chartAreaWidth = this.width - 2 * padding;
		int spanHeight = chartAreaWidth / (size - 1);
		int x = padding + rasterPosition * spanHeight;
		return x;
	}

	protected static class Vector
	{
		private int	x;
		private int	y;

		public Vector(int x, int y)
		{
			super();
			this.x = x;
			this.y = y;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

	}

	public static class DataPoint
	{
		private String	x;
		private String	y;

		public DataPoint(String x, String y)
		{
			super();
			this.x = x;
			this.y = y;
		}

		public String getX()
		{
			return x;
		}

		public String getY()
		{
			return y;
		}

		@Override
		public String toString()
		{
			return "DataPoint [x=" + x + ", y=" + y + "]";
		}

	}

	public void setColors(List<String> colors)
	{
		this.colors = colors;
	}

	public SVGBarChart addData(Stream<Stream<DataPoint>> data)
	{
		Map<String, Integer> horizontalAxis = this.horizontalAxisValues	.stream()
																		.collect(Collectors.toMap(	idAndLabel -> idAndLabel.getId(),
																									idAndLabel -> this.horizontalAxisValues.indexOf(idAndLabel)));
		Map<String, Integer> verticalAxis = this.verticalAxisValues	.stream()
																	.collect(Collectors.toMap(	idAndLabel -> idAndLabel.getId(),
																								idAndLabel -> this.verticalAxisValues.indexOf(idAndLabel)));

		Iterator<String> colors = this.colors.iterator();

		data.forEach(points ->
		{
			String color = colors.next();
			AtomicReference<Vector> previousPoint = new AtomicReference<>();
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
					.peek(vector ->
					{
						//
						Vector previousVector = previousPoint.getAndSet(vector);
						if (previousVector != null)
						{
							this.drawer.add(new SVGLine(previousVector.getX(), previousVector.getY(), vector.getX(), vector.getY())	.setStrokeColor("white")
																																	.setOpacity(0.95)
																																	.setStrokeWidth(this.pixelFactor
																																			* 2));
							this.drawer.add(new SVGLine(previousVector.getX(), previousVector.getY(), vector.getX(), vector.getY())	.setStrokeColor(color)
																																	.setOpacity(0.8)
																																	.setStrokeWidth(this.pixelFactor
																																			/ 2));
						}
						System.out.println("line");
					})
					.collect(Collectors.toList())
					.stream()
					.forEach(vector ->
					{
						System.out.println("circle");
						//
						int r = this.pixelFactor;
						this.drawer.add(new SVGCircle(vector.getX(), vector.getY(), r + this.pixelFactor / 2)	.setFillColor("white")
																												.setStrokeColor("white")
																												.setStrokeWidth(this.pixelFactor / 2)
																												.setOpacity(0.95));
						this.drawer.add(new SVGCircle(vector.getX(), vector.getY(), r)	.setFillColor("white")
																						.setStrokeColor(color)
																						.setStrokeWidth(this.pixelFactor / 2)
																						.setOpacity(0.8));
					});
		});

		return this;
	}

	public String render()
	{
		return this.drawer.render();
	}
}
