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
package org.omnaest.svg.chart.types;

import java.util.stream.Stream;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.SVGDrawer.ResolutionProvider;
import org.omnaest.svg.SVGDrawer.SVGRenderResult;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.chart.RedGreenDeviationRangeChart;
import org.omnaest.svg.chart.RedGreenDeviationTableChart;

public class SVGRedGreenDeviationTableChart implements RedGreenDeviationTableChart
{
    private SVGDrawer   drawer;
    private BoundedArea boundedArea;

    public SVGRedGreenDeviationTableChart(int width, int height)
    {
        super();

        this.drawer = SVGUtils.getDrawer(width, height);
        this.boundedArea = this.drawer.newBoundedArea();

    }

    @Override
    public RedGreenDeviationTableChart withRelativePadding(double relativePadding)
    {
        this.boundedArea = this.boundedArea.withRelativeSizedPadding(relativePadding);
        return this;
    }

    @Override
    public RedGreenDeviationTableChart withScreenDimensions(ResolutionProvider displayResolution)
    {
        this.drawer.withScreenDimensions(displayResolution);
        return this;
    }

    @Override
    public Stream<RedGreenDeviationRangeChart> createRows(int number)
    {
        return this.boundedArea.asHorizontalSlices(number)
                               .stream()
                               .map(boundedArea -> new SVGRedGreenDeviationRangeChart(boundedArea));
    }

    @Override
    public String render()
    {
        return this.drawer.render();
    }

    @Override
    public SVGRenderResult renderAsResult()
    {
        return this.drawer.renderAsResult();
    }

}
