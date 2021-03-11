/*******************************************************************************
 * Copyright 2021 Danny Kunz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.omnaest.svg.chart.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.chart.common.AbstractChart.Vector;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.base.SVGElement;

public class LineRenderer
{
	private int pixelFactor;

	public LineRenderer(int pixelFactor)
	{
		super();
		this.pixelFactor = pixelFactor;
	}

	public List<SVGElement> renderLines(String color, Stream<Vector> vectors)
	{
		Map<Integer, List<SVGElement>> layers = new TreeMap<>();
		AtomicReference<Vector> previousPoint = new AtomicReference<>();
		vectors	.peek(vector ->
		{
			//
			Vector previousVector = previousPoint.getAndSet(vector);
			if (previousVector != null)
			{
				getLayer(1, layers).add(new SVGLine(previousVector.getX(), previousVector.getY(), vector.getX(), vector.getY())	.setStrokeColor("white")
																																.setOpacity(0.95)
																																.setStrokeWidth(this.pixelFactor
																																		* 2));
				getLayer(2, layers).add(new SVGLine(previousVector.getX(), previousVector.getY(), vector.getX(), vector.getY())	.setStrokeColor(color)
																																.setOpacity(0.8)
																																.setStrokeWidth(this.pixelFactor
																																		/ 2));
			}
		})
				.collect(Collectors.toList())
				.stream()
				.forEach(vector ->
				{
					//
					int r = this.pixelFactor;
					getLayer(3, layers).add(new SVGCircle(vector.getX(), vector.getY(), r + this.pixelFactor / 2)	.setFillColor("white")
																													.setStrokeColor("white")
																													.setStrokeWidth(this.pixelFactor / 2)
																													.setOpacity(0.95));
					getLayer(4, layers).add(new SVGCircle(vector.getX(), vector.getY(), r)	.setFillColor("white")
																							.setStrokeColor(color)
																							.setStrokeWidth(this.pixelFactor / 2)
																							.setOpacity(0.8));
				});

		//
		List<SVGElement> svgElements = layers	.values()
												.stream()
												.flatMap(layer -> layer.stream())
												.collect(Collectors.toList());
		return svgElements;
	}

	private static List<SVGElement> getLayer(int index, Map<Integer, List<SVGElement>> layers)
	{
		return layers.computeIfAbsent(index, (id) -> new ArrayList<>());
	}
}
