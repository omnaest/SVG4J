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
package org.omnaest.svg;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.svg.SVGDrawer.BoundedArea;
import org.omnaest.svg.elements.SVGAnker;
import org.omnaest.svg.elements.SVGCircle;
import org.omnaest.svg.elements.SVGLine;
import org.omnaest.svg.elements.SVGRectangle;
import org.omnaest.svg.elements.SVGText;
import org.omnaest.svg.other.DisplayResolution;

public class SVGUtilsTest
{

	@Test
	@Ignore
	public void testParseFile() throws Exception
	{
		File sourceFile = new File("C:\\Z\\anatomy images\\Female_shadow_anatomy_without_labels.svg");
		String svg = SVGUtils	.getDrawer(sourceFile)
								.modifyRawElements((elements) -> elements	.skip(0)
																			.limit(1))
								.addRawElements(SVGUtils.getDrawer(sourceFile)
														.modifyRawElements((elements) -> elements	.skip(1)
																									.limit(10)))
								.add(new SVGLine(640, 0, 640, 2000).setStrokeWidth(4))
								.render();

		System.out.println(svg);
		FileUtils.writeStringToFile(new File("C:/Temp/parse.svg"), svg, "utf-8");
	}

	@Test
	@Ignore
	public void testAnker() throws IOException
	{
		SVGUtils.getDrawer(100, 100)
				.withScreenDimensions(200, 200)
				.enableCSSForAnkerLinks()
				.add(new SVGAnker("http://www.google.de")	.addElement(new SVGRectangle(50, 50, 20, 20).setFillColor("blue"))
															.addElement(new SVGText(10, 10, "Google")))
				.renderAsResult()
				.writeToFile(new File("C:/Temp/svgAnkerTest.svg"));

	}

	@Test
	@Ignore
	public void testBoundedContext() throws IOException
	{
		SVGDrawer drawer = SVGUtils	.getDrawer(1000, 500)
									.withScreenDimensions(DisplayResolution._1280x800);
		{
			BoundedArea boundedArea = drawer.newBoundedArea()
											.withRelativeTranslationX(0.5)
											.withRelativeTranslationY(0.5)
											.withRelativeHeight(0.25)
											.withRelativeWidth(0.25)
											.withScalingHeight(100)
											.withScalingWidth(100);
			assertEquals(250, boundedArea.getRawWidth(), 0.001);
			assertEquals(125, boundedArea.getRawHeight(), 0.001);
			assertEquals(100, boundedArea.getWidth(), 0.001);
			assertEquals(100, boundedArea.getHeight(), 0.001);
		}
		{
			BoundedArea boundedArea = drawer.newBoundedArea();
			assertEquals(1000, boundedArea.getRawWidth(), 0.001);
			assertEquals(500, boundedArea.getRawHeight(), 0.001);
			assertEquals(1000, boundedArea.getWidth(), 0.001);
			assertEquals(500, boundedArea.getHeight(), 0.001);
		}
	}

	@Test
	@Ignore
	public void testBoundedContextWithRender() throws IOException
	{
		SVGDrawer drawer = SVGUtils	.getDrawer(1000, 500)
									.withScreenDimensions(DisplayResolution._1280x800);
		drawer	.newBoundedArea()
				.add(new SVGRectangle(0, 0, 1000, 500).setStrokeColor("red"));
		BoundedArea boundedArea = drawer.newBoundedArea()
										.withRelativeTranslationX(0.5)
										.withRelativeTranslationY(0.5)
										.withRelativeHeight(0.25)
										.withRelativeWidth(0.25)
										.withScalingHeight(100)
										.withScalingWidth(100)
										.add(new SVGCircle(0, 0, 100))
										.add(new SVGRectangle(0, 0, 100, 100));
		assertEquals(250, boundedArea.getRawWidth(), 0.001);
		assertEquals(125, boundedArea.getRawHeight(), 0.001);
		boundedArea	.newSubArea()
					.withRelativeHeight(0.5)
					.withRelativeWidth(0.5)
					.withScalingHeight(100)
					.withScalingWidth(100)
					.add(new SVGRectangle(0, 0, 100, 100)	.setStrokeColor("yellow")
															.setFillColor("lightgray"))
					.withRelativeSizedPadding(0.05)
					.add(new SVGRectangle(0, 0, 100, 100)	.setStrokeColor("yellow")
															.setFillColor("white"));
		drawer	.renderAsResult()
				.writeToFile(new File("C:/Temp/svgBoundedAreaTest.svg"));

	}

