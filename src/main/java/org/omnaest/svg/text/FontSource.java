package org.omnaest.svg.text;

import java.io.IOException;
import java.io.InputStream;

/**
 * Extension point for where font bytes come from (classpath resource, file, byte array, ...).
 *
 * @see TextMetricsUtils#fontFromClasspathResource(String)
 * @see TextMetricsUtils#fontFromFile(java.io.File)
 * @see TextMetricsUtils#fontFromBytes(byte[])
 * @author omnaest
 */
public interface FontSource
{
    /**
     * Opens a fresh {@link InputStream} of the raw font file bytes (e.g. TrueType). The caller is responsible for closing
     * the returned stream.
     *
     * @return
     * @throws IOException
     */
    InputStream openStream() throws IOException;
}
