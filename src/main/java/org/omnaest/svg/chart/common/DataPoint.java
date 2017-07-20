package org.omnaest.svg.chart.common;

public class DataPoint
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