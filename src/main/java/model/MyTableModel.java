package model;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
    // 列名
    private String[] columnNames;
    Object[][] data;

    public MyTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }
    // 获取行数
    @Override
    public int getRowCount() {
        return data.length;
    }
    // 获取列数
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    // 获取具体单元格的值
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    // 获取列名
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    // 是否可以编辑， 默认不可编辑
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    // 更新数据并刷新表格
    public void updateDataAndRefresh(Object[][] data) {
        this.data = data;
        // 刷新表格数据
        fireTableDataChanged();
    }
}