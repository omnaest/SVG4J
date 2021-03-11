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

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.elements.base.SVGCompositeElement;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;

public abstract class AbstractSVGElementConsumer<R extends SVGElementAndRawElementConsumer<R>> implements SVGElementAndRawElementConsumer<R>
{

	@SuppressWarnings("unchecked")
	@Override
	public R addRawElements(SVGDrawer svgDrawer)
	{
		this.addRawElements(svgDrawer	.renderAsResult()
										.getRawSVGRoot()
										.getElements());
		return (R) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R addRawElements(Collection<RawSVGElement> rawElements)
	{
		if (rawElements != null)
		{
			rawElements.forEach(this::addRawElement);
		}
		return (R) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R addAll(Iterable<SVGElement> elements)
	{
		if (elements != null)
		{
			elements.forEach(this::add);
		}
		return (R) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R add(SVGCompositeElement element)
	{
		if (element != null)
		{
			this.addAll(element.getElements());
		}
		return (R) this;
	}

	@Override
	public R addAll(Stream<SVGElement> elements)
	{
		return this.addAll(elements.collect(Collectors.toList()));
	}

}
