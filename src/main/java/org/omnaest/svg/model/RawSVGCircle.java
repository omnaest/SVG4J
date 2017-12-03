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
package org.omnaest.svg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.omnaest.svg.model.DefaultRawSVGTransformer.SupplierConsumer;
import org.omnaest.utils.NumberUtils;

@XmlRootElement(name = "circle")
@XmlAccessorType(XmlAccessType.NONE)
public class RawSVGCircle extends RawSVGElement
{
	@XmlAttribute
	private String cx;

	@XmlAttribute
	private String cy;

	@XmlAttribute
	private String r;

	@XmlAttribute
	private String stroke;

	@XmlAttribute
	private String fill;

	@XmlAttribute(name = "fill-opacity")
	private String fillOpacity;

	@XmlAttribute(name = "stroke-opacity")
	private String strokeOpacity;

	@XmlAttribute
	private String style;

	public String getCx()
	{
		return this.cx;
	}

	public RawSVGCircle setCx(String cx)
	{
		this.cx = cx;
		return this;
	}

	public String getCy()
	{
		return this.cy;
	}

	public RawSVGCircle setCy(String cy)
	{
		this.cy = cy;
		return this;
	}

	public String getR()
	{
		return this.r;
	}

	public RawSVGCircle setR(String r)
	{
		this.r = r;
		return this;
	}

	public String getStroke()
	{
		return this.stroke;
	}

	public RawSVGCircle setStroke(String stroke)
	{
		this.stroke = stroke;
		return this;
	}

	public String getFill()
	{
		return this.fill;
	}

	public RawSVGCircle setFill(String fill)
	{
		this.fill = fill;
		return this;
	}

	public String getFillOpacity()
	{
		return this.fillOpacity;
	}

	public RawSVGCircle setFillOpacity(String fillOpacity)
	{
		this.fillOpacity = fillOpacity;
		return this;
	}

	public String getStrokeOpacity()
	{
		return this.strokeOpacity;
	}

	public RawSVGCircle setStrokeOpacity(String strokeOpacity)
	{
		this.strokeOpacity = strokeOpacity;
		return this;
	}

	@Override
	public String getStyle()
	{
		return this.style;
	}

	@Override
	public RawSVGCircle setStyle(String style)
	{
		this.style = style;
		return this;
	}

	@Override
	public String toString()
	{
		return "RawSVGCircle [cx=" + this.cx + ", cy=" + this.cy + ", r=" + this.r + ", stroke=" + this.stroke + ", fill=" + this.fill + ", fillOpacity="
				+ this.fillOpacity + ", strokeOpacity=" + this.strokeOpacity + ", style=" + this.style + "]";
	}

	@Override
	public RawSVGTransformer transformer()
	{
		return new DefaultRawSVGTransformer(this, new SupplierConsumer()
		{
			@Override
			public void accept(Double value)
			{
				RawSVGCircle.this.setCx(NumberUtils	.formatter()
													.format(value));
			}

			@Override
			public Double get()
			{
				return NumberUtils.toDouble(RawSVGCircle.this.getCx());
			}
		}, new SupplierConsumer()
		{
			@Override
			public void accept(Double value)
			{
				RawSVGCircle.this.setCy(NumberUtils	.formatter()
													.format(value));
			}

			@Override
			public Double get()
			{
				return NumberUtils.toDouble(RawSVGCircle.this.getCy());
			}
		}, new SupplierConsumer()
		{
			@Override
			public void accept(Double value)
			{
				RawSVGCircle.this.setR(NumberUtils	.formatter()
													.format(value));
			}

			@Override
			public Double get()
			{
				return NumberUtils.toDouble(RawSVGCircle.this.getR());
			}
		});
	}

}
