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
package org.omnaest.svg.chart.types;

import org.omnaest.svg.SVGChartUtils;
import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.SVGDrawer.SVGRenderResult;
import org.omnaest.svg.chart.RangeChart;
import org.omnaest.svg.chart.RangeChart.Color;
import org.omnaest.svg.chart.RangeChart.ScalePosition;
import org.omnaest.svg.chart.RedGreenDeviationRangeChart;

public class SVGRedGreenDeviationRangeChart implements RedGreenDeviationRangeChart
{
    private RangeChart rangeChart;
    private double     min;
    private double     max;
    private boolean    renderScalePoints = true;

    public SVGRedGreenDeviationRangeChart(int width, int height)
    {
        super();
        this.rangeChart = SVGChartUtils.newRangeChart(width, height);
    }

    public SVGRedGreenDeviationRangeChart(BoundedArea boundedArea)
    {
        super();
        this.rangeChart = new SVGRangeChart(boundedArea);
    }

    @Override
    public String render()
    {
        return this.rangeChart.render();
    }

    @Override
    public SVGRenderResult renderAsResult()
    {
        return this.rangeChart.renderAsResult();
    }

    @Override
    public RedGreenDeviationRangeChart setHorizontalScale(double min, double max)
    {
        this.min = min;
        this.max = max;
        this.rangeChart.setHorizontalScale(min, max);
        return this;
    }

    @Override
    public RedGreenDeviationRangeChart setLabel(String label)
    {
        this.rangeChart.setLabel(label);
        return this;
    }

    @Override
    public RedGreenDeviationRangeChart addGreenRange(double center, double deviation)
    {

        double min = Math.max(center - deviation, this.min);
        double max = Math.min(center + deviation, this.max);
        double minGreen = Math.max(center - deviation * 1.2, this.min);
        double maxGreen = Math.min(center + deviation * 1.2, this.max);
        double fadeOutOpacity = 0.0;
        this.rangeChart.addRange(Color.GREEN, minGreen, maxGreen, center, fadeOutOpacity);

        this.rangeChart.addRange(Color.RED, this.min, min, this.min, fadeOutOpacity);
        this.rangeChart.addRange(Color.RED, max, this.max, this.max, fadeOutOpacity);

        if (this.renderScalePoints)
        {
            this.rangeChart.addScalePoint(this.min, ScalePosition.HIGH);
            this.rangeChart.addScalePoint(this.max, ScalePosition.HIGH);
            this.rangeChart.addScalePoint(center, ScalePosition.HIGH);
            this.rangeChart.addScalePoint(min, ScalePosition.HIGH);
            this.rangeChart.addScalePoint(max, ScalePosition.HIGH);
        }

        return this;
    }

    @Override
    public RedGreenDeviationRangeChart addYellowRangeBox(double min, double max)
    {
        min = Math.max(min, this.min);
        max = Math.min(max, this.max);
        double opacity = 0.5;

        this.rangeChart.addRangeBox(Color.YELLOW, min, max, opacity);

        if (this.renderScalePoints)
        {
            this.rangeChart.addScalePoint(min, ScalePosition.LOW);
            this.rangeChart.addScalePoint(max, ScalePosition.LOW);
        }

        return this;
    }

    @Override
    public RedGreenDeviationRangeChart doNotRenderScalePoints()
    {
        this.renderScalePoints = false;
        return this;
    }

    @Override
    public RedGreenDeviationRangeChart addPoint(double value)
    {
        this.rangeChart.addPoint(Color.YELLOW, value);

        if (this.renderScalePoints)
        {
            this.rangeChart.addScalePoint(value, ScalePosition.LOW);
        }
        return this;
    }

}
