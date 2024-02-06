package org.omnaest.svg.internal.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RGBAUtilsTest
{

    @Test
    public void testToRGBAString() throws Exception
    {
        assertEquals("rgba(10,20,30,0.3333)", RGBAUtils.toRGBAString(10, 20, 30, 1.0 / 3.0));
    }

}
