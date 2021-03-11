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
package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stop")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGStopElement
{
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String offset;

	@XmlAttribute
	private String style;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOffset()
	{
		return offset;
	}

	public void setOffset(String offset)
	{
		this.offset = offset;
	}

	public String getStyle()
	{
		return style;
	}

	public RawSVGStopElement setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGStopElement [id=" + id + ", offset=" + offset + ", style=" + style + "]";
	}

}
