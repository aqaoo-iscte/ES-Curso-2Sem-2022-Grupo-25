package org.jfree.chart.legend;


import java.awt.Paint;
import java.awt.Stroke;
import org.jfree.chart.internal.Args;
import org.jfree.chart.event.TitleChangeEvent;
import java.io.Serializable;
import org.jfree.chart.api.PublicCloneable;

public class PaintScaleLegendProduct2 implements Serializable, PublicCloneable {
	private transient Paint stripOutlinePaint;
	private transient Stroke stripOutlineStroke;
	private transient Paint backgroundPaint;

	public Paint getStripOutlinePaint() {
		return stripOutlinePaint;
	}

	public void setStripOutlinePaint2(Paint stripOutlinePaint) {
		this.stripOutlinePaint = stripOutlinePaint;
	}

	public Stroke getStripOutlineStroke() {
		return stripOutlineStroke;
	}

	public void setStripOutlineStroke2(Stroke stripOutlineStroke) {
		this.stripOutlineStroke = stripOutlineStroke;
	}

	public Paint getBackgroundPaint() {
		return backgroundPaint;
	}

	public void setBackgroundPaint2(Paint backgroundPaint) {
		this.backgroundPaint = backgroundPaint;
	}

	/**
	* Sets the paint used to draw the outline of the paint strip, and sends a  {@link TitleChangeEvent}  to all registered listeners.
	* @param paint   the paint ( {@code  null}  not permitted).
	* @see #getStripOutlinePaint()
	*/
	public void setStripOutlinePaint(Paint paint, PaintScaleLegend paintScaleLegend) {
		Args.nullNotPermitted(paint, "paint");
		this.stripOutlinePaint = paint;
		paintScaleLegend.notifyListeners(new TitleChangeEvent(paintScaleLegend));
	}

	/**
	* Sets the stroke used to draw the outline around the paint strip and sends a  {@link TitleChangeEvent}  to all registered listeners.
	* @param stroke   the stroke ( {@code  null}  not permitted).
	* @see #getStripOutlineStroke()
	*/
	public void setStripOutlineStroke(Stroke stroke, PaintScaleLegend paintScaleLegend) {
		Args.nullNotPermitted(stroke, "stroke");
		this.stripOutlineStroke = stroke;
		paintScaleLegend.notifyListeners(new TitleChangeEvent(paintScaleLegend));
	}

	/**
	* Sets the background paint and sends a  {@link TitleChangeEvent}  to all registered listeners.
	* @param paint   the paint ( {@code  null}  permitted).
	*/
	public void setBackgroundPaint(Paint paint, PaintScaleLegend paintScaleLegend) {
		this.backgroundPaint = paint;
		paintScaleLegend.notifyListeners(new TitleChangeEvent(paintScaleLegend));
	}

	public Object clone() throws CloneNotSupportedException {
		return (PaintScaleLegendProduct2) super.clone();
	}
}