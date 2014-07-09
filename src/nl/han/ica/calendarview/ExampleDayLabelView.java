package nl.han.ica.calendarview;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

/**
 * Example implementation of a DayLabelView class. Displays the date in a text view and
 * provides listeners that output to Log.d
 * @author matthijs
 *
 */
public class ExampleDayLabelView extends DayLabelView {
	
	public ExampleDayLabelView(final Context context, final Date date, final ArrayList<Date> allDates) {
		super(context, date, allDates);
		/*
		ShapeDrawable background = new ShapeDrawable();
		background.getPaint().setColor(Color.RED);
		ShapeDrawable border = new ShapeDrawable();
		int borderColor = Color.GREEN;
		border.getPaint().setColor(borderColor);
		Drawable[] layers = new Drawable[2];
		layers[0] = border;
		layers[1] = background;
		LayerDrawable layerList = new LayerDrawable(layers);
		layerList.setLayerInset(0, 0, 0, 0, 0);
		layerList.setLayerInset(1, 0, 1, 0, 0);
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			this.setBackgroundDrawable(layerList);
		} else {
			this.setBackground(layerList);
		}
		*/
	
		TextView dateView = new TextView(this.getContext());

		DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.getContext());
		dateView.setText(dateFormat.format(this.getDate()));
		// Set some padding so the view is easier to click and swipe on.
		dateView.setPadding(5, 5, 5, 5);
		this.addView(dateView);
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
