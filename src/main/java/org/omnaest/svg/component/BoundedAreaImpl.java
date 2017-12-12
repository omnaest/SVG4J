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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.SVGDrawer.ParentAccessor;
import org.omnaest.svg.elements.ScalingSVGElementWrapper;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.vector.ModifiableVector;
import org.omnaest.vector.Vector;

public class BoundedAreaImpl extends GenericTranslationAreaImpl<BoundedArea> implements BoundedArea, ParentAccessor
{
	private double	height;
	private double	width;

	private Double	scalingHeight	= null;
	private Double	scalingWidth	= null;

	public BoundedAreaImpl(ParentAccessor parent)
	{
		super(parent);
		this.width = parent.getWidth();
		this.height = parent.getHeight();
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
		double parentHeight = this	.getParent()
									.getHeight();
		return this.withHeight(parentHeight * relativeHeight);
	}

	@Override
	public BoundedArea withRelativeWidth(double relativeWidth)
	{
		double parentWidth = this	.getParent()
									.getWidth();
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
		return new BoundedAreaImpl(this.asParentAccessor());
	}

	@Override
	public SVGElementAndRawElementConsumer<?> getConsumer()
	{
		return this;
	}

	@Override
	public double getTranslationX()
	{
		return this.getRawTranslationX();
	}

	@Override
	public double getTranslationY()
	{
		return this.getRawTranslationY();
	}

	protected ParentAccessor asParentAccessor()
	{
		return this;
	}

	@Override
	public BoundedArea withPadding(double borderSize)
	{
		double tx = borderSize;
		double ty = borderSize;
		double width = this.getRawWidth() - 2 * tx;
		double height = this.getRawHeight() - 2 * ty;
		return this	.newSubArea()
					.withTranslationX(tx)
					.withTranslationY(ty)
					.withHeight(height)
					.withWidth(width)
					.withScalingHeight(this.scalingHeight)
					.withScalingWidth(this.scalingWidth);
	}

	@Override
	public BoundedArea withRelativeSizedPadding(double relativeBorderSize)
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

	@Override
	public BoundedArea coverageMergeWith(BoundedArea boundedArea)
	{
		CoordinatesTranslator coordinatesTranslator = this	.getCoordinatesTranslator()
															.relatedTo(boundedArea);

		double otherX1 = coordinatesTranslator.translateX(0);
		double otherY1 = coordinatesTranslator.translateY(0);

		double x1 = Math.min(0, otherX1);
		double y1 = Math.min(0, otherY1);

		double otherX2 = coordinatesTranslator.translateX(boundedArea.getRawWidth());
		double otherY2 = coordinatesTranslator.translateY(boundedArea.getRawHeight());

		double x2 = Math.max(this.getRawWidth(), otherX2);
		double y2 = Math.max(this.getRawHeight(), otherY2);

		double width = x2 - x1;
		double height = y2 - y1;

		return new BoundedAreaImpl(this).withWidth(width)
										.withHeight(height)
										.withTranslationX(x1)
										.withTranslationY(y1);
	}

	private static class CoordinatesTranslatorImpl implements CoordinatesTranslator
	{
		private Vector	parentTranslationVector	= Vector.of(0.0, 0.0);
		private Vector	otherTranslationVector;

		public CoordinatesTranslatorImpl(BoundedArea boundedArea)
		{
			this.otherTranslationVector = this.determineTranslationVector(boundedArea);
		}

		public CoordinatesTranslatorImpl(BoundedArea boundedArea, Vector parentTranslationVector)
		{
			this.parentTranslationVector = parentTranslationVector;
			this.otherTranslationVector = this.determineTranslationVector(boundedArea);
		}

		private Vector determineTranslationVector(BoundedArea boundedArea)
		{
			ModifiableVector translation = ModifiableVector.of(0.0, 0.0);
			{
				ParentAccessor parent = boundedArea;
				while (parent != null)
				{
					translation.addX(parent.getTranslationX());
					translation.addY(parent.getTranslationY());

					parent = parent.getParent();
				}
			}
			return translation;
		}

		@Override
		public CoordinatesTranslator relatedTo(BoundedArea boundedArea)
		{
			return new CoordinatesTranslatorImpl(boundedArea, this.otherTranslationVector);
		}

		@Override
		public double translateX(double x)
		{
			return this.otherTranslationVector.getX() - this.parentTranslationVector.getX() + x;
		}

		@Override
		public double translateY(double y)
		{
			return this.otherTranslationVector.getY() - this.parentTranslationVector.getY() + y;
		}
	}

	@Override
	public CoordinatesTranslator getCoordinatesTranslator()
	{
		return new CoordinatesTranslatorImpl(this);
	}

	@Override
	public String toString()
	{
		return "BoundedAreaImpl [getRawHeight()=" + this.getRawHeight() + ", getRawWidth()=" + this.getRawWidth() + ", getHeight()=" + this.getHeight()
				+ ", getWidth()=" + this.getWidth() + ", getRawTranslationX()=" + this.getRawTranslationX() + ", getRawTranslationY()="
				+ this.getRawTranslationY() + "]";
	}

}