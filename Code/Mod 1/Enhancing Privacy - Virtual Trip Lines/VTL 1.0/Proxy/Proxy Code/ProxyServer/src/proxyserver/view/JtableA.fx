package proxyserver.view;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javafx.ext.swing.SwingComponent;

public  class TableColumn{
    public var text: String;
}
public class TableCell{
    public var text: String;
}

public class TableRow{

    public var cells: TableCell[];
}

public class JtableA extends SwingComponent {
    var jtab:JTable;
    var model:DefaultTableModel;
    public var selection: Integer;
    public var columns: TableColumn[] on replace{
        model = new DefaultTableModel(for(column in columns) column.text, 0);

        jtab.setModel(model);
        jtab.getTableHeader().setReorderingAllowed(false);
    };

    public var rows: TableRow[] on replace oldValue[lo..hi] = newVals{
        for(row in newVals){
            model.addRow(for(cell in row.cells) cell.text);
        }
    };

    public override function createJComponent(){
        jtab=new JTable();
        model=jtab.getModel() as DefaultTableModel;
        var selectionModel = jtab.getSelectionModel();
        selectionModel.addListSelectionListener(
            ListSelectionListener{
                public override function valueChanged(e: ListSelectionEvent ) {
                    selection = jtab.getSelectedRow();
                }
            }
        );
        var jsp:JScrollPane=new JScrollPane(jtab);
        var dim:java.awt.Dimension=new java.awt.Dimension(550,280);
        jsp.setPreferredSize(dim);
        return jsp;
    }
}