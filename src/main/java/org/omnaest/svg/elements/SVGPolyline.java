package org.omnaest.svg.elements;

import java.util.ArrayList;
import java.util.List;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.elements.base.SVGVector;

public class SVGPolyline implements SVGElement
{
	private List<SVGVector>	locations	= new ArrayList<>();
	private double			opacity		= 1.0;

	public SVGPolyline(List<SVGVector> locations)
	{
		super();
		this.locations.addAll(locations);
	}

	public SVGPolyline setOpacity(double opacity)
	{
		this.opacity = opacity;
		return this;
	}

	@Override
	public String render()
	{
		StringBuilder sb = new StringBuilder();
		for (SVGVector vector : this.locations)
		{
			int x = (int) Math.round(vector.getX());
			int y = (int) Math.round(vector.getY());
			sb.append(" " + x + "," + y);
		}

		return "\n<polyline points=\"" + sb.toString() + "\" stroke=\"red\" fill=\"none\" opacity=\"" + this.opacity + "\" />";
	}

}
