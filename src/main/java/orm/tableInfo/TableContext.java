package orm.tableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import orm.configuration.DBManager;

/**
 * 用来装载所有表
 * 
 * @author Administrator
 * 
 */
public class TableContext {
	/**
	 * key ---String 表名 value ---TableInfo 表结构
	 */
	public static Map<String, TableInfo> tables = new HashMap<String, TableInfo>();

	private TableContext() {

	}

	static {
		try {
			Connection con = DBManager.getConn();
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, "%", "%",
					new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = (String) rs.getObject("TABLE_NAME");
				System.out.println(tableName);
				TableInfo ti = new TableInfo(tableName,
						new HashMap<String, ColumnInfo>(),
						new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				// 获取多个字段和类型
				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");
				while (set.next()) {
					ColumnInfo ci = new ColumnInfo(
							set.getString("COLUMN_NAME"),
							set.getString("TYPE_NAME"), 0);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
				// 获取多个主键
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);
				while (set2.next()) {
					String columnName = set2.getString("COLUMN_NAME");
					//System.out.println(columnName);
					ColumnInfo ci2 = ti.getColumns().get(columnName);
					ci2.setKeyType(1);// 设为主键
					ti.getPriKeys().add(ci2);//id 这个列是主键
				}
				if (ti.getPriKeys().size() > 0) {// 取唯一主键，如果是联合主键，则为空
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(tables.get("t_user").getColumns().get("id").toString());
	}
}
