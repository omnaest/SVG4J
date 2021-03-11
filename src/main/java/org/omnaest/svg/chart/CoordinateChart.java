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

import java.util.List;
import java.util.stream.Stream;

import org.omnaest.svg.SVGDrawer.ResolutionProvider;
import org.omnaest.svg.chart.common.AxisOptions;
import org.omnaest.svg.chart.common.AxisPoint;
import org.omnaest.svg.chart.common.IdAndLabel;
import org.omnaest.svg.chart.common.Point;

public interface CoordinateChart extends Chart
{

    CoordinateChart addVerticalAxis(List<? extends AxisPoint<?>> values);

    CoordinateChart addVerticalAxis(List<? extends AxisPoint<?>> values, AxisOptions options);

    CoordinateChart addHorizontalAxis(List<IdAndLabel> values);

    CoordinateChart addHorizontalAxis(List<IdAndLabel> values, AxisOptions options);

    CoordinateChart setColors(List<String> colors);

    CoordinateChart addData(Stream<? extends Stream<? extends Point<?, ?>>> data);

    CoordinateChart setHorizontalAxisOptions(AxisOptions options);

    CoordinateChart setVerticalAxisOptions(AxisOptions options);

    CoordinateChart withScreenDimensions(ResolutionProvider displayResolution);

    CoordinateChart withRelativePadding(double relativePaddingSize);
}
