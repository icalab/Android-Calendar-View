package nl.han.ica.calendarview;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * Gesture listener that detects left / right swipes on a specific view. Since
 * gesture listeners block regular onClick events, these are handled here as well.
 * @author matthijs
 *
 */
public class LeftRightSwipeClickListener extends SimpleOnGestureListener {
	
    private static final int SWIPE_MIN_DISTANCE = 30;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    	
	private Runnable onLeftListener;
	
	/**
	 * Setter for the onLeftListener property.
	 * @param onLeftListener the runnable that will be run when a left swipe is detected
	 */
	public void setOnLeftListener(final Runnable onLeftListener) {
		this.onLeftListener = onLeftListener;
	}
	
	private Runnable onRightListener;
	
	/**
	 * Setter for the onRightListener property.
	 * @param onRightListener the runnable that will be run when a right swipe is detected
	 */
	public void setOnRightListener(final Runnable onRightListener) {
		this.onRightListener = onRightListener;
	}
	
	private Runnable onClickListener;
	/**
	 * Setter for the onClickListener property.
	 * @param onClickListener the runnable that will be run when a click is detected
	 */
	public void setOnClickListener(final Runnable onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(this.onLeftListener != null) {
                	this.onLeftListener.run();
                }
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(this.onRightListener != null) {
                	this.onRightListener.run();
                }
            }
        } catch (Exception e) {
            // nothing
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
          return true;
    }
    
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
    	if(this.onClickListener != null) {
    		this.onClickListener.run();
    	}
    	return true;
    }

}
