package nl.han.ica.calendarview;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Example calendar cell view class.
 * 
 * @author matthijs
 *
 */
public class ExampleCalendarCellView extends TableColumnWithRowSpanCellView {

	/**
	 * Constructor.
	 * 
	 * The @SuppressWarnings directive is required because the setBackgroundDrawable has been
	 * renamed (and therefore deprecated) in API level 16.
	 * 
	 * @param context
	 * @param cellData an
	 */
	@SuppressWarnings("deprecation")
	public ExampleCalendarCellView(
			Context context, 
			final TableColumnWithRowSpanCellDataInterface cellData) {
		super(context, cellData);
		TextView contentsView = new TextView(context);
		contentsView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		CalendarCell cell = (CalendarCell) cellData;
		CalendarEventInterface event = cell.getDataObject();
		if(event != null) {
			SimpleDateFormat format = new SimpleDateFormat("hh:mm");
			contentsView.setText(format.format(cell.getStartTime())
					+ ":"
					+ format.format(cell.getEndTime()));
		}


		// Example of how to create a table cell border.

		if(cell.getDataObject() != null) {
			ShapeDrawable background = new ShapeDrawable();
			background.getPaint().setColor(Color.LTGRAY);
			ShapeDrawable border = new ShapeDrawable();
			int borderColor = Color.BLACK;
			border.getPaint().setColor(borderColor);
			Drawable[] layers = new Drawable[2];
			layers[0] = border;
			layers[1] = background;
			LayerDrawable layerList = new LayerDrawable(layers);
			layerList.setLayerInset(0, 0, 0, 0, 0);
			layerList.setLayerInset(1, 1, 1, 1, 1);
			int sdk = android.os.Build.VERSION.SDK_INT;
			if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				this.setBackgroundDrawable(layerList);
			} else {
				this.setBackground(layerList);
			}
			this.setPadding(10, 10, 10, 10);
		}
		else {
			ShapeDrawable background = new ShapeDrawable();
			background.getPaint().setColor(Color.WHITE);
			ShapeDrawable border = new ShapeDrawable();
			int borderColor = Color.LTGRAY;
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(cell.getStartTime());
			if(startCalendar.get(Calendar.MINUTE) == 0) {
				borderColor = Color.GRAY;
			}
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
		}


		this.setClickable(true);
		this.setOnClickListener(new ExampleCalendarCellViewOnClickListener(cell));

		this.addView(contentsView);


	}

}
