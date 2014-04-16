package nl.han.ica.calendarview;

import java.util.Date;

public interface CalendarEventInterface {
	/**
	 * Return the start time for this calendar event.
	 * @return the start time for this calendar event
	 */
	public Date getStart();
	
	/**
	 * Return the end time for this calendar event.
	 * @return the end time for this calendar event
	 */
	public Date getEnd();
}
