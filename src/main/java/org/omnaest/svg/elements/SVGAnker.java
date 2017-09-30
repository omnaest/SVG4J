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
package org.omnaest.svg.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGAnkerElement;

/**
 * @see SVGElement
 * @author omnaest
 */
public class SVGAnker implements SVGElement
{
	private String href;

	private List<SVGElement> elements = new ArrayList<>();

	public SVGAnker(String href)
	{
		super();
		this.href = href;
	}

	public SVGAnker addElement(SVGElement element)
	{
		if (element != null)
		{
			this.elements.add(element);
		}
		return this;
	}

	public SVGAnker setElements(List<SVGElement> elements)
	{
		if (elements != null)
		{
			this.elements.addAll(elements);
		}
		return this;
	}

	@Override
	public RawSVGAnkerElement render()
	{
		return new RawSVGAnkerElement()	.setHref(this.href)
										.setElements(this.elements	.stream()
																	.map(element -> element.render())
																	.collect(Collectors.toList()));
	}

}
