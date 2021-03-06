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
package org.omnaest.svg.chart;

public interface RangeChart extends Chart
{
    public enum Color
    {
        RED, BLUE, GREEN, BLACK, YELLOW
    }

    public enum ScalePosition
    {
        HIGH, LOW
    }

    public RangeChart setHorizontalScale(double min, double max);

    public RangeChart addRange(Color color, double min, double max, double center, double fadeOutOpacity);

    public RangeChart addRangeBox(Color color, double min, double max, double opacity);

    public RangeChart setLabel(String label);

    public RangeChart addPoint(Color color, double value);

    public RangeChart addScalePoint(double value, ScalePosition position);

}
