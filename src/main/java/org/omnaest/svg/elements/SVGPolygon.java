package org.omnaest.svg.elements;

import java.util.ArrayList;
import java.util.List;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.elements.base.SVGVector;

public class SVGPolygon implements SVGElement
{
	private List<SVGVector> locations = new ArrayList<>();

	public SVGPolygon(List<SVGVector> locations)
	{
		super();
		this.locations.addAll(locations);
	}

	@Override
	public String render()
	{
		StringBuilder sb = new StringBuilder();
		for (SVGVector vector : this.locations)
		{
			int x = (int) vector.getX();
			int y = (int) vector.getY();
			sb.append(" " + x + " " + y);
		}

		return "\n<polygon points=\"" + sb.toString() + "\" stroke=\"red\" />";
	}

}
