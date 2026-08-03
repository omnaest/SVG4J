package org.omnaest.svg.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TextMetricsTest
{
    private static final String FONT_RESOURCE = "/org/omnaest/svg/text/Inter-Regular.ttf";

    private static TextMetrics newInterMetrics(double fontSize)
    {
        return TextMetricsUtils.newTextMetrics(TextMetricsUtils.fontFromClasspathResource(FONT_RESOURCE), fontSize);
    }

    @Test
    public void testEmptyStringMeasuresZero()
    {
        TextMetrics metrics = newInterMetrics(14);
        assertEquals(0, metrics.measureWidth(""), 0.0);
    }

    @Test
    public void testProportionalNotFallback()
    {
        TextMetrics metrics = newInterMetrics(14);
        double wide = metrics.measureWidth("WWWWW");
        double narrow = metrics.measureWidth("iiiii");
        assertTrue("Expected 'WWWWW' to be strictly wider than 'iiiii', got wide=" + wide + " narrow=" + narrow, wide > narrow);
    }

    @Test
    public void testLoadedFontFamilyIsInter()
    {
        // load the same font bytes directly via AWT to inspect its family name
        Font font;
        try (java.io.InputStream inputStream = TextMetricsTest.class.getResourceAsStream(FONT_RESOURCE))
        {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
        assertEquals("Inter", font.getFamily(java.util.Locale.ROOT));
    }

    @Test
    public void testDeterminismAcrossIndependentInstances()
    {
        TextMetrics metricsA = newInterMetrics(14);
        TextMetrics metricsB = newInterMetrics(14);

        List<String> samples = Arrays.asList("Idle", "Done", "s1 --> s2", "Hello World", "MiXeD CaSe", "   spaced   ");

        for (String sample : samples)
        {
            assertEquals("Mismatch for sample [" + sample + "]", metricsA.measureWidth(sample), metricsB.measureWidth(sample), 0.0);
        }
    }

    @Test
    public void testScalingIsApproximatelyProportionalAndNonMutating()
    {
        TextMetrics base = newInterMetrics(14);
        double widthAt14 = base.measureWidth("Hello World");

        TextMetrics scaled = base.withFontSize(28);
        double widthAt28 = scaled.measureWidth("Hello World");

        double ratio = widthAt28 / widthAt14;
        assertTrue("Expected roughly 2x scaling, got ratio=" + ratio, ratio > 1.9 && ratio < 2.1);

        // original instance must not have been mutated
        assertEquals(14, base.getFontSize(), 0.0);
        assertEquals(widthAt14, base.measureWidth("Hello World"), 0.0);
        assertEquals(28, scaled.getFontSize(), 0.0);
    }

    @Test
    public void testAscentDescentLineHeight()
    {
        TextMetrics metrics = newInterMetrics(14);
        assertTrue(metrics.getAscent() > 0);
        assertTrue(metrics.getDescent() > 0);
        assertTrue(metrics.getLineHeight() >= metrics.getAscent() + metrics.getDescent());
    }

    @Test
    public void testMissingFontResourceThrows()
    {
        String missingResource = "/does/not/exist.ttf";
        try
        {
            TextMetricsUtils.newTextMetrics(TextMetricsUtils.fontFromClasspathResource(missingResource), 14);
            fail("Expected an exception for a missing font resource");
        }
        catch (Exception e)
        {
            String message = messageChain(e);
            assertTrue("Expected the exception message chain to name the missing resource, got: " + message, message.contains(missingResource));
        }
    }

    private static String messageChain(Throwable throwable)
    {
        StringBuilder stringBuilder = new StringBuilder();
        Throwable current = throwable;
        while (current != null)
        {
            if (current.getMessage() != null)
            {
                stringBuilder.append(current.getMessage())
                             .append(" | ");
            }
            current = current.getCause();
        }
        return stringBuilder.toString();
    }

    @Test
    public void testMissingFontResourceDoesNotReturnWorkingInstance()
    {
        // guard: confirm the throw happens before any usable instance is handed back - i.e. no working instance
        // measuring anything is ever observable for a missing resource.
        boolean threw = false;
        try
        {
            TextMetrics metrics = TextMetricsUtils.newTextMetrics(TextMetricsUtils.fontFromClasspathResource("/does/not/exist.ttf"), 14);
            metrics.measureWidth("x");
        }
        catch (Exception e)
        {
            threw = true;
        }
        assertTrue("Expected construction or first use to throw rather than silently returning a working instance", threw);
    }
}
