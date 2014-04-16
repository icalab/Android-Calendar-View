package nl.han.ica.calendarview;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

public class CalendarView extends LinearLayout {
	
	private Class<? extends DayLabelView> dayLabelClass;
	
	/**
	 * Getter for the dayLabelClass attribute that contains the class used to create day labels.
	 * @return the current value of the dayLabelClass attribute
	 */
	protected Class<? extends DayLabelView> getDayLabelClass() {
		return this.dayLabelClass;
	}
	
	private Class<? extends TableColumnWithRowSpanCellView> cellViewClass;
	
	/**
	 * Getter for the cellViewClass attribute that contains the class used to create event cells.
	 * @return the current value of the cellViewClass attribute
	 */
	protected Class<? extends TableColumnWithRowSpanCellView> getCellViewClass() {
		return this.cellViewClass;
	}
	
	private TimeColumn timeColumn;
	
	/**
	 * Getter for the timeColumn attribute that contains the time indicator cells.
	 * @return the current value of the timeColumn attribute
	 */
	protected TimeColumn getTimeColumn() {
		return this.timeColumn;
	}
	
	private LinearLayout timeColumnWrapper;
	
	/**
	 * Getter for the timeColumnWrapper attribute that contains the linear layout
	 * that wraps the column with time indicator cells.
	 * @return the current value of the timeColumnWrapper attribute
	 */
	protected LinearLayout getTimeColumnWrapper() {
		return this.timeColumnWrapper;
	}
	
	private LinearLayout eventWrapper;
	
	/**
	 * Getter for the eventWrapper attribute that contains the linear layout that holds the individual event columns.
	 * @return the current value of the eventWrapper attribute
	 */
	protected LinearLayout getEventWrapper() {
		return this.eventWrapper;
	}
	
	private List<DayView> eventColumns;
	
	/**
	 * Getter for the eventColumns attribute that contains the list of DayView objects that hold the events.
	 * @return the current value of the eventColumns attribute
	 */
	protected List<? extends DayView> getEventColumns() {
		return this.eventColumns;
	}
	
	private List<? extends CalendarEventInterface> events;
	
	/**
	 * Getter for the events attribute that contains the list of events to display in this calendar view.
	 * @return the current value of the events attribute
	 */
	protected List<? extends CalendarEventInterface> getEvents() {
		return this.events;
	}
	
	
	private Date startDate;
	/**
	 * Getter for the startDate attribute that holds the start date for the current calendar view.
	 * @return the current value of the startDate attribute
	 */
	protected Date getStartDate() {
		return this.startDate;
	}

	private Date endDate;
	/**
	 * Getter for the endDate attribute that holds the end date for the current calendar view.
	 * @return the current value of the endDate attribute
	 */
	protected Date getEndDate() {
		return this.endDate;
	}
	private int resolution;
	
	/**
	 * Getter for the resolution attribute that holds the resolution (quarter, half hour, hour) for the current calendar view.
	 * @return the current value of the resolution attribute
	 */
	protected int getResolution() {
		return this.resolution;
	}
	
	private int rowHeight;
	
