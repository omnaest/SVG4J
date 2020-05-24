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
package org.omnaest.svg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.omnaest.svg.SVGDrawer.ResolutionProvider;
import org.omnaest.svg.model.RawSVGRoot;
import org.omnaest.utils.StringUtils;
import org.omnaest.utils.XMLHelper;

/**
 * {@link SVGUtils}
 * 
 * @see #getDrawer(int, int)
 * @see SVGChartUtils
 * @author omnaest
 */
public class SVGUtils
{

    /**
     * @see SVGDrawer
     * @param width
     * @param height
     * @return
     */
    public static SVGDrawer getDrawer(int width, int height)
    {
        return new SVGDrawer(width, height);
    }

    public static SVGDrawer getDrawer(ResolutionProvider displayResolution)
    {
        return getDrawer(displayResolution.getWidth(), displayResolution.getHeight());
    }

    public static SVGDrawer getDrawer(int originX, int originY, int width, int height)
    {
        return new SVGDrawer(originX, originY, width, height);
    }

    public static SVGDrawer getDrawer(InputStream svgInputStream) throws IOException
    {
        return new SVGDrawer(parse(svgInputStream));
    }

    public static SVGDrawer getDrawer(Reader reader) throws IOException
    {
        return new SVGDrawer(parse(reader));
    }

    public static SVGDrawer getDrawer(File svgFile) throws IOException
    {
        return new SVGDrawer(parse(svgFile));
    }

    public static SVGDrawer getDrawer(String svg)
    {
        return new SVGDrawer(parse(svg));
    }

    protected static RawSVGRoot parse(InputStream inputStream) throws IOException
    {
        return parse(StringUtils.toString(inputStream, StandardCharsets.UTF_8));
    }

    protected static RawSVGRoot parse(Reader reader) throws IOException
    {
        return parse(StringUtils.toString(reader));
    }

    protected static RawSVGRoot parse(File file) throws IOException
    {
        return parse(FileUtils.readFileToString(file, "utf-8"));
    }

    protected static RawSVGRoot parse(String svg)
    {
        return XMLHelper.parse()
                        .from(svg)
                        .withSAXParser()
                        .enforceNamespace("http://www.w3.org/2000/svg")
                        .into(RawSVGRoot.class);
    }
}
