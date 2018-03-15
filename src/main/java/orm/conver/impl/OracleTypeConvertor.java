package orm.conver.impl;

import orm.conver.TypeConvertor;

public class OracleTypeConvertor implements TypeConvertor {

	public String databaseType2JavaType(String column) {
		switch (column.toLowerCase()) {
		case "varchar":
		case "char":
			return "String";
		case "smallint":
		case "int":
		case "tinyint":
			return "Integer";
		case "bigint":
			return "Long";
		case "double":
			return "Double";
		case "float":
			return "Double";
		case "clob":
			return "java.sql.Clob";
		case "blob":
			return "java.sql.Blob";
		case "date":
			return "java.sql.Date";
		case "time":
			return "java.sql.Time";
		case "timestamp":
			return "java.sql.Timestamp";
		default:
			return null;
		}
	}

	public String javaType2DatabaseType(String column) {
		// TODO Auto-generated method stub
		return null;
	}

}
