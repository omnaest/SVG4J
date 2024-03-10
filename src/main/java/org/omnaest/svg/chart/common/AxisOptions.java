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
package org.omnaest.svg.chart.common;

import java.text.NumberFormat;

public class AxisOptions
{
    private int           rotation      = 0;
    private SortDirection sortDirection = null;
    private NumberFormat  numberFormat;

    public enum SortDirection
    {
        ASCENDING, DESCENDING
    }

    public AxisOptions()
    {
        super();
    }

    public int getRotation()
    {
        return this.rotation;
    }

    public AxisOptions setRotation(int rotation)
    {
        this.rotation = rotation;
        return this;
    }

    public SortDirection getSortDirection()
    {
        return this.sortDirection;
    }

    public AxisOptions setSortDirection(SortDirection sortDirection)
    {
        this.sortDirection = sortDirection;
        return this;
    }

    public NumberFormat getNumberFormat()
    {
        return this.numberFormat;
    }

    public AxisOptions setNumberFormat(NumberFormat numberFormat)
    {
        this.numberFormat = numberFormat;
        return this;
    }

}
