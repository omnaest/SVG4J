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

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;

public class TranslatingSVGElementWrapper implements SVGElement
{
	private SVGElement	element;
	private double		x;
	private double		y;

	public TranslatingSVGElementWrapper(SVGElement element, double x, double y)
	{
		super();
		this.element = element;
		this.x = x;
		this.y = y;
	}

	@Override
	public RawSVGElement render()
	{
		return this.element	.render()
							.translate(this.x, this.y);
	}

	@Override
	public String toString()
	{
		return "TranslatingSVGElementWrapper [element=" + this.element + ", x=" + this.x + ", y=" + this.y + "]";
	}

}
