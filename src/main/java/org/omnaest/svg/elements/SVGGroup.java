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
package org.omnaest.svg.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.svg.model.RawSVGGroupElement;

/**
 * @see SVGElement
 * @author omnaest
 */
public class SVGGroup implements SVGElement
{
	private double				opacity		= 1.0;
	private List<SVGElement>	elements	= new ArrayList<>();

	public SVGGroup()
	{
		super();
	}

	public SVGGroup addElement(SVGElement element)
	{
		if (element != null)
		{
			this.elements.add(element);
		}
		return this;
	}

	public SVGGroup setElements(List<SVGElement> elements)
	{
		if (elements != null)
		{
			this.elements.addAll(elements);
		}
		return this;
	}

	@Override
	public RawSVGElement render()
	{
		return new RawSVGGroupElement()	.setElements(this.elements	.stream()
																	.map(element -> element.render())
																	.collect(Collectors.toList()))
										.setOpacity("" + this.opacity);
	}

	public SVGGroup setOpacity(double opacity)
	{
		this.opacity = opacity;
		return this;
	}

}