	/**
	 * Getter for the rowHeight attribute that holds the height of each calendar row in pixels.
	 * @return the current value of the rowHeight attribute
	 */
	protected int getRowHeight() {
		return this.rowHeight;
	}
	/**
	 * Constructor. Create a new CalendarView object.
	 * @param context the application context
	 * @param events the list of events
	 * @param cellViewClass the class that will display events
	 * @param dayLabelClass the class that will be used to display the date labels 
	 * @param resolution the resolution to use for the event grid. Must be one of DayView.RESOLUTION_QUARTER, DayView.RESOLUTION_HALF_HOUR, DayView.RESOLUTION_HOUR
	 * @param rowHeight the height of a single row. Must be larger than 0, obviously
	 */
	public CalendarView(Context context,
			final List<? extends CalendarEventInterface> events,
			final Class<? extends TableColumnWithRowSpanCellView> cellViewClass,
			final Class<? extends DayLabelView> dayLabelClass,
			final int resolution,
			final int rowHeight,
			final Date startDate,
			final Date endDate
			) {
		super(context);
		
		if(resolution != DayView.RESOLUTION_QUARTER 
				&& resolution != DayView.RESOLUTION_HALF_HOUR 
				&& resolution != DayView.RESOLUTION_HOUR) {
			throw new IllegalArgumentException("resolution is not a known resolution.");
		}

		if(rowHeight <= 0) {
			throw new IllegalArgumentException("rowHeight is less than or equal to zero.");
		}
		
		this.events = events;
		this.cellViewClass = cellViewClass;
		this.dayLabelClass = dayLabelClass;
		this.resolution = resolution;
		this.rowHeight = rowHeight;
		this.startDate = startDate;
		this.endDate = endDate;
		
		// Set up defaults for the time column wrapper and the event wrapper. These
		// can be overruled in derived classes.
		this.timeColumnWrapper = new LinearLayout(context);
		this.timeColumnWrapper.setOrientation(LinearLayout.VERTICAL);
		this.timeColumnWrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
		this.timeColumnWrapper.setGravity(Gravity.BOTTOM);
		
		this.eventWrapper = new LinearLayout(context);
		this.eventWrapper.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				));
		this.eventWrapper.setOrientation(LinearLayout.HORIZONTAL);
		this.drawView();
	}
	
	/**
	 * The method that creates the view by creating and attaching sub views. It's
	 * protected so it can be overridden.
	 */
	protected void drawView() {

		this.setOrientation(LinearLayout.HORIZONTAL);
		
		this.timeColumn = new TimeColumn(this.getContext(), this.getResolution(), this.getRowHeight());
		this.getTimeColumnWrapper().addView(this.getTimeColumn());
		this.addView(this.getTimeColumnWrapper());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		// Group events by start date.
		HashMap<String, List<CalendarEventInterface>> byDate = new HashMap<String, List<CalendarEventInterface>>();
		for(CalendarEventInterface event : this.getEvents()) {
			String date = format.format(event.getStart());
			if(! byDate.containsKey(date)) {
				byDate.put(date, new ArrayList<CalendarEventInterface>());
			}
			byDate.get(date).add(event);
		}
		
		// Loop through every day starting from the start date and up to
		// and including the end date.
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(this.getStartDate());
		currentDate.set(Calendar.HOUR, 0);
		currentDate.set(Calendar.HOUR, 0);
		currentDate.set(Calendar.SECOND, 0);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(this.getEndDate());
		endDate.set(Calendar.HOUR, 0);
		endDate.set(Calendar.HOUR, 0);
		endDate.set(Calendar.SECOND, 0);
		
		this.eventColumns = new ArrayList<DayView>();
		while(currentDate.before(endDate) || currentDate.equals(endDate)) {
			String date = format.format(currentDate.getTime());
			List<? extends CalendarEventInterface> events = new ArrayList<CalendarEventInterface>();
			if(byDate.containsKey(date)) {
				events = byDate.get(date);
			}
			
			DayView column = new DayView(this.getContext(), events, this.getCellViewClass(), this.getResolution(), this.getRowHeight());
			
			DayLabelView label = null;
			try {
				label = (DayLabelView ) 
						this.getDayLabelClass().getDeclaredConstructor(new Class[] {Context.class, Date.class}).newInstance(this.getContext(), currentDate.getTime());
			}
			catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				Log.e(this.getClass().toString(), "Exception occurred instantiating day label view of class " + this.getDayLabelClass().getName() + ": " + exceptionAsString);
			}
			
			this.eventColumns.add(column);
			
			LinearLayout columnWrapper = new LinearLayout(this.getContext());
	
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT
			);
			params.weight = 1.0f;
			columnWrapper.setLayoutParams(params);
			columnWrapper.setOrientation(LinearLayout.VERTICAL);
	
			if(label != null) {
				columnWrapper.addView(label);
			}
			columnWrapper.addView(column);
			this.eventWrapper.addView(columnWrapper);
			
			currentDate.roll(Calendar.DATE, 1);
		}
		
		this.addView(this.getEventWrapper());

	}


}
