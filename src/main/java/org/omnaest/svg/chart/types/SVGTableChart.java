package org.omnaest.svg.chart.types;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.SVGDrawer.ResolutionProvider;
import org.omnaest.svg.SVGDrawer.SVGRenderResult;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.chart.TableChart;
import org.omnaest.svg.chart.types.helper.ThresholdOpacity;
import org.omnaest.svg.elements.composite.SVGTextBox;
import org.omnaest.svg.other.DisplayResolution;
import org.omnaest.utils.CollectorUtils;
import org.omnaest.utils.MapperUtils;
import org.omnaest.utils.StreamUtils;

public class SVGTableChart implements TableChart
{
    private Map<String, Column>   columnIdToColumn  = new LinkedHashMap<>();
    private Map<String, Row>      rowIdToRow        = new LinkedHashMap<>();
    private List<CoordinateEntry> entries;
    private int                   width             = 10000;
    private int                   height            = 10000;
    private ResolutionProvider    displayResolution = DisplayResolution._1280x800;

    private ThresholdOpacity thresholdOpacity = new ThresholdOpacity();

    private String columnBorderColor       = "white";
    private int    columnBorderSize        = 50;
    private String columnBackgroundColor   = "lightgreen";
    private double columnBackgroundOpacity = 0.5;
    private String columnTextColor         = "black";
    private String rowBorderColor          = "white";
    private int    rowBorderSize           = 50;
    private String rowBackgroundColor      = "lightgreen";
    private double rowBackgroundOpacity    = 0.5;
    private String rowTextColor            = "black";

    private double relativePaddingSize = 0.2;

    @Override
    public TableChart withRelativePadding(double relativePaddingSize)
    {
        this.relativePaddingSize = relativePaddingSize;
        return this;
    }

    @Override
    public TableChart withPositiveColor(String positiveColor)
    {
        this.thresholdOpacity.setPositiveColor(positiveColor);
        return this;
    }

    @Override
    public TableChart withNegativeColor(String negativeColor)
    {
        this.thresholdOpacity.setNegativeColor(negativeColor);
        return this;
    }

    @Override
    public TableChart withThresholdPrimary(double thresholdPrimary)
    {
        this.thresholdOpacity.setThresholdPrimary(thresholdPrimary);
        return this;
    }

    @Override
    public TableChart withThresholdSecondary(Double thresholdSecondary)
    {
        this.thresholdOpacity.setThresholdSecondary(thresholdSecondary);
        return this;
    }

    @Override
    public TableChart addColumns(Collection<Column> columns)
    {
        if (columns != null)
        {
            columns.forEach(column ->
            {
                this.columnIdToColumn.put(column.getId(), column);
            });
        }
        return this;
    }

    @Override
    public TableChart addRows(Collection<Row> rows)
    {
        if (rows != null)
        {
            rows.forEach(row ->
            {
                this.rowIdToRow.put(row.getId(), row);
            });
        }
        return this;
    }

    @Override
    public String render()
    {
        return this.renderAsResult()
                   .getAsSVG();
    }

    @Override
    public SVGRenderResult renderAsResult()
    {
        SVGDrawer drawer = SVGUtils.getDrawer(this.width, this.height)
                                   .withScreenDimensions(this.displayResolution);

        BoundedArea surrounding = drawer.newBoundedArea()
                                        .withRelativeSizedPadding(this.relativePaddingSize);

        double columnProportion = 0.2;
        BoundedArea contentArea = surrounding.newSubArea()
                                             .withRelativeTranslationX(columnProportion)
                                             .withRelativeTranslationY(columnProportion)
                                             .withRelativeHeight(1.0 - columnProportion)
                                             .withRelativeWidth(1.0 - columnProportion)
                                             .withScalingHeight(100)
                                             .withScalingWidth(100);

        BoundedArea rowArea = surrounding.newSubArea()
                                         .withRelativeTranslationY(columnProportion)
                                         .withRelativeHeight(1.0 - columnProportion)
                                         .withRelativeWidth(columnProportion)
                                         .withScalingHeight(100)
                                         .withScalingWidth(100);

        BoundedArea columnArea = surrounding.newSubArea()
                                            .withRelativeTranslationX(columnProportion)
                                            .withRelativeHeight(columnProportion)
                                            .withRelativeWidth(1.0 - columnProportion)
                                            .withScalingHeight(100)
                                            .withScalingWidth(100);

        //        contentArea.add(new SVGRectangle(0, 0, 100, 100).setStrokeColor("black")
        //                                                        .setStrokeWidth(10)
        //                                                        .setFillColor("white"));

        this.renderRows(rowArea);
        this.renderColumns(columnArea);
        this.renderContent(contentArea);

        return drawer.renderAsResult();
    }

