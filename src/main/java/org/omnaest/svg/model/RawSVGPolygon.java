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
package org.omnaest.svg.model;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.omnaest.utils.StreamUtils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "polygon")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGPolygon extends RawSVGElement
{
    @XmlAttribute
    private String points;

    @XmlAttribute
    private String stroke;

    @XmlAttribute(name = "stroke-width")
    private String strokeWidth;

    @XmlAttribute
    private String fill;

    public String getPoints()
    {
        return this.points;
    }

    public RawSVGPolygon setPoints(String points)
    {
        this.points = points;
        return this;
    }

    public String getStroke()
    {
        return this.stroke;
    }

    public RawSVGPolygon setStroke(String stroke)
    {
        this.stroke = stroke;
        return this;
    }

    public String getFill()
    {
        return this.fill;
    }

    public RawSVGPolygon setFill(String fill)
    {
        this.fill = fill;
        return this;
    }

    public String getStrokeWidth()
    {
        return this.strokeWidth;
    }

    public RawSVGPolygon setStrokeWidth(String strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        return this;
    }

    @Override
    public String toString()
    {
        return "RawSVGPolygon [points=" + this.points + ", stroke=" + this.stroke + "]";
    }

    @Override
    protected RawSVGTransformer transformer()
    {
        return new RawSVGTransformer()
        {
            public Stream<int[]> parsePoints()
            {
                String[] tokens = StringUtils.splitPreserveAllTokens(StringUtils.trim(RawSVGPolygon.this.points), " ");

                Stream<int[]> frames = StreamUtils.framedPreserveSize(2, Arrays.asList(tokens)
                                                                               .stream()
                                                                               .mapToInt(value -> NumberUtils.toInt(value)));
                return frames;
            }

            public String renderPoints(Stream<int[]> points)
            {
                return points.flatMap(array -> Arrays.asList(ArrayUtils.toObject(array))
                                                     .stream())
                             .map(String::valueOf)
                             .collect(Collectors.joining(" "));
            }

            @Override
            public RawSVGElement translate(double x, double y)
            {
                RawSVGPolygon.this.points = this.renderPoints(this.parsePoints()
                                                                  .peek(xy ->
                                                                  {
                                                                      xy[0] += x;
                                                                      xy[1] += y;
                                                                  }));
                return RawSVGPolygon.this;
            }

            @Override
            public RawSVGElement scale(double scaleX, double scaleY)
            {
                RawSVGPolygon.this.points = this.renderPoints(this.parsePoints()
                                                                  .peek(xy ->
                                                                  {
                                                                      xy[0] *= scaleX;
                                                                      xy[1] *= scaleY;
                                                                  }));
                return RawSVGPolygon.this;
            }
        };
    }

}
