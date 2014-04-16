package nl.han.ica.calendarview;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * LinearLayout class for table columns with a row span for every cell.
 */
public class TableColumnWithRowSpan extends LinearLayout {

	private Context context; 
	private List<? extends TableColumnWithRowSpanCellDataInterface> items;
	private Class<? extends TableColumnWithRowSpanCellView> cellViewClass;
	private int rowHeight;

	public TableColumnWithRowSpan(
			Context context, 
			List<? extends TableColumnWithRowSpanCellDataInterface> items,
			Class<? extends TableColumnWithRowSpanCellView> cellViewClass,
			int rowHeight) {
		super(context);
		
		if(rowHeight <= 0) {
			throw new IllegalArgumentException("rowHeight is less than or equal to zero.");
		}
		
		this.context = context;
		this.items = items;
		this.cellViewClass = cellViewClass;
		this.rowHeight = rowHeight;
		
	   	this.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
	   	this.setOrientation(LinearLayout.VERTICAL);
	   	
	   	int numItems = items.size();
	   	for(int itemNum = 0; itemNum < numItems; itemNum++) {
	   		this.addView(this.getCellView(this.items.get(itemNum)));
	   	}
	}
	

	/**
	 * Helper function that creates the cell for a specific item.
	 * @param item the item to create the cell for
	 * @return the cell view
	 */
	private TableColumnWithRowSpanCellView getCellView(final TableColumnWithRowSpanCellDataInterface item) {

		TableColumnWithRowSpanCellView cellView;
		
		try {
			cellView = (TableColumnWithRowSpanCellView ) 
					this.cellViewClass.getDeclaredConstructor(new Class[] {Context.class, TableColumnWithRowSpanCellDataInterface.class}).newInstance(this.context, item);		
		}
		catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			Log.e(this.getClass().toString(), "Exception occurred instantiating cell view of class " + this.cellViewClass.getName() + ": " + exceptionAsString);
			return null;
		}
		
		int rowHeight = this.rowHeight * item.getRowSpan();
		LayoutParams params = (LinearLayout.LayoutParams) cellView.getLayoutParams();
		if (params == null) { 
			cellView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, rowHeight)); 
		} else { 
			params.height = rowHeight; 
		}
		
		return cellView;
	}
}
