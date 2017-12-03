package org.omnaest.svg.other;

import org.omnaest.svg.SVGDrawer;
import org.omnaest.svg.SVGDrawer.ResolutionProvider;

public enum DisplayResolution implements ResolutionProvider
{
	_800x600(800, 600), _1440x900(1440, 900), _1280x800(1280, 800), _1280x1024(1280, 1024);

	private int	width;
	private int	height;

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