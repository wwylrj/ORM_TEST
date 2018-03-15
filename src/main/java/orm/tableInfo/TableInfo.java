package orm.tableInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {
	/**
     * ����
     */
    private String tname;

    /**
     * �����ֶ���Ϣ
     */
    private Map<String,ColumnInfo> columns;

    /**
     * Ψһ������Ŀǰ����ֻ�ܴ������ֻ��һ�������������
     */
    private ColumnInfo onlyPriKey;

    /**
     * ��������
     */
    private List<ColumnInfo> priKeys;

    public TableInfo() {
    }

	public TableInfo(String tname, HashMap<String, ColumnInfo> columns,
			ArrayList<ColumnInfo> arrayList) {
		this.tname = tname;
		this.columns = columns;
		this.priKeys = arrayList;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ColumnInfo> columns) {
		this.columns = columns;
	}

	public ColumnInfo getOnlyPriKey() {
		return onlyPriKey;
	}

	public void setOnlyPriKey(ColumnInfo onlyPriKey) {
		this.onlyPriKey = onlyPriKey;
	}

	public List<ColumnInfo> getPriKeys() {
		return priKeys;
	}

	public void setPriKeys(List<ColumnInfo> priKeys) {
		this.priKeys = priKeys;
	}
    
    
}
