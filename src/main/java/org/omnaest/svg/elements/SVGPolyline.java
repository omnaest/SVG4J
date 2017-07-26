package org.omnaest.svg.elements;

import java.util.ArrayList;
import java.util.List;

import org.omnaest.svg.elements.base.SVGElement;
import org.omnaest.svg.elements.base.SVGVector;
import org.omnaest.svg.model.RawSVGPolyline;

public class SVGPolyline implements SVGElement
{
	private List<SVGVector>	locations	= new ArrayList<>();
	private double			opacity		= 1.0;
	private String			strokeColor	= "red";
	private String			fillColor	= "none";

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

	public String getStrokeColor()
	{
		return strokeColor;
	}

	public SVGPolyline setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
		return this;
	}

	@Override
	public RawSVGPolyline render()
	{
		StringBuilder sb = new StringBuilder();
		for (SVGVector vector : this.locations)
		{
			int x = (int) Math.round(vector.getX());
			int y = (int) Math.round(vector.getY());
			sb.append(" " + x + "," + y);
		}

		return new RawSVGPolyline()	.setPoints(sb.toString())
									.setStroke(this.strokeColor)
									.setFill(this.fillColor)
									.setOpacity("" + this.opacity);

		//return "\n<polyline points=\"" + sb.toString() + "\" stroke=\"red\" fill=\"none\" opacity=\"" + this.opacity + "\" />";
	}

}
