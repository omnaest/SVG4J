package org.omnaest.svg.chart.common;

import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGText;

public abstract class AbstractCartesianCoordinateChart extends AbstractChart
{
	public AbstractCartesianCoordinateChart(int width, int height)
	{
		super(width, height);
	}

	protected void renderVerticalAxis()
	{
		//
		int padding = calculatePadding();
		this.drawer.add(new SVGLine(padding, padding, padding, this.height - padding)	.setStrokeColor("gray")
																						.setStrokeWidth(this.pixelFactor / 2));

		//
		int size = this.verticalAxisValues.size();
		for (int ii = 0; ii < size; ii++)
		{
			//stroke
			int y = calculateVerticalPosition(ii);
			this.drawer.add(new SVGLine(padding - 2 * pixelFactor, y, padding + 2 * pixelFactor, y)	.setStrokeWidth(this.pixelFactor)
																									.setStrokeColor("black"));

			//text
			IdAndLabel idAndLabel = this.verticalAxisValues.get(ii);
			String label = idAndLabel.getLabel();
			int fontSize = SVGText.DEFAULT_FONTSIZE * pixelFactor / 2;
			this.drawer.add(new SVGText(0, y + fontSize / 3, label)	.setColor("black")
																	.setFontSize(fontSize));
		}
	}

	protected int calculateVerticalPosition(int ii)
	{
		int padding = this.calculatePadding();
		int verticalAxisSize = this.verticalAxisValues.size();
		int chartAreaHeight = this.height - 2 * padding;
		int spanHeight = chartAreaHeight / (verticalAxisSize - 1);
		int y = this.height - padding - ii * spanHeight;
		return y;
	}

	protected int calculatePadding()
	{
		return this.pixelFactor * 10;
	}

	protected void renderHorizontalAxis()
	{
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
			IdAndLabel idAndLabel = this.horizontalAxisValues.get(ii);
			String label = idAndLabel.getLabel();
			int fontSize = SVGText.DEFAULT_FONTSIZE * pixelFactor / 2;
			this.drawer.add(new SVGText(x - fontSize / 3, this.height, label)	.setColor("black")
																				.setFontSize(fontSize)
																				.setRotation(this.horizontalAxisOptions.getRotation()));
		}
	}

	protected int calculateHorizontalPosition(int rasterPosition)
	{
		int padding = this.calculatePadding();
		int size = this.horizontalAxisValues.size();
		int chartAreaWidth = this.width - 2 * padding;
		int spanHeight = chartAreaWidth / (size - 1);
		int x = padding + rasterPosition * spanHeight;
		return x;
	}

}