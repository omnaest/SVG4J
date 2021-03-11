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

import java.util.stream.Stream;

/**
 * {@link Chart} with {@link Entry}s
 * 
 * @author omnaest
 */
public interface EntryChart extends Chart
{
    public static class Entry
    {
        private String text;
        private double value;

        public Entry(String text, double value)
        {
            super();
            this.text = text;
            this.value = value;
        }

        public String getText()
        {
            return this.text;
        }

        public double getValue()
        {
            return this.value;
        }

        @Override
        public String toString()
        {
            return "Entry [text=" + this.text + ", value=" + this.value + "]";
        }

    }

    public EntryChart addData(Stream<Entry> entries);

}
