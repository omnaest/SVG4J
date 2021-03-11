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
package org.omnaest.svg.component;

import org.omnaest.svg.SVGDrawer.GenericTranslationArea;
import org.omnaest.svg.SVGDrawer.ParentAccessor;
import org.omnaest.svg.elements.TranslatingSVGElementWrapper;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;

@SuppressWarnings("unchecked")
public class GenericTranslationAreaImpl<R extends GenericTranslationArea<R>> extends AbstractSVGElementConsumer<R> implements GenericTranslationArea<R>
{
	private ParentAccessor	parent;
	private double			translationX	= 0.0;
	private double			translationY	= 0.0;

	public GenericTranslationAreaImpl(ParentAccessor parent)
	{
		super();
		this.parent = parent;
	}

	@Override
	public ParentAccessor getParent()
	{
		return this.parent;
	}

	@Override
	public double getRawTranslationX()
	{
		return this.translationX;
	}

	@Override
	public double getRawTranslationY()
	{
		return this.translationY;
	}

	@Override
	public R withTranslationX(double x)
	{
		this.translationX = x;
		return (R) this;
	}

	@Override
	public R withTranslationY(double y)
	{
		this.translationY = y;
		return (R) this;
	}

	@Override
	public R withRelativeTranslationX(double x)
	{
		double parentWidth = this.parent.getWidth();
		return this.withTranslationX(parentWidth * x);
	}

	@Override
	public R withRelativeTranslationY(double y)
	{
		double parentHeight = this.parent.getHeight();
		return this.withTranslationY(parentHeight * y);
	}

	@Override
	public R add(SVGElement element)
	{
		this.parent	.getConsumer()
					.add(new TranslatingSVGElementWrapper(element, this.translationX, this.translationY));
		return (R) this;
	}

	@Override
	public R addRawElement(RawSVGElement rawElement)
	{
		this.parent	.getConsumer()
					.addRawElement(rawElement.translate(this.translationX, this.translationY));
		return (R) this;
	}

	@Override
	public String toString()
	{
		return "GenericTranslationAreaImpl [parent=" + this.parent + ", translationX=" + this.translationX + ", translationY=" + this.translationY + "]";
	}

}
