package orm.configuration.bean;

import java.io.Serializable;

public class ConfigurationBean implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = -6974919662786061793L;
	/**
      * #���ݿ�����
driver=com.mysql.jdbc.Driver
#���ݿ�url
url=jdbc\:mysql\://localhost\:3306/shiro
#���ݿ��û���
user=root
#���ݿ�����
pwd=root
#ʹ�����ݿ�����
usingDB=mysql
#��Ŀsrc��ַ
srcPath=/home/xjk/IdeaProjects/SORM/src
#����JavaBean����
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
