package org.jfree.chart.axis;


import java.util.List;
import java.awt.Font;
import org.jfree.chart.internal.Args;
import org.jfree.chart.event.AxisChangeEvent;
import java.io.Serializable;

public class SubCategoryAxisProduct implements Serializable, Cloneable {
	private List subCategories;
	private Font subLabelFont = new Font("SansSerif", Font.PLAIN, 10);

	public List getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List subCategories) {
		this.subCategories = subCategories;
	}

	public Font getSubLabelFont() {
		return subLabelFont;
	}

	/**
	* Adds a sub-category to the axis and sends an  {@link AxisChangeEvent}  to all registered listeners.
	* @param subCategory   the sub-category ( {@code  null}  not permitted).
	*/
	public void addSubCategory(Comparable subCategory, SubCategoryAxis subCategoryAxis) {
		Args.nullNotPermitted(subCategory, "subCategory");
		this.subCategories.add(subCategory);
		subCategoryAxis.notifyListeners(new AxisChangeEvent(subCategoryAxis));
	}

	/**
	* Sets the font used to display the sub-category labels and sends an {@link AxisChangeEvent}  to all registered listeners.
	* @param font   the font ( {@code  null}  not permitted).
	* @see #getSubLabelFont()
	*/
	public void setSubLabelFont(Font font, SubCategoryAxis subCategoryAxis) {
		Args.nullNotPermitted(font, "font");
		this.subLabelFont = font;
		subCategoryAxis.notifyListeners(new AxisChangeEvent(subCategoryAxis));
	}

	public Object clone() throws CloneNotSupportedException {
		return (SubCategoryAxisProduct) super.clone();
	}
}