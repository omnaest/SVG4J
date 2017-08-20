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

public class DataPoint implements Point<String, String>
{
	private String	x;
	private String	y;

	public DataPoint(String x, String y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String getX()
	{
		return this.x;
	}

	@Override
	public String getY()
	{
		return this.y;
	}

	@Override
	public String toString()
	{
		return "DataPoint [x=" + this.x + ", y=" + this.y + "]";
	}

}