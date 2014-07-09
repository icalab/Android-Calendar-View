package nl.han.ica.calendarview;

import nl.han.ica.activiteitenweger.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Usage example for the CalendarView class.
 * 
 * @author matthijs
 *
 */
public class ExampleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		// Test the basic table column with rowspan class (with a day label view)
		//testTableColumnWithRowSpan();

		// Test the DayView class
		//testDayView();
		// Test the CalendarView class
		//testSingleDayCalendarView();
		return true;
	}

	/*

	private void testSingleDayCalendarView(){

		ArrayList<nl.han.ica.activiteitenweger.model.Activity> events = this.getEvents();
		Date startDate = null;
		Date endDate = null;
		for(nl.han.ica.activiteitenweger.model.Activity event : events){
			if(startDate == null) {
				startDate = event.getStart();
			}
			else if (startDate.after(event.getStart())) {
				startDate = event.getStart();
			}
			if(endDate == null) {
				endDate = event.getEnd();
			}
			else if (endDate.before(event.getEnd())) {
				endDate = event.getEnd();
			}
		}

		CalendarView view = new CalendarView(
				(Context) this,
				this.getEvents(),
				ExampleCalendarCellView.class,
				ExampleDayLabelView.class,
				DayView.RESOLUTION_HALF_HOUR,
				60,
				startDate,
				endDate
				);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				ScrollView.LayoutParams.MATCH_PARENT,
				ScrollView.LayoutParams.WRAP_CONTENT));

		LinearLayout myLayout = (LinearLayout) findViewById(R.id.main);
		myLayout.addView(view);
	}

	private ArrayList<nl.han.ica.activiteitenweger.model.Activity> getEvents(){

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			// Generate some activities.
			ArrayList<nl.han.ica.activiteitenweger.model.Activity> events = new ArrayList<nl.han.ica.activiteitenweger.model.Activity>();

			nl.han.ica.activiteitenweger.model.Activity firstActivity = new nl.han.ica.activiteitenweger.model.Activity((Context) this);
			firstActivity.setStart(formatter.parse("1980-01-01 09:00"));
			firstActivity.setEnd(formatter.parse("1980-01-01 10:00"));
			firstActivity.setName("first activity");
			events.add(firstActivity);

			nl.han.ica.activiteitenweger.model.Activity secondActivity = new nl.han.ica.activiteitenweger.model.Activity((Context) this);
			secondActivity.setStart(formatter.parse("1980-01-02 11:00"));
			secondActivity.setEnd(formatter.parse("1980-01-02 11:30"));
			secondActivity.setName("second activity");
			events.add(secondActivity);

			nl.han.ica.activiteitenweger.model.Activity thirdActivity = new nl.han.ica.activiteitenweger.model.Activity((Context) this);
			thirdActivity.setStart(formatter.parse("1980-01-03 12:00"));
			thirdActivity.setEnd(formatter.parse("1980-01-03 12:15"));
			thirdActivity.setName("third activity");
			events.add(thirdActivity);

			
			//nl.han.ica.activiteitenweger.model.Activity fourthActivity = new nl.han.ica.activiteitenweger.model.Activity((Context) this);
			//fourthActivity.setStart(formatter.parse("1980-01-04 13:00"));
			//fourthActivity.setEnd(formatter.parse("1980-01-04 13:55"));
			//fourthActivity.setName("fourth activity");
			//events.add(fourthActivity);
	 

	return events;
}
catch(Exception e) {
	StringWriter sw = new StringWriter();
	e.printStackTrace(new PrintWriter(sw));
	String exceptionAsString = sw.toString();
	Log.e(this.getClass().toString(), 
			"Exception occurred in onCreateOptionsMenu:" + exceptionAsString);
	return null;
}
}

private void testDayView() {

	DayView dayView = new DayView(
			(Context) this,
			this.getEvents(),
			ExampleCalendarCellView.class,
			DayView.RESOLUTION_HALF_HOUR,
			60
			);
	dayView.setLayoutParams(new ScrollView.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT));

	LinearLayout myLayout = (LinearLayout) findViewById(R.id.main);
	ScrollView scrollWrapper = new ScrollView((Context) this);

	scrollWrapper.addView(dayView);
	myLayout.addView(scrollWrapper);



}



private void testTableColumnWithRowSpan() {

	LinearLayout myLayout = (LinearLayout) findViewById(R.id.main);

	// Generate some cells
	ArrayList<TableCell> cells = new ArrayList<TableCell>();
	cells.add(new TableCell());
	TableCell doubleCell = new TableCell();
	doubleCell.setRowSpan(2);
	cells.add(doubleCell);
	cells.add(new TableCell());
	cells.add(doubleCell);
	cells.add(new TableCell());
	cells.add(doubleCell);

	// Create the column
	TableColumnWithRowSpan column = new TableColumnWithRowSpan(
			(Context) this,
			cells,
			BasicColumnCellView.class,
			75
			);

	column.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT));


	myLayout.addView(column);
}

*/


}

