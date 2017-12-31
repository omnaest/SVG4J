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
package org.omnaest.svg.elements;

import org.apache.commons.lang3.StringUtils;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGText;
import org.omnaest.utils.NumberUtils;
import org.omnaest.utils.ObjectUtils;
import org.omnaest.vector.Vector;

public class SVGText implements SVGElement
{
    public static final int DEFAULT_FONTSIZE = 10;

    private int               x;
    private int               y;
    private String            text;
    private int               fontSize          = DEFAULT_FONTSIZE;
    private String            color             = "black";
    private int               rotation          = 0;
    private double            opacity           = 1.0;
    private TextAnchor        textAnchor        = TextAnchor.INHERIT;
    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;

    private LengthAdjust lengthAdjust = null;
    private Double       length;

    public static enum TextAnchor
    {
        START, MIDDLE, END, INHERIT
    }

    public static enum VerticalAlignment
    {
        ONTOP, /* HANGING, */ CENTER
    }

    public static enum LengthAdjust
    {
        spacingAndGlyphs, spacing
    }

    public SVGText(int x, int y, String text)
    {
        super();
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public TextAnchor getTextAnchor()
    {
        return this.textAnchor;
    }

    public SVGText setTextAnchor(TextAnchor textAnchor)
    {
        this.textAnchor = textAnchor;
        return this;
    }

    public VerticalAlignment getVerticalAlignment()
    {
        return this.verticalAlignment;
    }

    /**
     * Sets the {@link VerticalAlignment}; default is {@link VerticalAlignment#CENTER}
     * 
     * @param verticalAlignment
     * @return
     */
    public SVGText setVerticalAlignment(VerticalAlignment verticalAlignment)
    {
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    public LengthAdjust getLengthAdjust()
    {
        return this.lengthAdjust;
    }

    public SVGText setLengthAdjust(LengthAdjust lengthAdjust)
    {
        this.lengthAdjust = lengthAdjust;
        return this;
    }

    public double getLength()
    {
        return this.length;
    }

    public SVGText setLength(double length)
    {
        if (this.lengthAdjust == null)
        {
            this.lengthAdjust = LengthAdjust.spacingAndGlyphs;
        }

        this.length = length;
        return this;
    }

    public String getColor()
    {
        return this.color;
    }

    public SVGText setColor(String color)
    {
        this.color = color;
        return this;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public String getText()
    {
        return this.text;
    }

    public SVGText setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
        return this;
    }

    public int getFontSize()
    {
        return this.fontSize;
    }

    @Override
    public RawSVGText render()
    {
        Vector delta = this.determineDelta();
        return new RawSVGText().setX("" + this.x)
                               .setY("" + this.y)
                               .setFill(this.color)
                               .setOpacity(this.opacity)
                               .setTextLength(ObjectUtils.getIfNotNull(this.length, () -> "" + this.length))
                               .setLengthAdjust(ObjectUtils.getIfNotNull(this.lengthAdjust, () -> this.lengthAdjust.name()))
                               .setTextAnchor(ObjectUtils.getIfNotNull(this.textAnchor, () -> StringUtils.lowerCase(this.textAnchor.name())))
                               .setDx(NumberUtils.formatter()
                                                 .withMaximumFractionDigits(4)
                                                 .format(delta.getX()))
                               .setDy(NumberUtils.formatter()
                                                 .withMaximumFractionDigits(4)
                                                 .format(delta.getY()))
                               .setStyle("font-size:" + this.fontSize + "px")
                               .setTransform("rotate(" + this.rotation + "," + this.x + "," + this.y + ")")
                               .setText(this.text);
    }

    private Vector determineDelta()
    {
        Vector retval = Vector.of(0.0);

        if (VerticalAlignment.CENTER.equals(this.verticalAlignment))
        {
            retval = Vector.of(this.fontSize / 3 * Math.sin(Math.toRadians(this.rotation)), this.fontSize / 3 * Math.cos(Math.toRadians(this.rotation)));
        }

        return retval;
    }

    public SVGText setRotation(int rotation)
    {
        this.rotation = rotation;
        return this;
    }

    public SVGElement setOpacity(double opacity)
    {
        this.opacity = opacity;
        return this;
    }

}
