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
