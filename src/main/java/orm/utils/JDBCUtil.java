package orm.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import orm.configuration.DBManager;


/**
 * JDBCπ§æﬂ¿‡
 * 
 * @author Administrator
 * 
 */
public class JDBCUtil {
	public static void handlerParams(PreparedStatement ps, Object[] params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				try {
					ps.setObject(i+1, params[i]);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
}
