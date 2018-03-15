package orm.tableInfo;

/**
 * ���������ֶ�
 * @author Administrator
 *
 */
public class ColumnInfo {
    /**
     * �ֶ���
     */
    String name;
    /**
     * �ֶε���������
     */
    String dataType;
    /**
     * �ֶεļ����ͣ�0����ͨ����1��������2�������
     */
    int keyType;
    
    public ColumnInfo() {
    }

	public ColumnInfo(String name, String dataType, int keyType) {
		this.name = name;
		this.dataType = dataType;
		this.keyType = keyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", dataType=" + dataType
				+ ", keyType=" + keyType + "]";
	}
    
    
}
