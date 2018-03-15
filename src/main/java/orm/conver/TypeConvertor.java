package orm.conver;

public interface TypeConvertor {
    /**
     * 将数据库类型转换为java数据类型
     * @param column 数据库字段的类型
     * @return java的数据类型
     */
    public String databaseType2JavaType(String column);

    /**
     * 将java数据类型转换为数据库类型
     * @param column java的数据类型
     * @return 数据库字段的类型
     */
    public String javaType2DatabaseType(String column);
}
