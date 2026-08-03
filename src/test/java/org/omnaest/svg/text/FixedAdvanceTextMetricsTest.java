package org.omnaest.svg.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FixedAdvanceTextMetricsTest
{
    @Test
    public void testEmptyStringMeasuresZero()
    {
        TextMetrics metrics = TextMetricsUtils.newFixedAdvanceTextMetrics(0.6, 14);
        assertEquals(0, metrics.measureWidth(""), 0.0);
    }

    @Test
    public void testDeterministic()
    {
        TextMetrics metricsA = TextMetricsUtils.newFixedAdvanceTextMetrics(0.6, 14);
        TextMetrics metricsB = TextMetricsUtils.newFixedAdvanceTextMetrics(0.6, 14);

        assertEquals(metricsA.measureWidth("Hello World"), metricsB.measureWidth("Hello World"), 0.0);
    }

    @Test
    public void testStrictlyMonotonicInStringLength()
    {
        TextMetrics metrics = TextMetricsUtils.newFixedAdvanceTextMetrics(0.6, 14);

        double previous = metrics.measureWidth("");
        String text = "";
        for (int i = 1; i <= 10; i++)
        {
            text = text + "x";
            double current = metrics.measureWidth(text);
            assertTrue("Expected strictly increasing width at length " + i, current > previous);
            previous = current;
        }
    }

    @Test
    public void testWithFontSizeDoesNotMutateOriginal()
    {
        TextMetrics base = TextMetricsUtils.newFixedAdvanceTextMetrics(0.6, 14);
        double widthAt14 = base.measureWidth("abc");

        TextMetrics scaled = base.withFontSize(28);

        assertEquals(14, base.getFontSize(), 0.0);
        assertEquals(widthAt14, base.measureWidth("abc"), 0.0);
        assertEquals(28, scaled.getFontSize(), 0.0);
        assertEquals(widthAt14 * 2, scaled.measureWidth("abc"), 0.0001);
    }
}
