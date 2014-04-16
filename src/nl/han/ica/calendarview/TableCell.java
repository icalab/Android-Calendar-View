package nl.han.ica.calendarview;

import java.util.UUID;


/**
 * Example table cell class.
 * @author matthijs
 *
 */
public class TableCell implements TableColumnWithRowSpanCellDataInterface {
	protected String content = UUID.randomUUID().toString();
	
	private int rowSpan = 1;
	
	public void setRowSpan(final int rowSpan) {
		this.rowSpan = rowSpan;
	}
	
	public int getRowSpan() {
		return this.rowSpan;
	}
}
