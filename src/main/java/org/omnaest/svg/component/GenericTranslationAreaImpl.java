package org.omnaest.svg.component;

import java.util.function.Supplier;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.GenericTranslationArea;
import org.omnaest.svg.elements.TranslatingSVGElementWrapper;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;

@SuppressWarnings("unchecked")
public class GenericTranslationAreaImpl<R extends GenericTranslationArea<R>> extends AbstractSVGElementConsumer<R>
		implements GenericTranslationArea<R>
{
	private double	x	= 0.0;
	private double	y	= 0.0;

	protected Supplier<Double>					parentWidth;
	protected Supplier<Double>					parentHeight;
	private SVGElementAndRawElementConsumer<?>	parent;

	public GenericTranslationAreaImpl(SVGElementAndRawElementConsumer<?> parent, Supplier<Double> parentWidth, Supplier<Double> parentHeight)
	{
		super();
		this.parent = parent;

		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;
	}

	@Override
	public R withTranslationX(double x)
	{
		this.x = x;
		return (R) this;
	}

	@Override
	public R withTranslationY(double y)
	{
		this.y = y;
		return (R) this;
	}

	@Override
	public R withRelativeTranslationX(double x)
	{
		double parentWidth = this.parentWidth.get();
		return this.withTranslationX(parentWidth * x);
	}

	@Override
	public R withRelativeTranslationY(double y)
	{
		double parentHeight = this.parentHeight.get();
		return this.withTranslationY(parentHeight * y);
	}

	@Override
	public R add(SVGElement element)
	{
		this.parent.add(new TranslatingSVGElementWrapper(element, this.x, this.y));
		return (R) this;
	}

	@Override
	public R addRawElement(RawSVGElement rawElement)
	{
		this.parent.addRawElement(rawElement.translate(this.x, this.y));
		return (R) this;
	}

}