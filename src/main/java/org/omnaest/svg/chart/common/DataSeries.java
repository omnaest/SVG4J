package org.omnaest.svg.chart.common;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.utils.stream.Streamable;

public class DataSeries implements Streamable<Point<?, ?>>
{
    private List<Point<?, ?>> points;
    private String            label;

    protected DataSeries(List<Point<?, ?>> points)
    {
        super();
        this.points = points;
    }

    public DataSeries withLabel(String label)
    {
        this.label = label;
        return this;
    }

    public String getLabel()
    {
        return this.label;
    }

    public static DataSeries of(Stream<? extends Point<?, ?>> dataPoints)
    {
        return of(dataPoints.collect(Collectors.toList()));
    }

    @SuppressWarnings("unchecked")
    public static DataSeries of(List<? extends Point<?, ?>> dataPoints)
    {
        return new DataSeries((List<Point<?, ?>>) dataPoints);
    }

    public List<Point<?, ?>> getPoints()
    {
        return this.points;
    }

    @Override
    public Stream<Point<?, ?>> stream()
    {
        return this.points.stream();
    }

}