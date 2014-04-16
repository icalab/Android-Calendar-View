package nl.han.ica.calendarview;

import java.text.SimpleDateFormat;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ExampleCalendarCellViewOnClickListener implements OnClickListener {

	private CalendarCell cell;

	public ExampleCalendarCellViewOnClickListener(final CalendarCell cell) {
		super();
		this.cell = cell;
	}

	@Override
	public void onClick(final View v) {
		
		SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		String cellTime = format.format(this.cell.getStartTime())
				+ ":"
				+ format.format(this.cell.getEndTime());
		
		if(this.cell.getDataObject() == null) {
			Log.d(this.getClass().toString(), "Clicked an empty cell at " + cellTime);
			return;
		}
		

		Log.d(this.getClass().toString(), "Clicked event at " + cellTime);
	}

}
