package org.omnaest.svg.component;

import java.util.function.Supplier;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.TranslationArea;

public class TranslationAreaImpl extends GenericTranslationAreaImpl<TranslationArea> implements TranslationArea
{
	public TranslationAreaImpl(SVGElementAndRawElementConsumer<?> parent, Supplier<Double> parentWidth, Supplier<Double> parentHeight)
	{
		super(parent, parentWidth, parentHeight);
	}
}