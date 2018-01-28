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

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.SVGDrawer.ResolutionProvider;
import org.omnaest.svg.SVGDrawer.SVGRenderResult;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.chart.EntryChart;
import org.omnaest.svg.chart.types.helper.ThresholdOpacity;
import org.omnaest.svg.elements.composite.SVGTextBox;

public class SVGBoxMapChart implements EntryChart
{
    private SVGDrawer drawer;

    private String           textColor        = "black";
    private ThresholdOpacity thresholdOpacity = new ThresholdOpacity();

    private Supplier<Integer> chartPadding = () -> 0;

    public SVGBoxMapChart(int width, int height)
    {
        super();
        this.drawer = SVGUtils.getDrawer(width, height);
    }

    public SVGBoxMapChart setThresholdPrimary(double thresholdPrimary)
    {
        this.thresholdOpacity.setThresholdPrimary(thresholdPrimary);
        return this;
    }

    public SVGBoxMapChart setThresholdSecondary(double thresholdSecondary)
    {
        this.thresholdOpacity.setThresholdSecondary(thresholdSecondary);
        return this;
    }

    public SVGBoxMapChart withScreenDimensions(ResolutionProvider displayResolution)
    {
        this.drawer.withScreenDimensions(displayResolution);
        return this;
    }

    public SVGBoxMapChart withScreenDimensions(int width, int height)
    {
        this.drawer.withScreenDimensions(width, height);
        return this;
    }

    @Override
    public EntryChart addData(Stream<Entry> entries)
    {
        List<Entry> dataEntries = entries.collect(Collectors.toList());

        int size = dataEntries.size();

        int dimension = (int) Math.ceil(Math.sqrt(size));

        List<BoundedArea> horizontalSlices = this.drawer.newBoundedArea()
                                                        .withPadding(this.chartPadding.get())
                                                        .asHorizontalSlices(dimension);

        Iterator<Entry> iterator = dataEntries.iterator();
        for (int ii = 0; ii < dimension; ii++)
        {
            List<BoundedArea> verticalSlices = horizontalSlices.get(ii)
                                                               .asVerticalSlices(dimension);
            for (int jj = 0; jj < dimension; jj++)
            {
                Entry entry = iterator.hasNext() ? iterator.next() : null;

                if (entry != null)
                {
                    double value = entry.getValue();
                    double backgroundOpacity = this.thresholdOpacity.determineBackgroundOpacity(value);
                    String backgroundColor = this.thresholdOpacity.determineBackgroundColor(value);
                    verticalSlices.get(jj)
                                  .withRelativeSizedPadding(0.05)
                                  .withScalingHeight(100)
                                  .withScalingWidth(100)
                                  .add(new SVGTextBox(0, 0, 100, 100, entry.getText()).setBackgroundOpacity(backgroundOpacity)
                                                                                      .setBackgroundColor(backgroundColor)
                                                                                      .setBorderColor("white")
                                                                                      .setTextColor(this.textColor)
                                                                                      .setBorderSize(2));
                }
            }
        }

        return this;
    }

    public SVGBoxMapChart setPositiveColor(String positiveColor)
    {
        this.thresholdOpacity.setPositiveColor(positiveColor);
        return this;
    }

    public SVGBoxMapChart setNegativeColor(String negativeColor)
    {
        this.thresholdOpacity.setNegativeColor(negativeColor);
        return this;
    }

    public SVGBoxMapChart setTextColor(String textColor)
    {
        this.textColor = textColor;
        return this;
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

    public SVGBoxMapChart setRelativePadding(double relativePadding)
    {
        this.chartPadding = () -> (int) (Math.min(this.drawer.getHeight(), this.drawer.getWidth()) * relativePadding);
        return this;
    }

    public SVGBoxMapChart setPadding(int padding)
    {
        this.chartPadding = () -> padding;
        return this;
    }

}
