package orm.jdbc.template;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import orm.configuration.DBManager;
import orm.generator.entity.TCategory;
import orm.tableInfo.ColumnInfo;
import orm.tableInfo.TableContext;
import orm.tableInfo.TableInfo;
import orm.utils.JDBCUtil;
import orm.utils.ReflectionUtil;
import orm.utils.StringUtil;

public class JdbcTemplate {
	/**
	 * 执行DML操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeDML(String sql, Object[] params) {
		Connection conn = DBManager.getConn();
		int count = 0;
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			// 把参数加入sql中
			JDBCUtil.handlerParams(ps, params);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBManager.close(ps, conn);
		}
		return count;
	}

	

	
	//序列取出Id
/*    public static String getSequenceNextId(String sequenceName) {
        JdbcTemplate jdbcTemplate = WebContextUtils.getBean("jdbcTemplate");
        return jdbcTemplate.queryForObject("SELECT " + sequenceName + ".NEXTVAL FROM DUAL", String.class);
    }*/


	/**
	 * 插入对象到数据库，拼接的sql如 insert into users(id,name) values(?,?)
	 * 
	 * @param obj
	 *            要储存的数据
	 */
	public void insert(Object obj) {
		Class clazz = obj.getClass();
		int lastIndex = clazz.getName().lastIndexOf(".");
		String clazzName = clazz.getName().substring(lastIndex+1,clazz.getName().length());
		String tableKey = StringUtil.camelToUnderline(clazzName);
		// 获取tableInfo
		TableInfo tableInfo = TableContext.tables.get(tableKey);
		StringBuffer sb = new StringBuffer("insert into "
				+ tableInfo.getTname() + "(");
		Field[] fields = clazz.getDeclaredFields();
		Object[] fieldValue = new Object[fields.length];
		for(int i=0; i<fields.length;i++){
			Field field = fields[i];
			fieldValue[i] = ReflectionUtil.getFieldValue(obj, field.getName());
			sb.append(StringUtil.camelToUnderline(field.getName())+",");
		}
		sb.delete(sb.length()-1, sb.length());
		System.out.println(sb.toString());
		sb.append(") values (");
		for (int i=0;i<fields.length;i++){
            sb.append("?,");
        }
		sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		String sql = sb.toString();
		System.out.println(sql);
		JdbcTemplate.executeDML(sql,fieldValue);
	}
	
	
	/**
	 * 删除  delete from tableName where id = xxx
	 * @param obj
	 */
	@SuppressWarnings("rawtypes")
	public void delete (Object obj){
		Class clazz = obj.getClass();
		int lastIndex = clazz.getName().lastIndexOf(".");
		String clazzName = clazz.getName().substring(lastIndex+1,clazz.getName().length());
		String tableKey = StringUtil.camelToUnderline(clazzName);
		// 获取tableInfo
		TableInfo tableInfo = TableContext.tables.get(tableKey);
		//获取主键
		ColumnInfo  onlyKey = tableInfo.getOnlyPriKey();
		Object fieldValue = ReflectionUtil.getFieldValue(obj, onlyKey.getName());
		StringBuffer sb = new StringBuffer();
		sb.append("delete from "+tableInfo.getTname()+" where "+onlyKey.getName()+"= ?");
		String sql=sb.toString();
		System.out.println(sql);
		JdbcTemplate.executeDML(sql, new Object[]{fieldValue});
	}
	
	@SuppressWarnings("rawtypes")
	public void delete(Class clazz,Object id){
		int lastIndex = clazz.getName().lastIndexOf(".");
		String clazzName = clazz.getName().substring(lastIndex+1,clazz.getName().length());
		String tableKey = StringUtil.camelToUnderline(clazzName);
        //通过Class获取TableInfo
        TableInfo tableInfo = TableContext.tables.get(tableKey);
        //获得主键
        ColumnInfo columnInfo = tableInfo.getOnlyPriKey();
        String sql = "delete from " + tableInfo.getTname() + " where " + columnInfo.getName() + " =?";
        executeDML(sql, new Object[]{id});
    }
	
