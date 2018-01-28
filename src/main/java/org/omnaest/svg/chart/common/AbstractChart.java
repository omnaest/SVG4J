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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.SVGRenderResult;
import org.omnaest.svg.SVGUtils;
import org.omnaest.svg.chart.CoordinateChart;
import org.omnaest.svg.chart.common.AxisOptions.SortDirection;

public abstract class AbstractChart implements CoordinateChart
{
    protected SVGDrawer drawer;

    protected int width;
    protected int height;
    protected int pixelFactor = 1;

    protected List<IdAndLabel>             horizontalAxisValues;
    protected AxisOptions                  horizontalAxisOptions = new AxisOptions().setRotation(-45);
    protected List<? extends AxisPoint<?>> verticalAxisValues;
    protected AxisOptions                  verticalAxisOptions;

    protected List<String> colors = Arrays.asList("red", "blue", "green", "yellow", "brown", "purple");

    protected static class Vector
    {
        private int x;
        private int y;

        public Vector(int x, int y)
        {
            super();
            this.x = x;
            this.y = y;
        }

        public int getX()
        {
            return this.x;
        }

        public int getY()
        {
            return this.y;
        }

        public Vector rotate(double angle)
        {
            double alpha = angle / 180.0 * Math.PI;
            double cosinusAlpha = Math.cos(alpha);
            double sinusAlpha = Math.sin(alpha);

            int x1 = (int) Math.round(this.x * cosinusAlpha - this.y * sinusAlpha);
            int y1 = (int) Math.round(this.x * sinusAlpha + this.y * cosinusAlpha);

            return new Vector(x1, y1);
        }

        public Vector add(Vector vector)
        {
            return new Vector(this.x + vector.getX(), this.y + vector.getY());
        }

    }

