package org.omnaest.svg.text;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Structural guard behind S2-AC7: proves there is no system-font code path anywhere in the org.omnaest.svg.text main
 * sources.
 */
public class TextMetricsStructuralTest
{
    private static final String[] FORBIDDEN_TOKENS = {"Font.decode", "GraphicsEnvironment.getAvailableFontFamilyNames"};

    @Test
    public void testMainSourcesContainNoForbiddenSystemFontTokens() throws IOException
    {
        List<File> sourceFiles = this.findTextPackageMainSources();
        assertTrue("Expected to find main sources under org.omnaest.svg.text", !sourceFiles.isEmpty());

        for (File sourceFile : sourceFiles)
        {
            String content = new String(Files.readAllBytes(sourceFile.toPath()), java.nio.charset.StandardCharsets.US_ASCII);
            for (String forbiddenToken : FORBIDDEN_TOKENS)
            {
                assertFalse("Found forbidden token '" + forbiddenToken + "' in " + sourceFile, content.contains(forbiddenToken));
            }
        }
    }

    @Test
    public void testMainSourcesContainNoFontConstructionFromFamilyName() throws IOException
    {
        List<File> sourceFiles = this.findTextPackageMainSources();

        for (File sourceFile : sourceFiles)
        {
            String content = new String(Files.readAllBytes(sourceFile.toPath()), java.nio.charset.StandardCharsets.US_ASCII);
            // "new Font(" is the family-name/style/size constructor - createFont(int, InputStream) is the only
            // permitted way to obtain a java.awt.Font in this package.
            assertFalse("Found a 'new Font(' construction (family-name based) in " + sourceFile, content.contains("new Font("));
        }
    }

    private List<File> findTextPackageMainSources() throws IOException
    {
        File packageRoot = new File("src/main/java/org/omnaest/svg/text");
        assertTrue("Expected package directory to exist: " + packageRoot.getAbsolutePath(), packageRoot.isDirectory());

        try (Stream<java.nio.file.Path> paths = Files.walk(packageRoot.toPath()))
        {
            return paths.filter(p -> p.toString()
                                      .endsWith(".java"))
                        .map(java.nio.file.Path::toFile)
                        .collect(Collectors.toList());
        }
    }
}
