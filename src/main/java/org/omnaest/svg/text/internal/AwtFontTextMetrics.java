package org.omnaest.svg.text.internal;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.omnaest.svg.text.FontSource;
import org.omnaest.svg.text.TextMetrics;

/**
 * {@link TextMetrics} backed by a {@link Font} loaded via {@link Font#createFont(int, InputStream)} from a
 * {@link FontSource}.
 * <p>
 * Antialiasing and fractional metrics are both disabled on the shared {@link FontRenderContext} so that measured advances
 * come back as stable whole numbers, which is what makes the result byte-identical across separate JVM invocations.
 * <p>
 * There is no system/logical font fallback: if the font cannot be loaded, construction throws.
 *
 * @author omnaest
 */
public class AwtFontTextMetrics implements TextMetrics
{
    private static final FontRenderContext RENDER_CONTEXT = new FontRenderContext(null, false, false);

    private final Font                     bareFont;
    private final Font                     sizedFont;
    private final double                   fontSize;

    private final Map<String, Double>      widthCache     = new LinkedHashMap<>();

    public AwtFontTextMetrics(FontSource fontSource, double fontSize)
    {
        this(loadBaseFont(fontSource), fontSize);
    }

    private AwtFontTextMetrics(Font bareFont, double fontSize)
    {
        System.setProperty("java.awt.headless", "true");
        this.bareFont = bareFont;
        this.fontSize = fontSize;
        this.sizedFont = bareFont.deriveFont((float) fontSize);
    }

    private static Font loadBaseFont(FontSource fontSource)
    {
        System.setProperty("java.awt.headless", "true");
        try (InputStream inputStream = fontSource.openStream())
        {
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        }
        catch (IOException | FontFormatException e)
        {
            throw new IllegalStateException("Unable to load font: " + e.getMessage(), e);
        }
    }

    @Override
    public double measureWidth(String text)
    {
        if (text == null || text.isEmpty())
        {
            return 0;
        }
        return this.widthCache.computeIfAbsent(text, t -> this.sizedFont.getStringBounds(t, RENDER_CONTEXT)
                                                                        .getWidth());
    }

    @Override
    public double getAscent()
    {
        return this.lineMetrics()
                   .getAscent();
    }

    @Override
    public double getDescent()
    {
        return this.lineMetrics()
                   .getDescent();
    }

    @Override
    public double getLineHeight()
    {
        LineMetrics lineMetrics = this.lineMetrics();
        return lineMetrics.getAscent() + lineMetrics.getDescent() + lineMetrics.getLeading();
    }

    private LineMetrics lineMetrics()
    {
        return this.sizedFont.getLineMetrics("", RENDER_CONTEXT);
    }

    @Override
    public double getFontSize()
    {
        return this.fontSize;
    }

    @Override
    public TextMetrics withFontSize(double fontSize)
    {
        return new AwtFontTextMetrics(this.bareFont, fontSize);
    }
}
