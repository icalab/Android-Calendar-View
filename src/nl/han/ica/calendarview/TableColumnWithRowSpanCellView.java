package nl.han.ica.calendarview;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Base class for cells that go in a TableColumnWithRowSpan linear layout view.
 * 
 * @author matthijs
 *
 */
public abstract class TableColumnWithRowSpanCellView extends LinearLayout {

	protected TableColumnWithRowSpanCellDataInterface cellData;
	
	/**
	 * Constructor. Override in derived classes to set up the views below the holder view itself.
	 * @param holderView the base view for the holder
	 */
	public TableColumnWithRowSpanCellView(
			final Context context,
			final TableColumnWithRowSpanCellDataInterface cellData) {
		super(context);
		this.cellData = cellData;
	}
}
