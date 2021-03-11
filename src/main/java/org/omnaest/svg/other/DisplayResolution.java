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
package org.omnaest.svg.other;

import org.omnaest.svg.SVGDrawer.ResolutionProvider;

public enum DisplayResolution implements ResolutionProvider
{
    _800x600(800, 600), _1440x900(1440, 900), _1280x800(1280, 800), _1280x1024(1280, 1024), _800x1280(800, 1200);

    private int width;
    private int height;

    private DisplayResolution(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth()
    {
        return this.width;
    }

    @Override
    public int getHeight()
    {
        return this.height;
    }

}
