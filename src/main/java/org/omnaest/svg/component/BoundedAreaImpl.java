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

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.elements.ScalingSVGElementWrapper;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;

public class BoundedAreaImpl extends GenericTranslationAreaImpl<BoundedArea> implements BoundedArea
{
	private double	height;
	private double	width;

	private Double	scalingHeight	= null;
	private Double	scalingWidth	= null;

	public BoundedAreaImpl(SVGElementAndRawElementConsumer<?> parent, Supplier<Double> parentWidth, Supplier<Double> parentHeigth)
	{
		super(parent, parentWidth, parentHeigth);
		this.width = parentWidth.get();
		this.height = parentHeigth.get();
	}

	@Override
	public BoundedArea withTranslationX(double x)
	{
		super.withTranslationX(x);
		return this;
	}

	@Override
	public BoundedArea withTranslationY(double y)
	{
		super.withTranslationY(y);
		return this;
	}

	@Override
	public BoundedArea withRelativeTranslationX(double x)
	{
		super.withRelativeTranslationX(x);
		return this;
	}

	@Override
	public BoundedArea withRelativeTranslationY(double y)
	{
		super.withRelativeTranslationY(y);
		return this;
	}

	@Override
	public BoundedArea withHeight(double height)
	{
		this.height = height;
		return this;
	}

	@Override
	public BoundedArea withWidth(double width)
	{
		this.width = width;
		return this;
	}

	@Override
	public double getRawHeight()
	{
		return this.height;
	}

	@Override
	public double getRawWidth()
	{
		return this.width;
	}

	@Override
	public double getHeight()
	{
		return this.height / this.determineScaleY();
	}

	@Override
	public double getWidth()
	{
		return this.width / this.determineScaleX();
	}

	@Override
	public BoundedArea withRelativeHeight(double relativeHeight)
	{
		double parentHeight = this.parentHeight.get();
		return this.withHeight(parentHeight * relativeHeight);
	}

	@Override
	public BoundedArea withRelativeWidth(double relativeWidth)
	{
		double parentWidth = this.parentWidth.get();
		return this.withWidth(parentWidth * relativeWidth);
	}

	@Override
	public BoundedArea withScalingHeight(Double scalingHeight)
	{
		this.scalingHeight = scalingHeight;
		return this;
	}

	@Override
	public BoundedArea withScalingWidth(double scalingWidth)
	{
		return this.withScalingWidth(Double.valueOf(scalingWidth));
	}

	@Override
	public BoundedArea withScalingHeight(double scalingHeight)
	{
		return this.withScalingHeight(Double.valueOf(scalingHeight));
	}

	@Override
	public BoundedArea withScalingWidth(Double scalingWidth)
	{
		this.scalingWidth = scalingWidth;
		return this;
	}

	@Override
	public BoundedArea add(SVGElement element)
	{
		double scaleX = this.determineScaleX();
		double scaleY = this.determineScaleY();
		return super.add(new ScalingSVGElementWrapper(element, scaleX, scaleY));
	}

	private double determineScaleY()
	{
		return this.scalingHeight != null ? this.height / this.scalingHeight : 1.0;
	}

	private double determineScaleX()
	{
		return this.scalingWidth != null ? this.width / this.scalingWidth : 1.0;
	}

	@Override
	public BoundedArea addRawElement(RawSVGElement rawElement)
	{
		return super.addRawElement(rawElement.scale(this.determineScaleX(), this.determineScaleY()));
	}

	@Override
	public BoundedArea newSubArea()
	{
		return new BoundedAreaImpl(this, () -> this.getWidth(), () -> this.getHeight());
	}

	@Override
	public BoundedArea withBorder(double borderSize)
	{
		double tx = borderSize;
		double ty = borderSize;
		double relativeWidth = this.parentWidth.get() - 2 * tx;
		double relativeHeight = this.parentHeight.get() - 2 * ty;
		return this	.newSubArea()
					.withTranslationX(tx)
					.withTranslationY(ty)
					.withHeight(relativeHeight)
					.withWidth(relativeWidth)
					.withScalingHeight(this.scalingHeight)
					.withScalingWidth(this.scalingWidth);
	}

	@Override
	public BoundedArea withRelativeSizedBorder(double relativeBorderSize)
	{
		double tx = relativeBorderSize;
		double ty = relativeBorderSize;
		double relativeWidth = 1.0 - 2 * tx;
		double relativeHeight = 1.0 - 2 * ty;
		return this	.newSubArea()
					.withRelativeTranslationX(tx)
					.withRelativeTranslationY(ty)
					.withRelativeHeight(relativeHeight)
					.withRelativeWidth(relativeWidth)
					.withScalingHeight(this.scalingHeight)
					.withScalingWidth(this.scalingWidth);
	}

	@Override
	public List<BoundedArea> asVerticalSlices(int numberOfSlices)
	{
		return IntStream.range(0, numberOfSlices)
						.mapToObj(index -> this	.newSubArea()
												.withRelativeTranslationX(index * 1.0 / numberOfSlices)
												.withRelativeWidth(1.0 / numberOfSlices))
						.collect(Collectors.toList());
	}

	@Override
	public List<BoundedArea> asHorizontalSlices(int numberOfSlices)
	{
		return IntStream.range(0, numberOfSlices)
						.mapToObj(index -> this	.newSubArea()
												.withRelativeTranslationY(index * 1.0 / numberOfSlices)
												.withRelativeHeight(1.0 / numberOfSlices))
						.collect(Collectors.toList());
	}

}