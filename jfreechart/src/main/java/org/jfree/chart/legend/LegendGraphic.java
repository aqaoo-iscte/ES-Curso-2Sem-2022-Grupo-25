/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ------------------
 * LegendGraphic.java
 * ------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 */

package org.jfree.chart.legend;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

import org.jfree.chart.block.AbstractBlock;
import org.jfree.chart.block.Block;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.util.GradientPaintTransformer;
import org.jfree.chart.api.RectangleAnchor;
import org.jfree.chart.block.Size2D;
import org.jfree.chart.util.StandardGradientPaintTransformer;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.chart.internal.ShapeUtils;

/**
 * The graphical item within a legend item.
 */
public class LegendGraphic extends AbstractBlock
                           implements Block, PublicCloneable {

    private LegendGraphicProduct legendGraphicProduct = new LegendGraphicProduct();

	/** For serialization. */
    static final long serialVersionUID = -1338791523854985009L;

    /**
     * A flag that controls whether or not the shape is visible - see also
     * lineVisible.
     */
    private boolean shapeVisible;

    /**
     * The shape to display.  To allow for accurate positioning, the center
     * of the shape should be at (0, 0).
     */
    private transient Shape shape;

    /** A flag that controls whether or not the shape is filled. */
    private boolean shapeFilled;

    /** The fill paint for the shape. */
    private transient Paint fillPaint;

    /** A flag that controls whether or not the shape outline is visible. */
    private boolean shapeOutlineVisible;

    /** The outline paint for the shape. */
    private transient Paint outlinePaint;

    /** The outline stroke for the shape. */
    private transient Stroke outlineStroke;

    /**
     * A flag that controls whether or not the line is visible - see also
     * shapeVisible.
     */
    private boolean lineVisible;

    /** The line. */
    private transient Shape line;

    /** The line stroke. */
    private transient Stroke lineStroke;

    /** The line paint. */
    private transient Paint linePaint;

    /**
     * Creates a new legend graphic.
     *
     * @param shape  the shape ({@code null} not permitted).
     * @param fillPaint  the fill paint ({@code null} not permitted).
     */
    public LegendGraphic(Shape shape, Paint fillPaint) {
        Args.nullNotPermitted(shape, "shape");
        Args.nullNotPermitted(fillPaint, "fillPaint");
        this.shapeVisible = true;
        this.shape = shape;
        legendGraphicProduct.setShapeAnchor2(RectangleAnchor.CENTER);
        legendGraphicProduct.setShapeLocation2(RectangleAnchor.CENTER);
        this.shapeFilled = true;
        this.fillPaint = fillPaint;
        legendGraphicProduct.setFillPaintTransformer2(new StandardGradientPaintTransformer());
        setPadding(2.0, 2.0, 2.0, 2.0);
    }

    /**
     * Returns a flag that controls whether or not the shape
     * is visible.
     *
     * @return A boolean.
     *
     * @see #setShapeVisible(boolean)
     */
    public boolean isShapeVisible() {
        return this.shapeVisible;
    }

    /**
     * Sets a flag that controls whether or not the shape is
     * visible.
     *
     * @param visible  the flag.
     *
     * @see #isShapeVisible()
     */
    public void setShapeVisible(boolean visible) {
        this.shapeVisible = visible;
    }

    /**
     * Returns the shape.
     *
     * @return The shape.
     *
     * @see #setShape(Shape)
     */
    public Shape getShape() {
        return this.shape;
    }

    /**
     * Sets the shape.
     *
     * @param shape  the shape.
     *
     * @see #getShape()
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Returns a flag that controls whether or not the shapes
     * are filled.
     *
     * @return A boolean.
     *
     * @see #setShapeFilled(boolean)
     */
    public boolean isShapeFilled() {
        return this.shapeFilled;
    }

    /**
     * Sets a flag that controls whether or not the shape is
     * filled.
     *
     * @param filled  the flag.
     *
     * @see #isShapeFilled()
     */
    public void setShapeFilled(boolean filled) {
        this.shapeFilled = filled;
    }

    /**
     * Returns the paint used to fill the shape.
     *
     * @return The fill paint.
     *
     * @see #setFillPaint(Paint)
     */
    public Paint getFillPaint() {
        return this.fillPaint;
    }

    /**
     * Sets the paint used to fill the shape.
     *
     * @param paint  the paint.
     *
     * @see #getFillPaint()
     */
    public void setFillPaint(Paint paint) {
        this.fillPaint = paint;
    }

    /**
     * Returns the transformer used when the fill paint is an instance of
     * {@code GradientPaint}.
     *
     * @return The transformer (never {@code null}).
     *
     * @see #setFillPaintTransformer(GradientPaintTransformer)
     */
    public GradientPaintTransformer getFillPaintTransformer() {
        return this.legendGraphicProduct.getFillPaintTransformer();
    }

    /**
     * Sets the transformer used when the fill paint is an instance of
     * {@code GradientPaint}.
     *
     * @param transformer  the transformer ({@code null} not permitted).
     *
     * @see #getFillPaintTransformer()
     */
    public void setFillPaintTransformer(GradientPaintTransformer transformer) {
        legendGraphicProduct.setFillPaintTransformer(transformer);
    }

    /**
     * Returns a flag that controls whether the shape outline is visible.
     *
     * @return A boolean.
     *
     * @see #setShapeOutlineVisible(boolean)
     */
    public boolean isShapeOutlineVisible() {
        return this.shapeOutlineVisible;
    }

    /**
     * Sets a flag that controls whether or not the shape outline
     * is visible.
     *
     * @param visible  the flag.
     *
     * @see #isShapeOutlineVisible()
     */
    public void setShapeOutlineVisible(boolean visible) {
        this.shapeOutlineVisible = visible;
    }

    /**
     * Returns the outline paint.
     *
     * @return The paint.
     *
     * @see #setOutlinePaint(Paint)
     */
    public Paint getOutlinePaint() {
        return this.outlinePaint;
    }

    /**
     * Sets the outline paint.
     *
     * @param paint  the paint.
     *
     * @see #getOutlinePaint()
     */
    public void setOutlinePaint(Paint paint) {
        this.outlinePaint = paint;
    }

    /**
     * Returns the outline stroke.
     *
     * @return The stroke.
     *
     * @see #setOutlineStroke(Stroke)
     */
    public Stroke getOutlineStroke() {
        return this.outlineStroke;
    }

    /**
     * Sets the outline stroke.
     *
     * @param stroke  the stroke.
     *
     * @see #getOutlineStroke()
     */
    public void setOutlineStroke(Stroke stroke) {
        this.outlineStroke = stroke;
    }

    /**
     * Returns the shape anchor.
     *
     * @return The shape anchor.
     *
     * @see #getShapeAnchor()
     */
    public RectangleAnchor getShapeAnchor() {
        return this.legendGraphicProduct.getShapeAnchor();
    }

    /**
     * Sets the shape anchor.  This defines a point on the shapes bounding
     * rectangle that will be used to align the shape to a location.
     *
     * @param anchor  the anchor ({@code null} not permitted).
     *
     * @see #setShapeAnchor(RectangleAnchor)
     */
    public void setShapeAnchor(RectangleAnchor anchor) {
        legendGraphicProduct.setShapeAnchor(anchor);
    }

    /**
     * Returns the shape location.
     *
     * @return The shape location.
     *
     * @see #setShapeLocation(RectangleAnchor)
     */
    public RectangleAnchor getShapeLocation() {
        return this.legendGraphicProduct.getShapeLocation();
    }

    /**
     * Sets the shape location.  This defines a point within the drawing
     * area that will be used to align the shape to.
     *
     * @param location  the location ({@code null} not permitted).
     *
     * @see #getShapeLocation()
     */
    public void setShapeLocation(RectangleAnchor location) {
        legendGraphicProduct.setShapeLocation(location);
    }

    /**
     * Returns the flag that controls whether or not the line is visible.
     *
     * @return A boolean.
     *
     * @see #setLineVisible(boolean)
     */
    public boolean isLineVisible() {
        return this.lineVisible;
    }

    /**
     * Sets the flag that controls whether or not the line is visible.
     *
     * @param visible  the flag.
     *
     * @see #isLineVisible()
     */
    public void setLineVisible(boolean visible) {
        this.lineVisible = visible;
    }

    /**
     * Returns the line centered about (0, 0).
     *
     * @return The line.
     *
     * @see #setLine(Shape)
     */
    public Shape getLine() {
        return this.line;
    }

    /**
     * Sets the line.  A Shape is used here, because then you can use Line2D,
     * GeneralPath or any other Shape to represent the line.
     *
     * @param line  the line.
     *
     * @see #getLine()
     */
    public void setLine(Shape line) {
        this.line = line;
    }

    /**
     * Returns the line paint.
     *
     * @return The paint.
     *
     * @see #setLinePaint(Paint)
     */
    public Paint getLinePaint() {
        return this.linePaint;
    }

    /**
     * Sets the line paint.
     *
     * @param paint  the paint.
     *
     * @see #getLinePaint()
     */
    public void setLinePaint(Paint paint) {
        this.linePaint = paint;
    }

    /**
     * Returns the line stroke.
     *
     * @return The stroke.
     *
     * @see #setLineStroke(Stroke)
     */
    public Stroke getLineStroke() {
        return this.lineStroke;
    }

    /**
     * Sets the line stroke.
     *
     * @param stroke  the stroke.
     *
     * @see #getLineStroke()
     */
    public void setLineStroke(Stroke stroke) {
        this.lineStroke = stroke;
    }

    /**
     * Arranges the contents of the block, within the given constraints, and
     * returns the block size.
     *
     * @param g2  the graphics device.
     * @param constraint  the constraint ({@code null} not permitted).
     *
     * @return The block size (in Java2D units, never {@code null}).
     */
    @Override
    public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
        RectangleConstraint contentConstraint = toContentConstraint(constraint);
        LengthConstraintType w = contentConstraint.getWidthConstraintType();
        LengthConstraintType h = contentConstraint.getHeightConstraintType();
        Size2D contentSize = null;
        switch (w) {
            case NONE:
                if (h == LengthConstraintType.NONE) {
                    contentSize = arrangeNN(g2);
                }
                else if (h == LengthConstraintType.RANGE) {
                    throw new RuntimeException("Not yet implemented.");
                }
                else if (h == LengthConstraintType.FIXED) {
                    throw new RuntimeException("Not yet implemented.");
                }   break;
            case RANGE:
                if (h == LengthConstraintType.NONE) {
                    throw new RuntimeException("Not yet implemented.");
                }
                else if (h == LengthConstraintType.RANGE) {
                    throw new RuntimeException("Not yet implemented.");
                }
                else if (h == LengthConstraintType.FIXED) {
                    throw new RuntimeException("Not yet implemented.");
                }   break;
            case FIXED:
                if (h == LengthConstraintType.NONE) {
                    throw new RuntimeException("Not yet implemented.");
                }
                else if (h == LengthConstraintType.RANGE) {
                    throw new RuntimeException("Not yet implemented.");
                }
                else if (h == LengthConstraintType.FIXED) {
                    contentSize = new Size2D(contentConstraint.getWidth(),
                            contentConstraint.getHeight());
                }   break;
            default:
                throw new IllegalStateException("Unrecognised widthConstraintType.");
        }
        assert contentSize != null;
        return new Size2D(calculateTotalWidth(contentSize.getWidth()),
                calculateTotalHeight(contentSize.getHeight()));
    }

    /**
     * Performs the layout with no constraint, so the content size is
     * determined by the bounds of the shape and/or line drawn to represent
     * the series.
     *
     * @param g2  the graphics device.
     *
     * @return  The content size.
     */
    protected Size2D arrangeNN(Graphics2D g2) {
        Rectangle2D contentSize = new Rectangle2D.Double();
        if (this.line != null) {
            contentSize.setRect(this.line.getBounds2D());
        }
        if (this.shape != null) {
            contentSize = contentSize.createUnion(this.shape.getBounds2D());
        }
        return new Size2D(contentSize.getWidth(), contentSize.getHeight());
    }

    /**
     * Draws the graphic item within the specified area.
     *
     * @param g2  the graphics device.
     * @param area  the area.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area) {

        area = trimMargin(area);
        drawBorder(g2, area);
        area = trimBorder(area);
        area = trimPadding(area);

        if (this.lineVisible) {
            Point2D location = this.legendGraphicProduct.getShapeLocation().getAnchorPoint(area);
            Shape aLine = ShapeUtils.createTranslatedShape(getLine(),
                    this.legendGraphicProduct.getShapeAnchor(), location.getX(), location.getY());
            g2.setPaint(this.linePaint);
            g2.setStroke(this.lineStroke);
            g2.draw(aLine);
        }

        if (this.shapeVisible) {
            Point2D location = this.legendGraphicProduct.getShapeLocation().getAnchorPoint(area);

            Shape s = ShapeUtils.createTranslatedShape(this.shape,
                    this.legendGraphicProduct.getShapeAnchor(), location.getX(), location.getY());
            if (this.shapeFilled) {
                Paint p = p(s);
				        g2.setPaint(p);

                g2.fill(s);
            }
            if (this.shapeOutlineVisible) {
                g2.setPaint(this.outlinePaint);
                g2.setStroke(this.outlineStroke);
                g2.draw(s);
            }
        }
    }

	private Paint p(Shape s) {
		Paint p = this.fillPaint;
		if (p instanceof GradientPaint) {
			GradientPaint gp = (GradientPaint) this.fillPaint;
			p = this.legendGraphicProduct.getFillPaintTransformer().transform(gp, s);
		}
		return p;
	}

    /**
     * Draws the block within the specified area.
     *
     * @param g2  the graphics device.
     * @param area  the area.
     * @param params  ignored ({@code null} permitted).
     *
     * @return Always {@code null}.
     */
    @Override
    public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
        draw(g2, area);
        return null;
    }

    /**
     * Tests this {@code LegendGraphic} instance for equality with an
     * arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LegendGraphic)) {
            return false;
        }
        LegendGraphic that = (LegendGraphic) obj;
        if (this.shapeVisible != that.shapeVisible) {
            return false;
        }
        if (!ShapeUtils.equal(this.shape, that.shape)) {
            return false;
        }
        if (this.shapeFilled != that.shapeFilled) {
            return false;
        }
        if (!PaintUtils.equal(this.fillPaint, that.fillPaint)) {
            return false;
        }
        if (!Objects.equals(this.legendGraphicProduct.getFillPaintTransformer(), that.legendGraphicProduct.getFillPaintTransformer())) {
            return false;
        }
        if (this.shapeOutlineVisible != that.shapeOutlineVisible) {
            return false;
        }
        if (!PaintUtils.equal(this.outlinePaint, that.outlinePaint)) {
            return false;
        }
        if (!Objects.equals(this.outlineStroke, that.outlineStroke)) {
            return false;
        }
        if (this.legendGraphicProduct.getShapeAnchor() != that.legendGraphicProduct.getShapeAnchor()) {
            return false;
        }
        if (this.legendGraphicProduct.getShapeLocation() != that.legendGraphicProduct.getShapeLocation()) {
            return false;
        }
        if (this.lineVisible != that.lineVisible) {
            return false;
        }
        if (!ShapeUtils.equal(this.line, that.line)) {
            return false;
        }
        if (!PaintUtils.equal(this.linePaint, that.linePaint)) {
            return false;
        }
        if (!Objects.equals(this.lineStroke, that.lineStroke)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.shapeVisible ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.shape);
        hash = 89 * hash + Objects.hashCode(this.legendGraphicProduct.getShapeLocation());
        hash = 89 * hash + Objects.hashCode(this.legendGraphicProduct.getShapeAnchor());
        hash = 89 * hash + (this.shapeFilled ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.fillPaint);
        hash = 89 * hash + Objects.hashCode(this.legendGraphicProduct.getFillPaintTransformer());
        hash = 89 * hash + (this.shapeOutlineVisible ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.outlinePaint);
        hash = 89 * hash + Objects.hashCode(this.outlineStroke);
        hash = 89 * hash + (this.lineVisible ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.line);
        hash = 89 * hash + Objects.hashCode(this.lineStroke);
        hash = 89 * hash + Objects.hashCode(this.linePaint);
        return hash;
    }

    /**
     * Returns a clone of this {@code LegendGraphic} instance.
     *
     * @return A clone of this {@code LegendGraphic} instance.
     *
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        LegendGraphic clone = (LegendGraphic) super.clone();
		clone.legendGraphicProduct = (LegendGraphicProduct) this.legendGraphicProduct.clone();
        clone.shape = CloneUtils.clone(this.shape);
        clone.line = CloneUtils.clone(this.line);
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeShape(this.shape, stream);
        SerialUtils.writePaint(this.fillPaint, stream);
        SerialUtils.writePaint(this.outlinePaint, stream);
        SerialUtils.writeStroke(this.outlineStroke, stream);
        SerialUtils.writeShape(this.line, stream);
        SerialUtils.writePaint(this.linePaint, stream);
        SerialUtils.writeStroke(this.lineStroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.shape = SerialUtils.readShape(stream);
        this.fillPaint = SerialUtils.readPaint(stream);
        this.outlinePaint = SerialUtils.readPaint(stream);
        this.outlineStroke = SerialUtils.readStroke(stream);
        this.line = SerialUtils.readShape(stream);
        this.linePaint = SerialUtils.readPaint(stream);
        this.lineStroke = SerialUtils.readStroke(stream);
    }

}