    private void renderContent(BoundedArea contentArea)
    {
        Map<String, Integer> columnIdToIndex = this.columnIdToColumn.keySet()
                                                                    .stream()
                                                                    .map(MapperUtils.withIntCounter())
                                                                    .collect(CollectorUtils.toMapByBiElement());

        Map<String, Integer> rowIdToIndex = this.rowIdToRow.keySet()
                                                           .stream()
                                                           .map(MapperUtils.withIntCounter())
                                                           .collect(CollectorUtils.toMapByBiElement());

        this.entries.forEach(entry ->
        {
            Coordinate coordinate = entry.getCoordinate();
            String column = coordinate.getColumn();
            String row = coordinate.getRow();

            int columnIndex = columnIdToIndex.get(column);
            int rowIndex = rowIdToIndex.get(row);

            String backgroundColor = this.thresholdOpacity.determineBackgroundColor(entry.getValue());
            double backgroundOpacity = this.thresholdOpacity.determineBackgroundOpacity(entry.getValue());
            contentArea.asVerticalSlices(this.columnIdToColumn.size())
                       .get(columnIndex)
                       .asHorizontalSlices(rowIdToIndex.size())
                       .get(rowIndex)
                       .withScalingHeight(100)
                       .withScalingWidth(100)
                       .withRelativeSizedPadding(0.1)
                       .add(new SVGTextBox(0, 0, 100, 100, entry.getText()).setBorderSize(10)
                                                                           .setBackgroundColor(backgroundColor)
                                                                           .setBackgroundOpacity(backgroundOpacity));
        });

    }

    private void renderRows(BoundedArea rowArea)
    {
        List<BoundedArea> rows = rowArea.asHorizontalSlices(this.rowIdToRow.size());
        StreamUtils.merge(rows.stream(), this.rowIdToRow.values()
                                                        .stream())
                   .forEach(lar -> lar.getLeft()
                                      .withScalingHeight(100)
                                      .withScalingWidth(100)
                                      .add(new SVGTextBox(0, 0, 100, 100, lar.getRight()
                                                                             .getLabel()).setTextColor(this.rowTextColor)
                                                                                         .setBackgroundColor(this.rowBackgroundColor)
                                                                                         .setBackgroundOpacity(this.rowBackgroundOpacity)
                                                                                         .setBorderColor(this.rowBorderColor)
                                                                                         .setBorderSize(this.rowBorderSize)));
    }

    private void renderColumns(BoundedArea columnArea)
    {
        List<BoundedArea> columns = columnArea.asVerticalSlices(this.columnIdToColumn.size());
        StreamUtils.merge(columns.stream(), this.columnIdToColumn.values()
                                                                 .stream())
                   .forEach(lar -> lar.getLeft()
                                      .withScalingHeight(100)
                                      .withScalingWidth(100)
                                      .add(new SVGTextBox(0, 0, 100, 100, lar.getRight()
                                                                             .getLabel()).setRotation(90)
                                                                                         .setTextColor(this.columnTextColor)
                                                                                         .setBackgroundColor(this.columnBackgroundColor)
                                                                                         .setBorderColor(this.columnBorderColor)
                                                                                         .setBackgroundOpacity(this.columnBackgroundOpacity)
                                                                                         .setBorderSize(this.columnBorderSize)));
    }

    @Override
    public TableChart addData(Stream<CoordinateEntry> entries)
    {
        this.entries = entries.collect(Collectors.toList());
        this.createRowAndColumnIfNotExisting(this.entries.stream());
        return this;
    }

    private void createRowAndColumnIfNotExisting(Stream<CoordinateEntry> entries)
    {
        entries.forEach(entry ->
        {
            Coordinate coordinate = entry.getCoordinate();

            String column = coordinate.getColumn();
            this.columnIdToColumn.computeIfAbsent(column, c -> new Column(c));

            String row = coordinate.getRow();
            this.rowIdToRow.computeIfAbsent(row, r -> new Row(r));
        });

    }

}
