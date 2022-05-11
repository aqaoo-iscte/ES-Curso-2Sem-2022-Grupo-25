package org.jfree.chart.legend;


import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.internal.Args;
import org.jfree.chart.event.TitleChangeEvent;
import java.io.Serializable;
import org.jfree.chart.api.PublicCloneable;

public class PaintScaleLegendProduct implements Serializable, PublicCloneable {
	private PaintScale scale;
	private AxisLocation axisLocation;
	private int subdivisions;

	public PaintScale getScale() {
		return scale;
	}

	public void setScale2(PaintScale scale) {
		this.scale = scale;
	}

	public AxisLocation getAxisLocation() {
		return axisLocation;
	}

	public void setAxisLocation2(AxisLocation axisLocation) {
		this.axisLocation = axisLocation;
	}

	public int getSubdivisions() {
		return subdivisions;
	}

	public void setSubdivisions(int subdivisions) {
		this.subdivisions = subdivisions;
	}

	/**
	* Sets the scale and sends a  {@link TitleChangeEvent}  to all registered listeners.
	* @param scale   the scale ( {@code  null}  not permitted).
	* @see #getScale()
	*/
	public void setScale(PaintScale scale, PaintScaleLegend paintScaleLegend) {
		Args.nullNotPermitted(scale, "scale");
		this.scale = scale;
		paintScaleLegend.notifyListeners(new TitleChangeEvent(paintScaleLegend));
	}

	/**
	* Sets the axis location and sends a  {@link TitleChangeEvent}  to all registered listeners.
	* @param location   the location ( {@code  null}  not permitted).
	* @see #getAxisLocation()
	*/
	public void setAxisLocation(AxisLocation location, PaintScaleLegend paintScaleLegend) {
		Args.nullNotPermitted(location, "location");
		this.axisLocation = location;
		paintScaleLegend.notifyListeners(new TitleChangeEvent(paintScaleLegend));
	}

	/**
	* Sets the subdivision count and sends a  {@link TitleChangeEvent}  to all registered listeners.
	* @param count   the count.
	*/
	public void setSubdivisionCount(int count, PaintScaleLegend paintScaleLegend) {
		if (count <= 0) {
			throw new IllegalArgumentException("Requires 'count' > 0.");
		}
		this.subdivisions = count;
		paintScaleLegend.notifyListeners(new TitleChangeEvent(paintScaleLegend));
	}

	public Object clone() throws CloneNotSupportedException {
		return (PaintScaleLegendProduct) super.clone();
	}
}