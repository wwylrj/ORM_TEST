package orm.configuration.bean;

import java.io.Serializable;

public class ConfigurationBean implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = -6974919662786061793L;
	/**
      * #数据库驱动
driver=com.mysql.jdbc.Driver
#数据库url
url=jdbc\:mysql\://localhost\:3306/shiro
#数据库用户名
user=root
#数据库密码
pwd=root
#使用数据库类型
usingDB=mysql
#项目src地址
srcPath=/home/xjk/IdeaProjects/SORM/src
#生成JavaBean包名
poPackage=com.xxx.xxx
      */
	private String driver;
	private String url;
	private String user;
	private String pwd;
	private String dbType;
	private String packageName;
	private String srcPath;
	private String tableName;
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ConfigurationBean [driver=" + driver + ", url=" + url
				+ ", user=" + user + ", pwd=" + pwd + ", dbType=" + dbType
				+ ", packageName=" + packageName + ", srcPath=" + srcPath
				+ ", tableName=" + tableName + "]";
	}
	
	
}
