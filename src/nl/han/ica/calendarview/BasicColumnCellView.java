package nl.han.ica.calendarview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * An example of a basic column cell view class.
 * @author matthijs
 *
 */
public class BasicColumnCellView extends TableColumnWithRowSpanCellView {
	
	public BasicColumnCellView(
			Context context, 
			final TableColumnWithRowSpanCellDataInterface cellData) {
		super(context, cellData);
		
		TextView contentsView = new TextView(context);
		TableCell cell = (TableCell) cellData;
		contentsView.setText(cell.content);
		this.addView(contentsView);
		
		contentsView.setOnClickListener(new BasicColumnCellViewOnClickListener(cell));
	}

	private class BasicColumnCellViewOnClickListener implements OnClickListener {
		
		private TableCell cell;
		
		public BasicColumnCellViewOnClickListener(final TableCell cell) {
			this.cell = cell;
		}
		
		@Override
		public void onClick(final View v) {
			TableColumnWithRowSpanCellDataInterface item = 
					(TableColumnWithRowSpanCellDataInterface) this.cell;
			Log.d(this.getClass().toString(), "Clicked!");
			Log.d(this.getClass().toString(), "Row span is: " + item.getRowSpan());
		}
		
	}
}


