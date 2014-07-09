package nl.han.ica.calendarview;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Provide a linear layout with cells for a column displaying time markings
 * alongside a list of calendar events.
 * @author matthijs
 *
 */
public class TimeColumn extends LinearLayout {

	private Context context;

	private int rowHeight;

	public TimeColumn(
			final Context context,
			final int resolution,
			final int rowHeight) {
		super(context);

		if(resolution != DayView.RESOLUTION_QUARTER 
				&& resolution != DayView.RESOLUTION_HALF_HOUR 
				&& resolution != DayView.RESOLUTION_HOUR) {
			throw new IllegalArgumentException("resolution is not a known resolution.");
		}

		if(rowHeight <= 0) {
			throw new IllegalArgumentException("rowHeight is less than or equal to zero.");
		}

		this.context = context;
		this.rowHeight = rowHeight;

		this.setOrientation(LinearLayout.VERTICAL);

		int numMinutesPerCell = 15;
		if(resolution == DayView.RESOLUTION_HALF_HOUR) {
			numMinutesPerCell = 30;
		}
		if(resolution == DayView.RESOLUTION_HOUR) {
			numMinutesPerCell = 60;
		}
		
		initBackgroundEmpty();
		initBackgroundLabel();
		initBackgroundLabelHalf();

		int hour = 0;
		int minute = 0;
		while(hour <= 23) {

			// We're copying the GoogleCalendar interface where we
			// only show times next to the start of a new hour.
			// Well, no. In the IOS app, half hours are shown as well.
			if(minute == 0 || ((resolution == DayView.RESOLUTION_QUARTER) && (minute == 30)) ) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				this.addView(
						this.generateCell(
								DateFormat.getTimeFormat(this.context).format(calendar.getTime())));
			}
			else {
				this.addView(this.generateCell());
			}
			minute = minute + numMinutesPerCell;

			if(minute >= 60) {
				minute = 0;
				hour++;
			}
		}
	}

	private LayerDrawable backgroundEmpty;
	private LayerDrawable backgroundContent;
	private LayerDrawable backgroundContentHalfHour;

	/**
	 * Generate a new empty cell.
	 * @return a new empty cell
	 */
	@SuppressWarnings("deprecation")
	protected View generateCell() {

		TextView cell = new TextView(this.context);
		cell.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		int sdk = android.os.Build.VERSION.SDK_INT;

		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			cell.setBackgroundDrawable(this.backgroundEmpty);
		} else {
			cell.setBackground(this.backgroundEmpty);
		}
		cell.setPadding(10,10,10,10);
		cell.setHeight(this.rowHeight);

		return cell;

	}

	/**
	 * Generate a new cell with content
	 * @param content the content to display in the cell
	 * @return the cell
	 */
	@SuppressWarnings("deprecation")
	protected View generateCell(String content) {

		TextView cell = new TextView(this.context);
		cell.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		int sdk = android.os.Build.VERSION.SDK_INT;

		LayerDrawable background = this.backgroundContent;
		if(content.matches(".*30$")) {
			cell.setTextColor(Color.GRAY);
			background = this.backgroundContentHalfHour;
		}

		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			cell.setBackgroundDrawable(background);
		} else {
			cell.setBackground(background);
		}
		cell.setPadding(10,10,10,10);
		cell.setText(content);
		



		cell.setHeight(this.rowHeight);

		return cell;

	}
	
	private void initBackgroundEmpty() {
		// Backgrounds for empty cells
		ShapeDrawable background = new ShapeDrawable();
		background.getPaint().setColor(Color.WHITE);
		ShapeDrawable border = new ShapeDrawable();
		border.getPaint().setColor(Color.LTGRAY);
		Drawable[] layers = new Drawable[2];
		layers[0] = border;
		layers[1] = background;
		LayerDrawable layerList = new LayerDrawable(layers);
		layerList.setLayerInset(0, 0, 0, 0, 0);
		layerList.setLayerInset(1, 0, 1, 1, 0);
		this.backgroundEmpty = layerList;
	}
	
	private void initBackgroundLabel() {
		ShapeDrawable background = new ShapeDrawable();
		background.getPaint().setColor(Color.WHITE);
		ShapeDrawable border = new ShapeDrawable();
		border.getPaint().setColor(Color.GRAY);
		ShapeDrawable rightBorder = new ShapeDrawable();
		rightBorder.getPaint().setColor(Color.LTGRAY);
		Drawable[] layers = new Drawable[3];
		layers[0] = rightBorder;
		layers[1] = border;
		layers[2] = background;
		LayerDrawable layerList = new LayerDrawable(layers);
		layerList.setLayerInset(0, 0, 0, 0, 0);
		layerList.setLayerInset(1, 0, 0, 1, 0);
		layerList.setLayerInset(2, 0, 1, 1, 0);
		this.backgroundContent = layerList;
	}
	
	private void initBackgroundLabelHalf() {
		ShapeDrawable background = new ShapeDrawable();
		background.getPaint().setColor(Color.WHITE);
		ShapeDrawable border = new ShapeDrawable();
		border.getPaint().setColor(Color.LTGRAY);
		ShapeDrawable rightBorder = new ShapeDrawable();
		rightBorder.getPaint().setColor(Color.LTGRAY);
		Drawable[] layers = new Drawable[3];
		layers[0] = rightBorder;
		layers[1] = border;
		layers[2] = background;
		LayerDrawable layerList = new LayerDrawable(layers);
		layerList.setLayerInset(0, 0, 0, 0, 0);
		layerList.setLayerInset(1, 0, 0, 1, 0);
		layerList.setLayerInset(2, 0, 1, 1, 0);
		this.backgroundContentHalfHour = layerList;
	}
}
