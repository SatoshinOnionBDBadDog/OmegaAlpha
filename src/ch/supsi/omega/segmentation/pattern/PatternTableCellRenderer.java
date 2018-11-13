package ch.supsi.omega.segmentation.pattern;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class PatternTableCellRenderer implements TableCellRenderer
{
	public final DefaultTableCellRenderer	DEFAULT_RENDERER	= new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Color foreground = Color.BLACK;
		Color background = Color.WHITE;

		if (column == 1)
		{
			try
			{
				if(row == 0)	
					foreground = PatternsLoader.patternsColor[4];
				else
					foreground = PatternsLoader.patternsColor[row-1];
			}
			catch (Exception e)
			{
				// nothing we can do...
			}
		}

		renderer.setForeground(foreground);
		renderer.setBackground(background);
		return renderer;
	}
}