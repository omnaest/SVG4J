package org.omnaest.svg.text.internal;

import org.omnaest.svg.text.TextMetrics;

/**
 * Explicit, deterministic, font-free {@link TextMetrics} implementation: {@code measureWidth(s) = s.length() * advanceRatio
 * * fontSize}. Never selected automatically - only ever constructed when a caller explicitly asks for a font-free
 * measurement.
 *
 * @author omnaest
 */
public class FixedAdvanceTextMetrics implements TextMetrics
{
    private final double advanceRatio;
    private final double fontSize;

    public FixedAdvanceTextMetrics(double advanceRatio, double fontSize)
    {
        this.advanceRatio = advanceRatio;
        this.fontSize = fontSize;
    }

    @Override
    public double measureWidth(String text)
    {
        if (text == null || text.isEmpty())
        {
            return 0;
        }
        return text.length() * this.advanceRatio * this.fontSize;
    }

    @Override
    public double getAscent()
    {
        return this.fontSize * 0.8;
    }

    @Override
    public double getDescent()
    {
        return this.fontSize * 0.2;
    }

    @Override
    public double getLineHeight()
    {
        return this.getAscent() + this.getDescent();
    }

    @Override
    public double getFontSize()
    {
        return this.fontSize;
    }

    @Override
    public TextMetrics withFontSize(double fontSize)
    {
        return new FixedAdvanceTextMetrics(this.advanceRatio, fontSize);
    }
}
