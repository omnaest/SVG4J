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

import java.util.Objects;

public class NumberAndLabel implements AxisPoint<Double>
{
	private double	value;
	private String	label;

	public NumberAndLabel(double value, String label)
	{
		super();
		this.value = value;
		this.label = label;
	}

	public NumberAndLabel(double label)
	{
		this(label, Objects.toString(label));
	}

	@Override
	public Double getId()
	{
		return this.value;
	}

	@Override
	public String getLabel()
	{
		return this.label;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		NumberAndLabel other = (NumberAndLabel) obj;
		if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

}
