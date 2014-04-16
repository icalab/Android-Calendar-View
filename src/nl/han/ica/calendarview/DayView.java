package nl.han.ica.calendarview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Class for creating day view objects: lists of events for a single day.
 * 
 * @author matthijs
 *
 */
public class DayView extends LinearLayout {



	

	/**
	 * Available resolutions to use for the event grid:
	 * RESOLUTION_QUARTER
	 * RESOLUTION_HALF_HOUR
	 * RESOLUTION_HOUR
	 */
	public static final int RESOLUTION_QUARTER = 1;

	public static final int RESOLUTION_HALF_HOUR = 2;

	public static final int RESOLUTION_HOUR = 3;

	private int resolution;

	private int numMinutesPerCell;

	private List<? extends CalendarEventInterface> events;


	/**
	 * Constructor. Provide a day view object (a list of events arranged into  
	 * @param context the application context
	 * @param events the list of events. Must conform both to the CalendarEventInterface and the TableColumnWithRowSpanCellDataInterface
	 * @param cellViewClass the class that will be used to display a single event
	 * @param resolution the resolution to use for the grid. Must be one of RESOLUTION_QUARTER, RESOLUTION_HALF_HOUR, RESOLUTION_HOUR
	 * @param rowHeight the height of a single row. Must be larger than 0, obviously
	 */
	public DayView(
			final Context context, 
			final List<? extends CalendarEventInterface> events,
			final Class<? extends TableColumnWithRowSpanCellView> cellViewClass,
			final int resolution,
			final int rowHeight
			) {
		super(context);
		
		if(resolution != RESOLUTION_QUARTER 
				&& resolution != RESOLUTION_HALF_HOUR 
				&& resolution != RESOLUTION_HOUR) {
			throw new IllegalArgumentException("resolution is not a known resolution.");
		}

		if(rowHeight <= 0) {
			throw new IllegalArgumentException("rowHeight is less than or equal to zero.");
		}

		this.events = events;
		this.resolution = resolution;
		int numMinutesPerCell = 15;
		if(this.resolution == RESOLUTION_HALF_HOUR) {
			numMinutesPerCell = 30;
		}
		if(this.resolution == RESOLUTION_HOUR) {
			numMinutesPerCell = 60;
		}
		this.numMinutesPerCell = numMinutesPerCell;
		
		ArrayList<CalendarCell> cells = this.getCellsForEvents();
		
		// Create the column
		TableColumnWithRowSpan columnView = new TableColumnWithRowSpan(context,
				(List<? extends TableColumnWithRowSpanCellDataInterface>) cells,
				cellViewClass,
				rowHeight
				);
		columnView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

		// Attach the column and display it.
		this.removeAllViews();
		this.addView(columnView);
	}


	/**
	 * Helper method that creates a list of cells for an entire day, with
	 * events inserted in all the correct places.
	 * @return
	 */
	private ArrayList<CalendarCell> getCellsForEvents() {

		// Group events by hour and minute.
		HashMap<Integer, HashMap<Integer, CalendarEventInterface>> byHourAndMinute
		= new HashMap<Integer, HashMap<Integer, CalendarEventInterface>>();
		for(int i = 0; i < this.events.size(); i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.events.get(i).getStart());
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int minutes = calendar.get(Calendar.MINUTE);
			// Round off the number of minutes.
			minutes = minutes / this.numMinutesPerCell;
			double remainder = (minutes % this.numMinutesPerCell) / this.numMinutesPerCell;
			minutes = minutes + ((int) Math.round(remainder) * this.numMinutesPerCell);

			if(! byHourAndMinute.containsKey(hours)) {
				byHourAndMinute.put(hours, new HashMap<Integer, CalendarEventInterface>());
			}
			byHourAndMinute.get(hours).put(minutes, this.events.get(i));
		}

		// Create the list of events.
		ArrayList<CalendarCell> cells = new ArrayList<CalendarCell>();
		int hour = 0;
		int minute = 0;
		while(hour <= 23) {
			
			// Create start and end times for empty cells.
			// The exact date does not matter, all we care about
			// is the time.
			Calendar startTime = Calendar.getInstance();
			startTime.setTime(new Date());
			startTime.set(Calendar.HOUR, hour);
			startTime.set(Calendar.MINUTE, minute);
			int endHour = hour;
			int endMinute = minute + this.numMinutesPerCell;
			if(endMinute >= 60) {
				endMinute = 0;
				endHour++;
			}
			Calendar endTime = Calendar.getInstance();
			endTime.setTime(new Date());
			endTime.set(Calendar.HOUR, endHour);
			endTime.set(Calendar.MINUTE, endMinute);
			
			// There's an event that starts during this hour.
			if(byHourAndMinute.containsKey(hour)) {
				// There's an event for this time slot.
				if(byHourAndMinute.get(hour).containsKey(minute)) {
					CalendarEventInterface event = byHourAndMinute.get(hour).get(minute);
					CalendarCell cell = new CalendarCell(event.getStart(), event.getEnd());
					cell.setDataObject(event);
					cell.setRowSpan(this.getNumRowsForEvent(cell.getDataObject()));
					cells.add(cell);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(cell.getDataObject().getEnd());
					hour = calendar.get(Calendar.HOUR_OF_DAY);
					minute = calendar.get(Calendar.MINUTE);
					if(minute % this.numMinutesPerCell != 0) {
						minute = (minute / this.numMinutesPerCell) * this.numMinutesPerCell;
						minute = minute + this.numMinutesPerCell;
						if(minute >= 60) {
							minute = 0;
							hour++;
						}
					}
					continue;
				}
				// No event for this time slot.
				else {
					CalendarCell cell = new CalendarCell(startTime.getTime(), endTime.getTime());
					cell.setRowSpan(1);
					cells.add(cell);
					minute = minute + this.numMinutesPerCell;

				}
			}
			// No events this hour.
			else {
				CalendarCell cell = new CalendarCell(startTime.getTime(), endTime.getTime());
				cell.setRowSpan(1);
				cells.add(cell);
				minute = minute + this.numMinutesPerCell;
			}

			if(minute >= 60) {
				minute = 0;
				hour++;
			}

		}

		return cells;
	}

	/**
	 * Helper function that returns the number of rows an event should occupy.
	 * @param event the event
	 * @return the number of rows to occupy
	 */
	private int getNumRowsForEvent(final CalendarEventInterface event) {

		long diffMinutes = ((event.getEnd().getTime() - event.getStart().getTime()) / (60 * 1000));
		long numCells = diffMinutes / this.numMinutesPerCell;
		long remainingMinutes = diffMinutes % this.numMinutesPerCell;
		double remainingRatio = (double) remainingMinutes / (double) this.numMinutesPerCell;
		long remainingCells = Math.round(remainingRatio);
		numCells = numCells + remainingCells;
		if(numCells < 1) {
			numCells = 1;
		}
		return (int) numCells;
	}


}
