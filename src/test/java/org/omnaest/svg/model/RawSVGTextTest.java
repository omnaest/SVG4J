/*******************************************************************************
 * Copyright 2021 Danny Kunz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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

import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.omnaest.svg.elements.SVGText;
import org.omnaest.utils.XMLHelper;

public class RawSVGTextTest
{
    @Test
    public void testContent()
    {
        RawSVGText text = new RawSVGText();
        text.setText("value");
        assertEquals("value", text.getText());
    }

    @Test
    public void testContentWithTSpan()
    {
        RawSVGText text = new RawSVGText();
        text.addTSpan(new RawSVGTSpan().setContent("span value"));
        assertEquals("span value", text.getText());
    }

    @Test
    public void testContentSerialization()
    {
        try
        {
            RawSVGText text = new RawSVGText();
            text.addTSpan(new RawSVGTSpan().setContent("span value"));

            String xml = XMLHelper.serializer()
                                  .withoutHeader()
                                  .withCompactPrint()
                                  .serialize(text);
            //            System.out.println(xml);
            RawSVGText clone = XMLHelper.parse(xml, RawSVGText.class);
            assertEquals("span value", clone.getText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void testContentSerialization2()
    {
        try
        {
            RawSVGText text = new RawSVGText();
            text.addTSpan(new RawSVGTSpan().setContent("span value1"))
                .addTSpan(new RawSVGTSpan().setContent("span value2"));

            String xml = XMLHelper.serializer()
                                  .withoutHeader()
                                  .withCompactPrint()
                                  .serialize(text);
            //            System.out.println(xml);
            RawSVGText clone = XMLHelper.parse(xml, RawSVGText.class);
            assertEquals("span value1span value2", clone.getText());
            List<Object> rawContent = clone.getRawContent();
            assertEquals(2, rawContent.size());
            assertTrue(rawContent.iterator()
                                 .next() instanceof RawSVGTSpan);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

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
