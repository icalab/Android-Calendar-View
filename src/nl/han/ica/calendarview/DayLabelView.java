package nl.han.ica.calendarview;

import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Class for day labels at the top of each calendar event column.
 * Allows assigning listeners to onClick, onLeftSwipe and onRightSwipe
 * @author matthijs
 *
 */
public abstract class DayLabelView extends LinearLayout {

	private Date date;

	/**
	 * Setter for the date attribute.
	 * @param date the new value for the date attribute
	 */
	public void setDate(final Date date) {
		this.date = date;
		this.updateDateView();
	}

	/**
	 * Getter for the date attribute. Useful in runnables.
	 * @return the current value of the date attribute
	 */
	public Date getDate() {
		return this.date;
	}
	private LeftRightSwipeClickListener swipeListener;

	/**
	 * Default implementation for the getOnLeftSwipeListener method that returns the runnable
	 * that will be used as an on left swipe listener
	 * @return the runnable that will be used as an on left swipe listener
	 */
	protected Runnable getOnLeftSwipeListener() {
		return null;
	}

	/**
	 * Default implementation for the getOnRightSwipeListener method that returns the runnable
	 * that will be used as an on right swipe listener
	 * @return the runnable that will be used as an on right swipe listener
	 */
	protected Runnable getOnRightSwipeListener() {
		return null;
	}

	/**
	 * Default implementation for the getOnClickListener method that returns the runnable
	 * that will be used as an on click listener
	 * @return the runnable that will be used as an on click listener
	 */
	protected Runnable getOnClickListener() {
		return null;
	}

	/**
	 * Setter for the onLeftSwipeListener property.
	 * @param onLeftSwipeListener the runnable that will be run when a left swipe is detected
	 */
	protected void setOnLeftSwipeListener(final Runnable onLeftSwipeListener) {
		this.swipeListener.setOnLeftListener(onLeftSwipeListener);
	}

	/**
	 * Setter for the onRightSwipeListener property.
	 * @param onRightSwipeListener the runnable that will be run when a right swipe is detected
	 */
	protected void setOnRightSwipeListener(final Runnable onRightSwipeListener) {
		this.swipeListener.setOnRightListener(onRightSwipeListener);
	}

	/**
	 * Setter for the onClickListener property.
	 * @param onClickListener the runnable that will be run when a click is detected
	 */
	protected void setOnClickListener(final Runnable onClickListener) {
		this.swipeListener.setOnClickListener(onClickListener);
	}

	/**
	 * Update the date view with the current value of this.date.
	 */
	private void updateDateView() {
		if( this.date == null) {
			return;
		}

	}

	/**
	 * Constructor. Creates the view and sets up the left swipe, right swipe and click listeners.
	 * @param context
	 */
	public DayLabelView(Context context, Date date) {
		super(context);
		this.setDate(date);
		
		this.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		this.addView(this.getView());


		this.swipeListener = new LeftRightSwipeClickListener();



		this.setOnLeftSwipeListener(new Runnable() {
			public void run() {
				Runnable leftSwipeListener = getOnLeftSwipeListener();
				if(leftSwipeListener != null) {
					leftSwipeListener.run();
				}
			}
		});

		this.setOnRightSwipeListener(new Runnable() {
			public void run() {
				Runnable rightSwipeListener = getOnRightSwipeListener();
				if(rightSwipeListener != null) {
					rightSwipeListener.run();
				}
			}
		});

		this.setOnClickListener(new Runnable() {
			public void run() {
				Runnable clickListener = getOnClickListener();
				if(clickListener != null) {
					clickListener.run();
				}
			}
		});

		final GestureDetector gestureDetector = new GestureDetector(context, swipeListener);
		View.OnTouchListener gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		this.setOnTouchListener(gestureListener);




	}

	/**
	 * This is the method that returns the view that contains whatever will be displayed by this view.
	 * @return the view with that will be attached to the DayLabelView
	 */
	protected abstract View getView();


}
