package org.jfree.chart.legend;


import org.jfree.chart.api.RectangleAnchor;
import org.jfree.chart.util.GradientPaintTransformer;
import org.jfree.chart.internal.Args;
import java.io.Serializable;
import org.jfree.chart.api.PublicCloneable;

public class LegendGraphicProduct implements Serializable, PublicCloneable {
	private RectangleAnchor shapeLocation;
	private RectangleAnchor shapeAnchor;
	private GradientPaintTransformer fillPaintTransformer;

	public RectangleAnchor getShapeLocation() {
		return shapeLocation;
	}

	public void setShapeLocation2(RectangleAnchor shapeLocation) {
		this.shapeLocation = shapeLocation;
	}

	public RectangleAnchor getShapeAnchor() {
		return shapeAnchor;
	}

	public void setShapeAnchor2(RectangleAnchor shapeAnchor) {
		this.shapeAnchor = shapeAnchor;
	}

	public GradientPaintTransformer getFillPaintTransformer() {
		return fillPaintTransformer;
	}

	public void setFillPaintTransformer2(GradientPaintTransformer fillPaintTransformer) {
		this.fillPaintTransformer = fillPaintTransformer;
	}

	/**
	* Sets the transformer used when the fill paint is an instance of {@code  GradientPaint} .
	* @param transformer   the transformer ( {@code  null}  not permitted).
	* @see #getFillPaintTransformer()
	*/
	public void setFillPaintTransformer(GradientPaintTransformer transformer) {
		Args.nullNotPermitted(transformer, "transformer");
		this.fillPaintTransformer = transformer;
	}

	/**
	* Sets the shape anchor.  This defines a point on the shapes bounding rectangle that will be used to align the shape to a location.
	* @param anchor   the anchor ( {@code  null}  not permitted).
	* @see #setShapeAnchor(RectangleAnchor)
	*/
	public void setShapeAnchor(RectangleAnchor anchor) {
		Args.nullNotPermitted(anchor, "anchor");
		this.shapeAnchor = anchor;
	}

	/**
	* Sets the shape location.  This defines a point within the drawing area that will be used to align the shape to.
	* @param location   the location ( {@code  null}  not permitted).
	* @see #getShapeLocation()
	*/
	public void setShapeLocation(RectangleAnchor location) {
		Args.nullNotPermitted(location, "location");
		this.shapeLocation = location;
	}

	public Object clone() throws CloneNotSupportedException {
		return (LegendGraphicProduct) super.clone();
	}
}