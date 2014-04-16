package nl.han.ica.calendarview;

/**
 * Interface for data objects that can be loaded into a TableColumnWithRowSpan cell.
 * @author matthijs
 *
 */
public interface TableColumnWithRowSpanCellDataInterface {
	/**
	 * Set the row span for the current data object.
	 * @param rowSpan the new value for the row span
	 */
	public void setRowSpan(final int rowSpan);
	
	/**
	 * Get the row span for the current data object.
	 * @return the current value for the row span for the data object
	 */
	public int getRowSpan();	
}
