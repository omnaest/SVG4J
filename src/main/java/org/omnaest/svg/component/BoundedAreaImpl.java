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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.apache.commons.lang.ArrayUtils;
import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.SVGDrawer.ParentAccessor;
import org.omnaest.svg.elements.ScalingSVGElementWrapper;
import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.model.RawSVGElement;
import org.omnaest.vector.Matrix;
import org.omnaest.vector.Vector;

public class BoundedAreaImpl extends GenericTranslationAreaImpl<BoundedArea> implements BoundedArea, ParentAccessor
{
    private double height;
    private double width;

    private Double scalingHeight = null;
    private Double scalingWidth  = null;

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
        return this.height / this.getScaleY();
    }

    @Override
    public double getWidth()
    {
        return this.width / this.getScaleX();
    }

    @Override
    public BoundedArea withRelativeHeight(double relativeHeight)
    {
        double parentHeight = this.getParent()
                                  .getHeight();
        return this.withHeight(parentHeight * relativeHeight);
    }

    @Override
    public BoundedArea withRelativeWidth(double relativeWidth)
    {
        double parentWidth = this.getParent()
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
        double scaleX = this.getScaleX();
        double scaleY = this.getScaleY();
        return super.add(new ScalingSVGElementWrapper(element, scaleX, scaleY));
    }

    @Override
    public double getScaleY()
    {
        return this.scalingHeight != null ? this.height / this.scalingHeight : 1.0;
    }

    @Override
    public double getScaleX()
    {
        return this.scalingWidth != null ? this.width / this.scalingWidth : 1.0;
    }

    @Override
    public BoundedArea addRawElement(RawSVGElement rawElement)
    {
        return super.addRawElement(rawElement.scale(this.getScaleX(), this.getScaleY()));
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
        return this.newSubArea()
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
        return this.newSubArea()
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
        return this.asVerticalSlices(IntStream.range(0, numberOfSlices)
                                              .mapToDouble(index -> 1.0 / numberOfSlices));
    }

    @Override
    public List<BoundedArea> asVerticalSlices(DoubleStream proportions)
    {
        BiFunction<Double, Double, BoundedArea> subBoundedAreaFactory = (translationX, proportion) -> this.newSubArea()
                                                                                                          .withRelativeTranslationX(translationX)
                                                                                                          .withRelativeWidth(proportion);
        return this.asSlices(proportions, subBoundedAreaFactory);
    }

    @Override
    public List<BoundedArea> asVerticalSlices(double... proportions)
    {
        return this.asVerticalSlices(Arrays.asList(ArrayUtils.toObject(proportions))
                                           .stream()
                                           .mapToDouble(v -> v));
    }

    @Override
    public List<BoundedArea> asHorizontalSlices(int numberOfSlices)
    {
        return this.asHorizontalSlices(IntStream.range(0, numberOfSlices)
                                                .mapToDouble(index -> 1.0 / numberOfSlices));
    }

    private List<BoundedArea> asSlices(DoubleStream proportions, BiFunction<Double, Double, BoundedArea> subBoundedAreaFactory)
    {
        List<BoundedArea> retlist = new ArrayList<>();

        List<Double> proportionList = proportions.mapToObj(v -> v)
                                                 .collect(Collectors.toList());
        double sum = proportionList.stream()
                                   .mapToDouble(v -> v)
                                   .sum();

        int numberOfSlices = proportionList.size();
        double translation = 0;
        for (int ii = 0; ii < numberOfSlices; ii++)
        {
            double proportion = proportionList.get(ii) / sum;

            BoundedArea boundedArea = subBoundedAreaFactory.apply(translation, proportion);
            translation += proportion;
            retlist.add(boundedArea);
        }

        return retlist;
    }

    @Override
    public List<BoundedArea> asHorizontalSlices(double... proportions)
    {
        return this.asHorizontalSlices(Arrays.asList(ArrayUtils.toObject(proportions))
                                             .stream()
                                             .mapToDouble(v -> v));
    }

    @Override
    public List<BoundedArea> asHorizontalSlices(DoubleStream proportions)
    {
        BiFunction<Double, Double, BoundedArea> subBoundedAreaFactory = (translationY, proportion) -> this.newSubArea()
                                                                                                          .withRelativeTranslationY(translationY)
                                                                                                          .withRelativeHeight(proportion);
        return this.asSlices(proportions, subBoundedAreaFactory);
    }

    @Override
    public BoundedArea coverageMergeWith(BoundedArea boundedArea)
    {
        BoundedArea retval = this;

        if (boundedArea != null)
        {
            CoordinatesTranslator coordinatesTranslator = this.getCoordinatesTranslator()
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

            retval = new BoundedAreaImpl(this).withWidth(width)
                                              .withHeight(height)
                                              .withTranslationX(x1)
                                              .withTranslationY(y1);
        }

        return retval;
    }

    private static class CoordinatesTranslatorImpl implements CoordinatesTranslator
    {
        private Vector parentTranslationVector = Vector.of(0.0, 0.0);
        private Vector parentScaleVector       = Vector.of(1.0, 1.0);
        private Vector otherTranslationVector;
        private Vector otherScaleVector        = Vector.of(1.0, 1.0);

        public CoordinatesTranslatorImpl(BoundedArea boundedArea)
        {
            this.otherTranslationVector = this.determineTranslationVector(boundedArea);
            this.otherScaleVector = this.determineScaleVector(boundedArea);
        }

        public CoordinatesTranslatorImpl(BoundedArea boundedArea, Vector parentTranslationVector, Vector parentScaleVector)
        {
            this.parentTranslationVector = parentTranslationVector;
            this.parentScaleVector = parentScaleVector;
            this.otherTranslationVector = this.determineTranslationVector(boundedArea);
            this.otherScaleVector = this.determineScaleVector(boundedArea);
        }

        private Vector determineTranslationVector(BoundedArea boundedArea)
        {
            Vector translation = Vector.of(0.0, 0.0);
            {
                ParentAccessor parent = boundedArea;
                while (parent != null)
                {
                    double scaleX = parent.getScaleX();
                    double scaleY = parent.getScaleY();

                    translation = Matrix.builder()
                                        .addRow(scaleX, 0)
                                        .addRow(0, scaleY)
                                        .build()
                                        .multiply(translation)
                                        .add(Vector.of(parent.getTranslationX(), parent.getTranslationY()));

                    parent = parent.getParent();
                }
            }
            return translation;
        }

        private Vector determineScaleVector(BoundedArea boundedArea)
        {
            Vector scale = Vector.of(1.0, 1.0);
            {
                ParentAccessor parent = boundedArea;
                while (parent != null)
                {
                    double scaleX = parent.getScaleX();
                    double scaleY = parent.getScaleY();

                    scale = Matrix.builder()
                                  .addRow(scaleX, 0)
                                  .addRow(0, scaleY)
                                  .build()
                                  .multiply(scale);

                    parent = parent.getParent();
                }
            }
            return scale;
        }

        @Override
        public CoordinatesTranslator relatedTo(BoundedArea boundedArea)
        {
            return new CoordinatesTranslatorImpl(boundedArea, this.otherTranslationVector, this.otherScaleVector);
        }

        @Override
        public double translateX(double x)
        {
            double scaleX = this.otherScaleVector.getX();
            double parentScaleX = this.parentScaleVector.getX();
            return (this.otherTranslationVector.getX() - this.parentTranslationVector.getX()) / scaleX + x / parentScaleX;
        }

        @Override
        public double translateY(double y)
        {
            double scaleY = this.otherScaleVector.getY();
            double parentScaleY = this.parentScaleVector.getY();
            return (this.otherTranslationVector.getY() - this.parentTranslationVector.getY()) / scaleY + y / parentScaleY;
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