    public AbstractChart(int width, int height)
    {
        super();
        this.width = width;
        this.height = height;
        this.drawer = SVGUtils.getDrawer(width, height);
        this.pixelFactor = Math.min(width, height) / 100;
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

    @Override
    public AbstractChart setColors(List<String> colors)
    {
        this.colors = colors;
        return this;
    }

    @Override
    public CoordinateChart addVerticalAxis(List<? extends AxisPoint<?>> values)
    {
        AxisOptions options = null;
        return this.addVerticalAxis(values, options);
    }

    @Override
    public CoordinateChart addVerticalAxis(List<? extends AxisPoint<?>> values, AxisOptions options)
    {
        List<? extends AxisPoint<?>> axisValues = new ArrayList<>(values);
        if (options != null)
        {
            this.verticalAxisOptions = options;
        }
        if (this.verticalAxisOptions != null && this.verticalAxisOptions.getSortDirection() != null)
        {
            SortDirection sortDirection = this.verticalAxisOptions.getSortDirection();
            int sortDirectionFactor = SortDirection.ASCENDING.equals(sortDirection) ? 1 : -1;
            Collections.sort(axisValues, (ap1, ap2) -> sortDirectionFactor * StringUtils.defaultString(ap1.getLabel())
                                                                                        .compareTo(ap2.getLabel()));
        }

        this.verticalAxisValues = axisValues;
        return this;
    }

    protected abstract void renderVerticalAxis();

    @Override
    public CoordinateChart addHorizontalAxis(List<IdAndLabel> values)
    {
        return this.addHorizontalAxis(values, null);
    }

    @Override
    public CoordinateChart addHorizontalAxis(List<IdAndLabel> values, AxisOptions options)
    {
        this.horizontalAxisValues = values;
        this.horizontalAxisOptions = options != null ? options : this.horizontalAxisOptions;
        return this;
    }

    protected abstract void renderHorizontalAxis();

    @Override
    public AbstractChart addData(Stream<? extends Stream<? extends Point<?, ?>>> data)
    {
        //
        List<List<? extends Point<?, ?>>> dataPoints = this.collectData(data);

        //
        this.ensureVerticalAxisOptions(dataPoints);

        //
        this.ensureHorziontalAxis(dataPoints);
        this.ensureVerticalAxis(dataPoints);

        //
        int verticalAxisPointCount = this.verticalAxisValues.size();
        Map<String, Integer> horizontalAxis = this.horizontalAxisValues.stream()
                                                                       .collect(Collectors.toMap(idAndLabel -> idAndLabel.getId(),
                                                                                                 idAndLabel -> this.horizontalAxisValues.indexOf(idAndLabel)));
        Map<Object, Double> verticalAxis = this.verticalAxisValues.stream()
                                                                  .collect(Collectors.toMap(axisPoint -> axisPoint.getId(), axisPoint ->
                                                                  {
                                                                      if (axisPoint instanceof IdAndLabel)
                                                                      {
                                                                          return this.verticalAxisValues.indexOf(axisPoint) / (1.0 * verticalAxisPointCount);
                                                                      }
                                                                      else if (axisPoint instanceof NumberAndLabel)
                                                                      {
                                                                          return ((NumberAndLabel) axisPoint).getId();
                                                                      }
                                                                      else
                                                                      {
                                                                          throw new RuntimeException("Vertical axis element is of unknown type");
                                                                      }
                                                                  }));

        Iterator<String> colors = this.colors.iterator();

        //
        this.renderVerticalAxis();
        this.renderHorizontalAxis();
        this.renderData(dataPoints.stream()
                                  .map(points -> points.stream()),
                        horizontalAxis, verticalAxis, colors);

        //
        return this;
    }

    private void ensureVerticalAxisOptions(List<List<? extends Point<?, ?>>> dataPoints)
    {
        if (this.verticalAxisOptions == null)
        {
            this.verticalAxisOptions = new AxisOptions();

            boolean hasNumberPoints = dataPoints.stream()
                                                .flatMap(points -> points.stream())
                                                .anyMatch(point -> point instanceof NumberPoint);
            if (hasNumberPoints)
            {
                this.verticalAxisOptions.setSortDirection(SortDirection.ASCENDING);
            }
        }

    }

    private void ensureHorziontalAxis(List<List<? extends Point<?, ?>>> dataPoints)
    {
        if (this.horizontalAxisValues == null)
        {
            this.addHorizontalAxis(dataPoints.stream()
                                             .flatMap(points -> points.stream())
                                             .map(point ->
                                             {
                                                 if (point instanceof DataPoint)
                                                 {
                                                     return ((DataPoint) point).getX();
                                                 }
                                                 else if (point instanceof NumberPoint)
                                                 {
                                                     return ((NumberPoint) point).getX();
                                                 }
                                                 else
                                                 {
                                                     return null;
                                                 }
                                             })
                                             .filter(point -> point != null)
                                             .distinct()
                                             .map(label -> new IdAndLabel(label))
                                             .collect(Collectors.toList()));
        }

    }

    private void ensureVerticalAxis(List<List<? extends Point<?, ?>>> dataPoints)
    {
        if (this.verticalAxisValues == null)
        {
            this.addVerticalAxis(dataPoints.stream()
                                           .flatMap(points -> points.stream())
                                           .map(point ->
                                           {
                                               if (point instanceof DataPoint)
                                               {
                                                   return new IdAndLabel(((DataPoint) point).getY());
                                               }
                                               else if (point instanceof NumberPoint)
                                               {
                                                   return new NumberAndLabel(((NumberPoint) point).getY());
                                               }
                                               else
                                               {
                                                   return null;
                                               }
                                           })
                                           .filter(idAndLabel -> idAndLabel != null)
                                           .distinct()
                                           .collect(Collectors.toList()));
        }

    }

    private List<List<? extends Point<?, ?>>> collectData(Stream<? extends Stream<? extends Point<?, ?>>> data)
    {
        return data.map(points -> points.collect(Collectors.toList()))
                   .collect(Collectors.toList());
    }

    protected abstract void renderData(Stream<Stream<? extends Point<?, ?>>> data, Map<String, Integer> horizontalAxis, Map<Object, Double> verticalAxis,
                                       Iterator<String> colors2);

    protected double extractNormValueFromAxisPoint(int index, AxisPoint<?> axisPoint)
    {
        int size = this.verticalAxisValues.size();
        double maxValue = this.calculateVerticalAxisMaxValue();
        double value = 0.0;
        if (axisPoint instanceof NumberAndLabel)
        {
            value = (((NumberAndLabel) axisPoint).getId()) / maxValue;
        }
        else
        {
            value = size > 0 ? index / (1.0 * size) : 0.5;
        }
        return value;
    }

    protected double calculateVerticalAxisMaxValue()
    {
        return this.verticalAxisValues.stream()
                                      .filter(value -> value instanceof NumberAndLabel)
                                      .map(value -> (NumberAndLabel) value)
                                      .mapToDouble(value -> value.getId())
                                      .max()
                                      .orElseGet(() -> 1.0);
    }

    @Override
    public CoordinateChart setHorizontalAxisOptions(AxisOptions options)
    {
        this.horizontalAxisOptions = options;
        return this;
    }

    @Override
    public CoordinateChart setVerticalAxisOptions(AxisOptions options)
    {
        this.verticalAxisOptions = options;
        return this;
    }

}