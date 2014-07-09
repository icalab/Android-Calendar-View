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
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
	public TimeColumn getTimeColumn() {
		return this.timeColumn;
	}

	private LinearLayout timeColumnWrapper;

	/**
	 * Getter for the timeColumnWrapper attribute that contains the linear layout
	 * that wraps the column with time indicator cells.
	 * @return the current value of the timeColumnWrapper attribute
	 */
	public LinearLayout getTimeColumnWrapper() {
		return this.timeColumnWrapper;
	}

	private LinearLayout eventWrapper;

	/**
	 * Getter for the eventWrapper attribute that contains the linear layout that holds the individual event columns.
	 * @return the current value of the eventWrapper attribute
	 */
	public LinearLayout getEventWrapper() {
		return this.eventWrapper;
	}

	private List<DayView> eventColumns;

	/**
	 * Getter for the eventColumns attribute that contains the list of DayView objects that hold the events.
	 * @return the current value of the eventColumns attribute
	 */
	public List<? extends DayView> getEventColumns() {
		return this.eventColumns;
	}

	private LinearLayout headerWrapper;

	/**
	 * Getter for the headerWrapper attribute that contains the linear layout that holds column labels.
	 * @return the linear layout that holds the column labels
	 */
	public LinearLayout getHeaderWrapper() {
		return this.headerWrapper;
	}

	private ScrollView contentWrapper;

	/**
	 * Getter for the contentWrapper attribute that holds scroll view surrounding the event columns and the time column.
	 * @return
	 */
	public ScrollView getContentWrapper() {
		return this.contentWrapper;
	}

	private LinearLayout contentSubWrapper;
	/**
	 * Getter for the contentSubWrapper attribute that holds the event columns and the time column.
	 * @return
	 */
	public LinearLayout getContentSubWrapper() {
		return this.contentSubWrapper;
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
	
	private int timeColumnWidth;
	/**
	 * Getter for the timeColumnWidth attribute that holds the width of the column with time labels in pixels.
	 * @return the current value of the timeColumnWidth attribute
	 */
	protected int getTimeColumnWidth() {
		return this.timeColumnWidth;
	}
	/**
	 * Constructor. Create a new CalendarView object.
	 * @param context the application context
	 * @param events the list of events
	 * @param cellViewClass the class that will display events
	 * @param dayLabelClass the class that will be used to display the date labels 
	 * @param resolution the resolution to use for the event grid. Must be one of DayView.RESOLUTION_QUARTER, DayView.RESOLUTION_HALF_HOUR, DayView.RESOLUTION_HOUR
	 * @param rowHeight the height of a single row. Must be larger than 0, obviously
	 * @param timeColumnWidth the width of the column with time labels
	 * @param startDate the first date for the calendar
	 * @param endDate the last date for the calendar
	 */
	public CalendarView(Context context,
			final List<? extends CalendarEventInterface> events,
			final Class<? extends TableColumnWithRowSpanCellView> cellViewClass,
			final Class<? extends DayLabelView> dayLabelClass,
			final int resolution,
			final int rowHeight,
			final int timeColumnWidth,
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
		this.timeColumnWidth = timeColumnWidth;
		this.rowHeight = rowHeight;
		this.startDate = startDate;
		this.endDate = endDate;

		this.timeColumnWrapper = new LinearLayout(context);
		this.timeColumnWrapper.setOrientation(LinearLayout.VERTICAL);
		this.timeColumnWrapper.setLayoutParams(new LinearLayout.LayoutParams(
				timeColumnWidth,
				LinearLayout.LayoutParams.MATCH_PARENT));
		this.timeColumnWrapper.setGravity(Gravity.BOTTOM);

		this.eventWrapper = new LinearLayout(context);
		this.eventWrapper.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				));
		this.eventWrapper.setOrientation(LinearLayout.HORIZONTAL);

		this.contentWrapper = new ScrollView(context);
		this.contentWrapper.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
				));
		this.contentSubWrapper = new LinearLayout(context);
		this.contentSubWrapper.setLayoutParams(new ScrollView.LayoutParams(
				ScrollView.LayoutParams.MATCH_PARENT,
				ScrollView.LayoutParams.MATCH_PARENT
				));
		this.contentWrapper.addView(contentSubWrapper);

		this.headerWrapper = new LinearLayout(context);
		this.headerWrapper.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				));
		this.headerWrapper.setOrientation(LinearLayout.HORIZONTAL);
		//this.headerWrapper.setGravity(Gravity.RIGHT);
		this.drawView();
	}

	/**
	 * The method that creates the view by creating and attaching sub views. It's
	 * protected so it can be overridden.
	 */
	protected void drawView() {

		this.setOrientation(LinearLayout.VERTICAL);

		this.timeColumn = new TimeColumn(this.getContext(), this.getResolution(), this.getRowHeight());
		this.getTimeColumnWrapper().addView(this.getTimeColumn());
		this.contentSubWrapper.addView(this.getTimeColumnWrapper());

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

		// Get a list of all relevant dates.
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(this.getStartDate());
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(this.getEndDate());
		endDate.set(Calendar.HOUR_OF_DAY, 0);
		endDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.SECOND, 0);
		ArrayList<Date> allDates = new ArrayList<Date>();
		while(currentDate.before(endDate) || currentDate.equals(endDate)) {
			allDates.add(currentDate.getTime());
			currentDate.add(Calendar.DATE, 1);
		}

		// Create columns.
		this.eventColumns = new ArrayList<DayView>();
		for(final Date date : allDates) {
			String dateString = format.format(date);
			List<? extends CalendarEventInterface> events = new ArrayList<CalendarEventInterface>();
			if(byDate.containsKey(dateString)) {
				events = byDate.get(dateString);
			}

			DayView column = new DayView(this.getContext(), date, events, this.getCellViewClass(), this.getResolution(), this.getRowHeight());
			DayLabelView label = null;
			try {
				label = (DayLabelView ) 
						this.getDayLabelClass().getDeclaredConstructor(new Class[] {Context.class, Date.class, ArrayList.class}).newInstance(this.getContext(), date, allDates);
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
					LayoutParams.WRAP_CONTENT,
					0.25f
					
					);
			columnWrapper.setLayoutParams(params);
			columnWrapper.setOrientation(LinearLayout.VERTICAL);

			if(label != null) {
				LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT,
						1f);
				label.setLayoutParams(labelParams);
				this.headerWrapper.addView(label);
			}
			columnWrapper.addView(column);
			this.eventWrapper.addView(columnWrapper);
		}

		this.contentSubWrapper.addView(this.getEventWrapper());
		this.addView(this.getHeaderWrapper());
		this.addView(this.getContentWrapper());
	}

	public void scrollToTime(final Date timeToScrollTo) {
		int totalNumCells = 4 * 24;
		long numMillisPerCell = 15 * 1000 * 60;
		if(this.getResolution() == DayView.RESOLUTION_HALF_HOUR) {
			numMillisPerCell = 30 * 1000 * 60;
			totalNumCells = totalNumCells / 2;
		}
		else if(this.getResolution() == DayView.RESOLUTION_HOUR) {
			numMillisPerCell = 60 * 1000 * 60;
			totalNumCells = totalNumCells / 4;
		}

		Calendar dayStart = Calendar.getInstance();
		dayStart.setTime(timeToScrollTo);
		dayStart.set(Calendar.HOUR_OF_DAY, 0);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		long numMillisToScroll = timeToScrollTo.getTime() - dayStart.getTimeInMillis();

		final int numCells = (int) Math.round(numMillisToScroll / numMillisPerCell);
		final int total = totalNumCells;
		final ScrollView wrapper = this.getContentWrapper();
		final LinearLayout subWrapper = this.getContentSubWrapper();
		wrapper.post(new Runnable() {
			@Override
			public void run() {
				int totalHeight = subWrapper.getMeasuredHeight();
				int cellHeight = totalHeight / total;
				// Subtract one to make sure the top of the cell is visible.
				wrapper.scrollTo(0, (numCells - 1) * cellHeight);
			}
		});


	}


}
