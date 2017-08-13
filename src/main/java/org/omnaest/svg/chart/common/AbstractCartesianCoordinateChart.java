/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.svg.chart.common;

import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGText;

public abstract class AbstractCartesianCoordinateChart extends AbstractChart
{
	public AbstractCartesianCoordinateChart(int width, int height)
	{
		super(width, height);
	}

	@Override
	protected void renderVerticalAxis()
	{
		//
		int padding = this.calculatePadding();
		this.drawer.add(new SVGLine(padding, padding, padding, this.height - padding)	.setStrokeColor("gray")
																						.setStrokeWidth(this.pixelFactor / 2));

		//
		int size = this.verticalAxisValues.size();

		int maxNumberOfVerticalAxisLabels = 20;
		int modFactor = (size / maxNumberOfVerticalAxisLabels) + 1;

		for (int ii = 0; ii < size; ii++)
		{
			if (ii % modFactor == 0 || ii == size - 1)
			{
				//stroke
				int y = this.calculateVerticalPosition(ii);
				this.drawer.add(new SVGLine(padding - 2 * this.pixelFactor, y, padding + 2 * this.pixelFactor, y)	.setStrokeWidth(this.pixelFactor / 2)
																													.setStrokeColor("black"));

				//text
				IdAndLabel idAndLabel = this.verticalAxisValues.get(ii);
				String label = idAndLabel.getLabel();
				int fontSize = SVGText.DEFAULT_FONTSIZE * 4 * this.pixelFactor / Math.max(3, Math.min(maxNumberOfVerticalAxisLabels, size));
				this.drawer.add(new SVGText(0, y + fontSize / 3, label)	.setColor("black")
																		.setFontSize(fontSize));
			}
		}
	}

	protected int calculateVerticalPosition(int ii)
	{
		int padding = this.calculatePadding();
		int verticalAxisSize = this.verticalAxisValues.size();
		int chartAreaHeight = this.height - 2 * padding;
		int spanHeight = verticalAxisSize > 1 ? chartAreaHeight / (verticalAxisSize - 1) : chartAreaHeight / 2;
		int y = this.height - padding - ii * spanHeight;
		return y;
	}

	protected int calculatePadding()
	{
		return this.pixelFactor * 10;
	}

	@Override
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
			int x = this.calculateHorizontalPosition(ii);
			this.drawer.add(new SVGLine(x, this.height - padding + 2 * this.pixelFactor, x,
										this.height - padding - 2 * this.pixelFactor)	.setStrokeWidth(this.pixelFactor / 2)
																						.setStrokeColor("black"));

			//text
			IdAndLabel idAndLabel = this.horizontalAxisValues.get(ii);
			String label = idAndLabel.getLabel();
			int fontSize = SVGText.DEFAULT_FONTSIZE * 20 * this.pixelFactor / Math.max(2, size);
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