package org.omnaest.svg.elements;

import java.util.ArrayList;
import java.util.List;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.elements.base.SVGVector;
import org.omnaest.svg.model.RawSVGPolygon;

public class SVGPolygon implements SVGElement
{
	private List<SVGVector>	locations	= new ArrayList<>();
	private String			strokeColor	= "red";

	public SVGPolygon(List<SVGVector> locations)
	{
		super();
		this.locations.addAll(locations);
	}

	public String getStrokeColor()
	{
		return strokeColor;
	}

	public SVGPolygon setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	@Override
	public RawSVGPolygon render()
	{
		StringBuilder sb = new StringBuilder();
		for (SVGVector vector : this.locations)
		{
			int x = (int) vector.getX();
			int y = (int) vector.getY();
			sb.append(" " + x + " " + y);
		}

		return new RawSVGPolygon()	.setPoints(sb.toString())
									.setStroke(this.strokeColor);
		//	return "\n<polygon points=\"" + sb.toString() + "\" stroke=\"red\" />";
	}

}
