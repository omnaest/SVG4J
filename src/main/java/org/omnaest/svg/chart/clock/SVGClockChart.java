package org.omnaest.svg.chart.clock;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.omnaest.svg.chart.common.AbstractChart;
import org.omnaest.svg.chart.common.DataPoint;
import org.omnaest.svg.chart.common.IdAndLabel;
import org.omnaest.svg.chart.common.LineRenderer;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;

public class SVGClockChart extends AbstractChart
{

	public SVGClockChart(int width, int height)
	{
		super(width, height);
	}

	protected int calculatePadding()
	{
		return this.pixelFactor * 20;
	}

	private Vector calculatePosition(int rasterX, int rasterY)
	{
		int padding = this.calculatePadding();

		int verticalAxisSize = this.verticalAxisValues.size();

		int minDimension = Math.min(this.height, this.width);
		int chartAreaHeight = minDimension - 2 * padding;

		int spanHeight = chartAreaHeight / 2 / (verticalAxisSize + 1);
		int y = padding + rasterY * spanHeight;
		return this	.calculateCenterPoint()
					.add(calculateDirectionVector(rasterX, y));
	}

	private Vector calculateDirectionVector(int rasterX, int y)
	{
		int horizontalAxisSize = this.horizontalAxisValues != null ? this.horizontalAxisValues.size() : 0;
		int angleStep = 360 / Math.max(1, horizontalAxisSize);
		return new Vector(0, y)	.rotate(180)
								.rotate(angleStep * rasterX);
	}

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
					return this.calculatePosition(rasterXPosition, rasterYPosition);
				}
				else
				{
					return null;
				}
			})
																						.filter(vector -> vector != null)));
		});
	}

	private Vector calculateCenterPoint()
	{
		int x = this.width / 2;
		int y = this.height / 2;
		return new Vector(x, y);
	}

	@Override
	protected void renderVerticalAxis()
	{
		//
		Vector centerPoint = this.calculateCenterPoint();
		int padding = calculatePadding();

		//
		int size = this.verticalAxisValues.size();

		for (int ii = 0; ii < size; ii++)
		{
			Vector position = this.calculatePosition(0, ii);

			//circle layers
			int r = centerPoint.getY() - position.getY();
			this.drawer.add(new SVGCircle(centerPoint.getX(), centerPoint.getY(), r).setStrokeWidth(this.pixelFactor / 2)
																					.setStrokeColor("gray")
																					.setFillOpacity(0.1));

		}

		//
		{
			int x = centerPoint.getX();
			int y = centerPoint.getY();
			this.drawer.add(new SVGRectangle(x - padding * 5 / 3 / 2, 0, padding * 5 / 3 / 2, y - padding / 2)	.setStrokeColor("white")
																												.setFillColor("white")
																												.setFillOpacity(1.0)
																												.setStrokeWidth(this.pixelFactor / 2));
			this.drawer.add(new SVGLine(x, y - padding, x, padding)	.setStrokeColor("gray")
																	.setStrokeWidth(this.pixelFactor / 2));

		}

		for (int ii = 0; ii < size; ii++)
		{
			Vector position = this.calculatePosition(0, ii);

			//stroke
			int x = position.getX();
			int y = position.getY();
			this.drawer.add(new SVGLine(x - 2 * pixelFactor, y, x + 2 * pixelFactor, y)	.setStrokeWidth(this.pixelFactor)
																						.setStrokeColor("black"));

			//text
			IdAndLabel idAndLabel = this.verticalAxisValues.get(ii);
			String label = idAndLabel.getLabel();
			int fontSize = SVGText.DEFAULT_FONTSIZE * pixelFactor / 2;
			this.drawer.add(new SVGText(x - padding * 4 / 3 / 2, y + fontSize / 3, label)	.setColor("black")
																							.setFontSize(fontSize));
		}

	}

	@Override
	protected void renderHorizontalAxis()
	{
		//
		int size = this.horizontalAxisValues.size();
		int modulo = size / 10;
		int rasterY = this.verticalAxisValues.size() - 1;
		for (int ii = 0; ii < size; ii++)
		{
			//
			boolean hasLargeStroke = ii % modulo == 0;

			//
			Vector position = this.calculatePosition(ii, rasterY);
			Vector directionVector = this.calculateDirectionVector(ii, (hasLargeStroke ? 4 : 2) * pixelFactor);
			Vector outerPoint = position.add(directionVector);

			int x = position.getX();
			int y = position.getY();

			//stroke
			this.drawer.add(new SVGLine(x, y, outerPoint.getX(), outerPoint.getY())	.setStrokeWidth(this.pixelFactor)
																					.setStrokeColor("black"));

			//text
			if (hasLargeStroke)
			{
				Vector outerTextPoint = outerPoint.add(directionVector);

				IdAndLabel idAndLabel = this.horizontalAxisValues.get(ii);
				String label = idAndLabel.getLabel();
				int fontSize = SVGText.DEFAULT_FONTSIZE * pixelFactor / 2;
				this.drawer.add(new SVGText(outerTextPoint.getX() - fontSize / 3, outerTextPoint.getY(), label)	.setColor("black")
																												.setFontSize(fontSize)
																												.setRotation(0));
			}

		}

	}

}