	@Test
	public void testBoundedContextWithCoverageMerge() throws IOException
	{
		SVGDrawer drawer = SVGUtils	.getDrawer(900, 500)
									.withScreenDimensions(DisplayResolution._1280x800);
		BoundedArea boundedArea = drawer.newBoundedArea();

		List<BoundedArea> verticalSlices = boundedArea.asVerticalSlices(3);

		BoundedArea coverageArea = verticalSlices	.get(2)
													.coverageMergeWith(verticalSlices.get(1));

		assertEquals(-300.0, coverageArea.getRawTranslationX(), 0.01);
		assertEquals(0.0, coverageArea.getRawTranslationY(), 0.01);
		assertEquals(600, coverageArea.getRawWidth(), 0.01);
		assertEquals(500, coverageArea.getRawHeight(), 0.01);
	}

	@Test
	public void testBoundedContextWithSlices() throws IOException
	{
		SVGDrawer drawer = SVGUtils	.getDrawer(900, 600)
									.withScreenDimensions(DisplayResolution._1280x800);
		BoundedArea boundedArea = drawer.newBoundedArea();

		{
			//
			List<BoundedArea> verticalSlices = boundedArea.asVerticalSlices(3);

			//
			assertEquals(	0.0, verticalSlices.get(0)
											.getRawTranslationX(),
							0.01);
			assertEquals(	300.0, verticalSlices.get(1)
												.getRawTranslationX(),
							0.01);
			assertEquals(	600.0, verticalSlices.get(2)
												.getRawTranslationX(),
							0.01);

			//
			assertEquals(	0.0, verticalSlices.get(0)
											.getRawTranslationY(),
							0.01);

			//
			assertEquals(	0.0, verticalSlices.get(0)
											.getCoordinatesTranslator()
											.translateX(0),
							0.01);
			assertEquals(	300.0, verticalSlices.get(1)
												.getCoordinatesTranslator()
												.translateX(0),
							0.01);
			assertEquals(	600.0, verticalSlices.get(2)
												.getCoordinatesTranslator()
												.translateX(0),
							0.01);

			//
			assertEquals(	0.0, verticalSlices.get(2)
											.getCoordinatesTranslator()
											.translateY(0),
							0.01);

		}
		{
			List<BoundedArea> horizontalSlices = boundedArea.asHorizontalSlices(3);

			assertEquals(	0.0, horizontalSlices.get(0)
												.getRawTranslationY(),
							0.01);
			assertEquals(	200.0, horizontalSlices.get(1)
												.getRawTranslationY(),
							0.01);
			assertEquals(	400.0, horizontalSlices.get(2)
												.getRawTranslationY(),
							0.01);
			assertEquals(	0.0, horizontalSlices.get(0)
												.getRawTranslationX(),
							0.01);

			//
			assertEquals(	0.0, horizontalSlices.get(0)
												.getCoordinatesTranslator()
												.translateY(0),
							0.01);
			assertEquals(	200.0, horizontalSlices.get(1)
												.getCoordinatesTranslator()
												.translateY(0),
							0.01);
			assertEquals(	400.0, horizontalSlices.get(2)
												.getCoordinatesTranslator()
												.translateY(0),
							0.01);
		}

	}

	@Test
	public void testBoundedContextWithCoverageMergeDraw() throws IOException
	{
		SVGDrawer drawer = SVGUtils.getDrawer(1000, 700);
		BoundedArea boundedArea = drawer.newBoundedArea()
										.withPadding(10);

		boundedArea	.newSubArea()
					.withScalingHeight(100)
					.withScalingWidth(100)
					.add(new SVGRectangle(0, 0, 100, 100).setFillOpacity(0.1));

		List<BoundedArea> verticalSlices = boundedArea.asVerticalSlices(3);

		BoundedArea lowerRight = verticalSlices	.get(2)
												.asHorizontalSlices(2)
												.get(1);
		BoundedArea lowerMiddle = verticalSlices.get(1)
												.asHorizontalSlices(2)
												.get(1);
		BoundedArea coverageArea = lowerRight.coverageMergeWith(lowerMiddle);

		System.out.println(coverageArea);
		coverageArea.withPadding(10)
					.withScalingWidth(100)
					.withScalingHeight(100)
					.add(new SVGRectangle(0, 0, 100, 100).setFillOpacity(0.4))
					.add(new SVGText(50, 50, "2+3").setFontSize(100));

		lowerMiddle	.newSubArea()
					.withPadding(10)
					.withScalingWidth(100)
					.withScalingHeight(100)
					.add(new SVGRectangle(0, 0, 100, 100).setFillOpacity(0.4))
					.add(new SVGText(50, 50, "2").setFontSize(100));

		lowerRight	.newSubArea()
					.withPadding(10)
					.withScalingWidth(100)
					.withScalingHeight(100)
					.add(new SVGRectangle(0, 0, 100, 100).setFillOpacity(0.4))
					.add(new SVGText(50, 50, "3").setFontSize(100));

		drawer	.renderAsResult()
				.writeToFile(new File("C:/Temp/svgCoverageMergeTest.svg"));
	}
}
