package orm.conver;

public interface TypeConvertor {
    /**
     * �����ݿ�����ת��Ϊjava��������
     * @param column ���ݿ��ֶε�����
     * @return java����������
     */
    public String databaseType2JavaType(String column);

    /**
     * ��java��������ת��Ϊ���ݿ�����
     * @param column java����������
     * @return ���ݿ��ֶε�����
     */
    public String javaType2DatabaseType(String column);
}
