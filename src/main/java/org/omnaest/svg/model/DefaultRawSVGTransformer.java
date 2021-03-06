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
package org.omnaest.svg.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.omnaest.utils.ObjectUtils;

public class DefaultRawSVGTransformer implements RawSVGTransformer
{
    public static interface SupplierConsumer extends Supplier<Double>, Consumer<Double>
    {
    }

    public static interface SupplierBiConsumer extends Supplier<Double>, BiConsumer<Double, Double>
    {
    }

    private Collection<SupplierConsumer>   supplierConsumersLocationX = new ArrayList<>();
    private Collection<SupplierConsumer>   supplierConsumersLocationY = new ArrayList<>();
    private Collection<SupplierConsumer>   supplierConsumersWidth     = new ArrayList<>();
    private Collection<SupplierConsumer>   supplierConsumersHeight    = new ArrayList<>();
    private Collection<SupplierBiConsumer> supplierConsumersRadius    = new ArrayList<>();
    private RawSVGElement                  rawSVGElement;

    @Deprecated
    public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, SupplierConsumer supplierConsumersX, SupplierConsumer supplierConsumersY)
    {
        this(rawSVGElement, Arrays.asList(supplierConsumersX), Arrays.asList(supplierConsumersY), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Deprecated
    public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, SupplierConsumer supplierConsumersLocationX, SupplierConsumer supplierConsumersLocationY,
                                    SupplierConsumer supplierConsumersWidth, SupplierConsumer supplierConsumersHeight)
    {
        this(rawSVGElement, Arrays.asList(supplierConsumersLocationX), Arrays.asList(supplierConsumersLocationY), new ArrayList<>(),
                Arrays.asList(supplierConsumersWidth), Arrays.asList(supplierConsumersHeight));
    }

    @Deprecated
    public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, SupplierConsumer supplierConsumersX, SupplierConsumer supplierConsumersY,
                                    SupplierBiConsumer supplierConsumersXY)
    {
        this(rawSVGElement, Arrays.asList(supplierConsumersX), Arrays.asList(supplierConsumersY), Arrays.asList(supplierConsumersXY), new ArrayList<>(),
                new ArrayList<>());
    }

    @Deprecated
    public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, Collection<SupplierConsumer> supplierConsumersLocationX,
                                    Collection<SupplierConsumer> supplierConsumersLocationY, Collection<SupplierBiConsumer> supplierConsumersRadius,
                                    Collection<SupplierConsumer> supplierConsumersWidth, Collection<SupplierConsumer> supplierConsumersHeight)
    {
        super();
        this.rawSVGElement = rawSVGElement;
        this.supplierConsumersLocationX = supplierConsumersLocationX;
        this.supplierConsumersLocationY = supplierConsumersLocationY;
        this.supplierConsumersRadius = supplierConsumersRadius;
        this.supplierConsumersWidth = supplierConsumersWidth;
        this.supplierConsumersHeight = supplierConsumersHeight;
    }

    public DefaultRawSVGTransformer(RawSVGElement rawSVGElement)
    {
        super();
        this.rawSVGElement = rawSVGElement;
    }

    public DefaultRawSVGTransformer addLocationXSupplierConsumer(SupplierConsumer locationXSupplierConsumer)
    {
        this.supplierConsumersLocationX.add(locationXSupplierConsumer);
        return this;
    }

    public DefaultRawSVGTransformer addLocationYSupplierConsumer(SupplierConsumer locationYSupplierConsumer)
    {
        this.supplierConsumersLocationY.add(locationYSupplierConsumer);
        return this;
    }

    public DefaultRawSVGTransformer addRadiusSupplierConsumer(SupplierBiConsumer radiusSupplierConsumer)
    {
        this.supplierConsumersRadius.add(radiusSupplierConsumer);
        return this;
    }

    public DefaultRawSVGTransformer addHeightSupplierConsumer(SupplierConsumer heightSupplierConsumer)
    {
        this.supplierConsumersHeight.add(heightSupplierConsumer);
        return this;
    }

    public DefaultRawSVGTransformer addWidthSupplierConsumer(SupplierConsumer widthSupplierConsumer)
    {
        this.supplierConsumersWidth.add(widthSupplierConsumer);
        return this;
    }

    @Override
    public RawSVGElement translate(double x, double y)
    {
        this.supplierConsumersLocationX.forEach(cs -> cs.accept(cs.get() + x));
        this.supplierConsumersLocationY.forEach(cs -> cs.accept(cs.get() + y));
        return this.rawSVGElement;
    }

    @Override
    public RawSVGElement scale(double scaleX, double scaleY)
    {
        this.supplierConsumersLocationX.forEach(cs -> cs.accept(cs.get() * scaleX));
        this.supplierConsumersLocationY.forEach(cs -> cs.accept(cs.get() * scaleY));
        this.supplierConsumersWidth.forEach(cs -> ObjectUtils.ifNotNull(cs.get(), value -> cs.accept(cs.get() * scaleX)));
        this.supplierConsumersHeight.forEach(cs -> ObjectUtils.ifNotNull(cs.get(), value -> cs.accept(value * scaleY)));
        this.supplierConsumersRadius.forEach(cs -> ObjectUtils.ifNotNull(cs.get(), value -> cs.accept(value * scaleX, cs.get() * scaleY)));
        return this.rawSVGElement;
    }

    @Override
    public String toString()
    {
        return "DefaultRawSVGTransformer [supplierConsumersX=" + this.supplierConsumersLocationX + ", supplierConsumersY=" + this.supplierConsumersLocationY
                + ", rawSVGElement=" + this.rawSVGElement + "]";
    }

}
