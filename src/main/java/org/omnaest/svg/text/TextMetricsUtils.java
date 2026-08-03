package org.omnaest.svg.text;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.omnaest.svg.text.internal.AwtFontTextMetrics;
import org.omnaest.svg.text.internal.FixedAdvanceTextMetrics;

/**
 * {@link TextMetricsUtils}
 *
 * @see #newTextMetrics(FontSource, double)
 * @see #newFixedAdvanceTextMetrics(double, double)
 * @author omnaest
 */
public class TextMetricsUtils
{
    /**
     * Returns a new {@link TextMetrics} instance backed by the given {@link FontSource}, measured at the given font size.
     * <p>
     * There is no system-font fallback: if the font cannot be loaded, this throws.
     *
     * @param fontSource
     * @param fontSize
     * @return
     */
    public static TextMetrics newTextMetrics(FontSource fontSource, double fontSize)
    {
        return new AwtFontTextMetrics(fontSource, fontSize);
    }

    /**
     * Returns a new explicit, deterministic, font-free {@link TextMetrics} instance where
     * {@code measureWidth(s) = s.length() * advanceRatio * fontSize}.
     *
     * @param advanceRatio
     * @param fontSize
     * @return
     */
    public static TextMetrics newFixedAdvanceTextMetrics(double advanceRatio, double fontSize)
    {
        return new FixedAdvanceTextMetrics(advanceRatio, fontSize);
    }

    /**
     * Returns a {@link FontSource} that reads a font file from the classpath (via {@link Class#getResourceAsStream(String)}).
     *
     * @param resourcePath
     * @return
     */
    public static FontSource fontFromClasspathResource(String resourcePath)
    {
        return () ->
        {
            InputStream inputStream = TextMetricsUtils.class.getResourceAsStream(resourcePath);
            if (inputStream == null)
            {
                throw new IOException("Classpath resource not found: " + resourcePath);
            }
            return inputStream;
        };
    }

    /**
     * Returns a {@link FontSource} that reads a font file from the file system.
     *
     * @param file
     * @return
     */
    public static FontSource fontFromFile(File file)
    {
        return () -> new FileInputStream(file);
    }

    /**
     * Returns a {@link FontSource} that reads a font file from an in-memory byte array.
     *
     * @param data
     * @return
     */
    public static FontSource fontFromBytes(byte[] data)
    {
        return () -> new ByteArrayInputStream(data);
    }
}
