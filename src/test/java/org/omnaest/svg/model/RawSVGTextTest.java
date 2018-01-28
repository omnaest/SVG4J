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
package org.omnaest.svg.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;
import org.omnaest.svg.elements.SVGText;

public class RawSVGTextTest
{

    @Test
    public void testTransformer() throws Exception
    {
        RawSVGText rawSVGText = new SVGText(0, 0, "test").setFontSize(50)
                                                         .render();

        String style = rawSVGText.transformer()
                                 .scale(1.0, 0.5)
                                 .getStyle();

        //System.out.println(style);
        assertEquals("font-size:25px", style);
    }

    @Test
    public void testTransformer2() throws Exception
    {
        RawSVGText rawSVGText = new SVGText(0, 0, "test").setFontSize(50)
                                                         .setRotation(90)
                                                         .render();

        String style = rawSVGText.transformer()
                                 .scale(1.5, 0.5)
                                 .getStyle();

        //System.out.println(style);
        assertEquals("font-size:75px", style);
    }

    @Test
    public void testTransformer3() throws Exception
    {
        RawSVGText rawSVGText = new SVGText(0, 0, "test").setFontSize(50)
                                                         .setRotation(-90)
                                                         .render();

        String style = rawSVGText.transformer()
                                 .scale(1.5, 0.5)
                                 .getStyle();

        //System.out.println(style);
        assertEquals("font-size:75px", style);
    }

    @Test
    public void testFontSizePattern()
    {
        boolean matches = Pattern.matches("font\\-size[ ]*\\:[ ]*([0-9]*\\.?[0-9]*)[ ]*(px)?", "font-size:38.400000000000006px");
        assertTrue(matches);
    }

}
