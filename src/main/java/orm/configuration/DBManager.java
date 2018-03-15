package orm.configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import orm.configuration.bean.ConfigurationBean;


public class DBManager {
	private static ConfigurationBean conf;
    static {
        Properties properties=new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf=new ConfigurationBean();
        conf.setDriver(properties.getProperty("driver"));
        conf.setPackageName(properties.getProperty("packageName"));
        conf.setSrcPath(properties.getProperty("srcPath"));
        conf.setPwd(properties.getProperty("pwd"));
        conf.setUser(properties.getProperty("user"));
        conf.setUrl(properties.getProperty("url"));
        conf.setDbType(properties.getProperty("dbType"));
        conf.setTableName("tableName");
    }

    public static Connection getConn(){
        try {
            //要求JVM查找并加载指定的数据库驱动
            Class.forName(conf.getDriver());
            return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ConfigurationBean getConf(){
        return conf;
    }
    
    public static void close(PreparedStatement ps){
    	DBManager.close(ps,null);
    }
    
    public static void close(Connection conn){
    	DBManager.close(null,conn);
    }
    
    public static void close(PreparedStatement ps , Connection conn){
    	if(ps!=null){
    		try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	if(conn!=null){
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    public static void main(String[] args) {
		System.out.println(conf.toString());
	}
}