	/**
	 * update TCategory set xxx=yyy where id=xxx
	 * @param obj
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(Object obj) {
		Class clazz = obj.getClass();
		int lastIndex = clazz.getName().lastIndexOf(".");
		String clazzName = clazz.getName().substring(lastIndex+1,clazz.getName().length());
		String tableKey = StringUtil.camelToUnderline(clazzName);
		// 获取tableInfo
		TableInfo tableInfo = TableContext.tables.get(tableKey);
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
		StringBuffer sb = new StringBuffer("update   "
				+ tableInfo.getTname() + " set ");
		Field[] fields = clazz.getDeclaredFields();
		List<Object> params = new ArrayList();
		for(int i=0; i<fields.length;i++){
			Field field = fields[i];
			params.add( ReflectionUtil.getFieldValue(obj, field.getName()));
			sb.append(StringUtil.camelToUnderline(field.getName())+" = ?,");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(" where "+onlyPriKey.getName()+" = ? ");
		Object id = ReflectionUtil.getFieldValue(obj, onlyPriKey.getName());
		params.add(id);
		//System.out.println(sb.toString());
		String sql = sb.toString();
		System.out.println(sql);
		JdbcTemplate.executeDML(sql,params.toArray());
	} 
	
	/**
     * 返回多行记录，并将每行记录封装到指定的类中
     * @param sql   查询语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return  查询到的结果
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryRow(final String sql,final Class clazz,final Object[] params){
        Connection connection=DBManager.getConn();
        ResultSet resultSet=null;
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            JDBCUtil.handlerParams(ps,params);
            resultSet=ps.executeQuery();
            List result=new ArrayList();
                ResultSetMetaData resultSetMetaData= null;
                try {
                    resultSetMetaData = resultSet.getMetaData();
                    while (resultSet.next()){
                        Object rowObj=clazz.newInstance();
                        //获得遍历该行的字段,并将其赋值给对象
                        for(int i=0;i<resultSetMetaData.getColumnCount();i++){
                            //获取字段名
                            String columnName=resultSetMetaData.getColumnLabel(i+1);
                            //获取字段属性
                            Object columnValue=resultSet.getObject(i+1);
                            //赋值
                            if(columnValue!=null)
                            ReflectionUtil.invokeSet(columnName,columnValue,rowObj);
                        }
                        result.add(rowObj);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }finally {
                    DBManager.close(ps,connection);
                }
                return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    } 
    
    
    
    /**
     * 返回一行记录，并将一行记录封装到指定的类中
     * @param sql   查询语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return  查询到的结果
     */
    public Object queryUniqueRow(String sql,Class clazz,Object[] params){
        List result=queryRow(sql,clazz,params);
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }
	
    /**
     * 返回一个值（一行一列）
     * @param sql   查询语句
     * @param params    sql的参数
     * @return  查询到的结果
     */
    public Object queryValue(String sql,Object[] params){
        Connection connection=DBManager.getConn();
        ResultSet resultSet=null;
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            JDBCUtil.handlerParams(ps,params);
            resultSet=ps.executeQuery();
           try {
                    while (resultSet.next()){
                        //只返回第一条记录的第一个字段的值
                        return resultSet.getObject(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    DBManager.close(ps,connection);
                }
        }catch(SQLException e){
        	 e.printStackTrace();
        }
        return null;
    }
    
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) {
		JdbcTemplate jt=new JdbcTemplate();
		
		//测试insert
/*		TCategory t= new TCategory();
        t.setCountComments(123);
        t.setCountTopics(456);
        t.setId(233);
        t.setName("python");
		jt.insert(t);*/
		
		
		//测试删除
       TCategory t= new TCategory();
        t.setCountComments(123);
        t.setCountTopics(456);
        t.setId(233);
        t.setName("python");
        jt.delete(t);
		//测试更新
       /* t.setId(234);
        t.setName("nodeJs");
        jt.update(t);*/
		//TCategory t= new TCategory();
       /* Class clazz = t.getClass();
		int lastIndex = clazz.getName().lastIndexOf(".");
		String clazzName = clazz.getName().substring(lastIndex+1,clazz.getName().length());
		String tableKey = StringUtil.camelToUnderline(clazzName);
        String sql="select * from t_category where name != ?";
        String name="nodeJs";
        Object l = jt.queryUniqueRow(sql, clazz, new Object[]{name});
        TCategory tt=(TCategory) l;
        System.out.println(tt.toString());*/
		/*for(int i =0;i<l.size();i++){
			TCategory tt = (TCategory) l.get(i);
			System.out.println(tt.toString());
		}*/
	}
}
