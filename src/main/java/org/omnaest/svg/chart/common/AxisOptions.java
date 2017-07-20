package org.omnaest.svg.chart.common;

public class AxisOptions
{
	private int rotation = 0;

	public AxisOptions()
	{
		super();
	}

	public int getRotation()
	{
		return rotation;
	}

	public AxisOptions setRotation(int rotation)
	{
		this.rotation = rotation;
		return this;
	}

}