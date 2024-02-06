package org.omnaest.svg.internal.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class RGBAUtils
{
    public static String toRGBAString(int red, int green, int blue, double opacity)
    {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(4);

        return "rgba(" + red + "," + green + "," + blue + "," + numberFormat.format(opacity) + ")";
    }
}
