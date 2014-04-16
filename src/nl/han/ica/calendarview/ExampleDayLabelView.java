package nl.han.ica.calendarview;

import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Example implementation of a DayLabelView class. Displays the date in a text view and
 * provides listeners that output to Log.d
 * @author matthijs
 *
 */
public class ExampleDayLabelView extends DayLabelView {
	
	public ExampleDayLabelView(final Context context, final Date date) {
		super(context, date);
	}
	
	@Override
	protected View getView() {
		TextView dateView = new TextView(this.getContext());

		DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.getContext());
		dateView.setText(dateFormat.format(this.getDate()));
		// Set some padding so the view is easier to click and swipe on.
		dateView.setPadding(5, 5, 5, 5);
		return dateView;
	}
	
	@Override
	protected Runnable getOnLeftSwipeListener() {
		final String dateString = this.getDate().toString();
		return new Runnable() {
			public void run() {
				Log.d("ExampleDayLabelView", "Swiped left on date " + dateString);
			}
		};
	}
	
	@Override
	protected Runnable getOnRightSwipeListener() {
		final String dateString = this.getDate().toString();
		return new Runnable() {
			public void run() {
				Log.d("ExampleDayLabelView", "Swiped right on date " + dateString);
			}
		};
	}
	
	@Override
	protected Runnable getOnClickListener() {
		final String dateString = this.getDate().toString();
		return new Runnable() {
			public void run() {
				Log.d("ExampleDayLabelView", "Clicked on date " + dateString);
			}
		};
	}

}
