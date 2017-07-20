package org.omnaest.svg.chart.common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.chart.CoordinateChart;

public abstract class AbstractChart implements CoordinateChart
{
	protected SVGDrawer drawer;

	protected int	width;
	protected int	height;
	protected int	pixelFactor	= 1;

	protected List<IdAndLabel>	horizontalAxisValues;
	protected AxisOptions		horizontalAxisOptions;
	protected List<IdAndLabel>	verticalAxisValues;

	protected List<String> colors = Arrays.asList("red", "blue", "green", "yellow", "brown", "purple");

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

		public Vector rotate(int angle)
		{
			double alpha = angle / 180.0 * Math.PI;
			double cosinusAlpha = Math.cos(alpha);
			double sinusAlpha = Math.sin(alpha);

			int x1 = (int) Math.round(this.x * cosinusAlpha - this.y * sinusAlpha);
			int y1 = (int) Math.round(this.x * sinusAlpha + this.y * cosinusAlpha);

			return new Vector(x1, y1);
		}

		public Vector add(Vector vector)
		{
			return new Vector(this.x + vector.getX(), this.y + vector.getY());
		}

	}

	public AbstractChart(int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
		this.drawer = SVGUtils.getDrawer(width, height);
		this.pixelFactor = Math.min(width, height) / 100;
	}

	@Override
	public String render()
	{
		return this.drawer.render();
	}

	@Override
	public AbstractChart setColors(List<String> colors)
	{
		this.colors = colors;
		return this;
	}

	@Override
	public CoordinateChart addVerticalAxis(List<IdAndLabel> values)
	{
		this.verticalAxisValues = values;
		return this;
	}

	protected abstract void renderVerticalAxis();

	@Override
	public CoordinateChart addHorizontalAxis(List<IdAndLabel> values)
	{
		return this.addHorizontalAxis(values, new AxisOptions());
	}

	@Override
	public CoordinateChart addHorizontalAxis(List<IdAndLabel> values, AxisOptions options)
	{
		this.horizontalAxisValues = values;
		this.horizontalAxisOptions = options;
		return this;
	}

	protected abstract void renderHorizontalAxis();

	public AbstractChart addData(Stream<Stream<DataPoint>> data)
	{
		Map<String, Integer> horizontalAxis = this.horizontalAxisValues	.stream()
																		.collect(Collectors.toMap(	idAndLabel -> idAndLabel.getId(),
																									idAndLabel -> this.horizontalAxisValues.indexOf(idAndLabel)));
		Map<String, Integer> verticalAxis = this.verticalAxisValues	.stream()
																	.collect(Collectors.toMap(	idAndLabel -> idAndLabel.getId(),
																								idAndLabel -> this.verticalAxisValues.indexOf(idAndLabel)));

		Iterator<String> colors = this.colors.iterator();

		//
		this.renderVerticalAxis();
		this.renderHorizontalAxis();
		renderData(data, horizontalAxis, verticalAxis, colors);

		return this;
	}

	protected abstract void renderData(	Stream<Stream<DataPoint>> data, Map<String, Integer> horizontalAxis, Map<String, Integer> verticalAxis,
										Iterator<String> colors2);

}