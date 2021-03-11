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
package org.omnaest.svg.chart;

import java.util.Collection;
import java.util.stream.Stream;

public interface TableChart extends Chart
{
    public static class Coordinate
    {
        private String column;
        private String row;

        public Coordinate(String column, String row)
        {
            super();
            this.column = column;
            this.row = row;
        }

        public String getColumn()
        {
            return column;
        }

        public String getRow()
        {
            return row;
        }

    }

    public static class CoordinateEntry
    {
        private Coordinate coordinate;
        private String     text;
        private double     value;

        public CoordinateEntry(String text, double value, String column, String row)
        {
            super();
            this.text = text;
            this.value = value;
            this.coordinate = new Coordinate(column, row);
        }

        public String getText()
        {
            return this.text;
        }

        public double getValue()
        {
            return this.value;
        }

        public Coordinate getCoordinate()
        {
            return coordinate;
        }

        @Override
        public String toString()
        {
            return "Entry [coordinate=" + coordinate + ", text=" + text + ", value=" + value + "]";
        }

    }

    public static class Column
    {
        private String id;
        private String label;

        public Column(String id, String label)
        {
            super();
            this.id = id;
            this.label = label;
        }

        public Column(String label)
        {
            super();
            this.id = label;
            this.label = label;
        }

        public String getId()
        {
            return this.id;
        }

        public String getLabel()
        {
            return this.label;
        }

        @Override
        public String toString()
        {
            return "Column [id=" + this.id + ", label=" + this.label + "]";
        }

    }

    public static class Row
    {
        private String id;
        private String label;

        public Row(String id, String label)
        {
            super();
            this.id = id;
            this.label = label;
        }

        public Row(String label)
        {
            super();
            this.id = label;
            this.label = label;
        }

        public String getId()
        {
            return this.id;
        }

        public String getLabel()
        {
            return this.label;
        }

        @Override
        public String toString()
        {
            return "Row [id=" + id + ", label=" + label + "]";
        }

    }

    public TableChart addColumns(Collection<Column> columns);

    public TableChart addRows(Collection<Row> rows);

    public TableChart addData(Stream<CoordinateEntry> entries);

    public TableChart withThresholdSecondary(Double thresholdSecondary);

    public TableChart withThresholdPrimary(double thresholdPrimary);

    public TableChart withNegativeColor(String negativeColor);

    public TableChart withPositiveColor(String positiveColor);

    TableChart withRelativePadding(double relativePaddingSize);
}
