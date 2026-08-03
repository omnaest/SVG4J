package org.omnaest.svg.text;

/**
 * Deterministic text measurement for a single font at a single font size.
 * <p>
 * Implementations MUST be deterministic across JVM invocations for the same font bytes and the same input string, and MUST
 * NOT fall back to any system/logical font when the requested font cannot be loaded.
 *
 * @see TextMetricsUtils
 * @author omnaest
 */
public interface TextMetrics
{
    /**
     * Returns the advance width of the given text at {@link #getFontSize()}. Returns exactly 0 for an empty string.
     *
     * @param text
     * @return
     */
    double measureWidth(String text);

    /**
     * Returns the ascent of the font at {@link #getFontSize()}.
     *
     * @return
     */
    double getAscent();

    /**
     * Returns the descent of the font at {@link #getFontSize()}.
     *
     * @return
     */
    double getDescent();

    /**
     * Returns the recommended line height of the font at {@link #getFontSize()}.
     *
     * @return
     */
    double getLineHeight();

    /**
     * Returns the font size this instance measures at.
     *
     * @return
     */
    double getFontSize();

    /**
     * Returns a new {@link TextMetrics} instance for the same font at a different font size. Does not mutate this instance.
     *
     * @param fontSize
     * @return
     */
    TextMetrics withFontSize(double fontSize);
}
