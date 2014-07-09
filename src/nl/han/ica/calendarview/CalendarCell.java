package nl.han.ica.calendarview;
import java.util.Date;

/**
 * Class for column cells in a calendar day view. Provides
 * a row span and an dataObject, if one exists. The
 * date object must implement CalendarEventInterface (because it
 * has a start and end time).
 * @author matthijs
 *
 */
public class CalendarCell implements TableColumnWithRowSpanCellDataInterface {
	
	private int rowSpan = 1;
	
	@Override
	public void setRowSpan(final int rowSpan) {
		this.rowSpan = rowSpan;
	}
	
	@Override
	public int getRowSpan() {
		return this.rowSpan;
	}
	
	private CalendarEventInterface dataObject;
	
	/**
	 * Getter for the dataObject property.
	 * @return the current value of the dataObject property
	 */
	public CalendarEventInterface getDataObject() {
		return this.dataObject;
	}
	
	/**
	 * Setter for the dataObject property.
	 * @param dataObject the new value for the dataObject property
	 */
	public void setDataObject(final CalendarEventInterface dataObject) {
		this.dataObject = dataObject;
	}
	
	private Date startTime;
	
	/**
	 * Getter for the startTime property.
	 * @return the current value of the startDate property
	 */
	public Date getStartTime() {
		return this.startTime;
	}
	
	private Date endTime;
	
	/**
	 * Getter for the endTime property.
	 * @return the current value of the endDate property
	 */
	public Date getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Constructor. Provide a start date and an end date.
	 * @param startTime the start date
	 * @param endTime the end date
	 */
	public CalendarCell(final Date startTime, final Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

}
