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
	 * ִ��DML����
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
			// �Ѳ�������sql��
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

	

	
	//����ȡ��Id
/*    public static String getSequenceNextId(String sequenceName) {
        JdbcTemplate jdbcTemplate = WebContextUtils.getBean("jdbcTemplate");
        return jdbcTemplate.queryForObject("SELECT " + sequenceName + ".NEXTVAL FROM DUAL", String.class);
    }*/


	/**
	 * ����������ݿ⣬ƴ�ӵ�sql�� insert into users(id,name) values(?,?)
	 * 
	 * @param obj
	 *            Ҫ���������
	 */
	public void insert(Object obj) {
		Class clazz = obj.getClass();
		int lastIndex = clazz.getName().lastIndexOf(".");
		String clazzName = clazz.getName().substring(lastIndex+1,clazz.getName().length());
		String tableKey = StringUtil.camelToUnderline(clazzName);
		// ��ȡtableInfo
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
	 * ɾ��  delete from tableName where id = xxx
	 * @param obj
	 */
	@SuppressWarnings("rawtypes")
	public void delete (Object obj){
		Class clazz = obj.getClass();
		int lastIndex = clazz.getName().lastIndexOf(".");
		String clazzName = clazz.getName().substring(lastIndex+1,clazz.getName().length());
		String tableKey = StringUtil.camelToUnderline(clazzName);
		// ��ȡtableInfo
		TableInfo tableInfo = TableContext.tables.get(tableKey);
		//��ȡ����
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
        //ͨ��Class��ȡTableInfo
        TableInfo tableInfo = TableContext.tables.get(tableKey);
        //�������
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
		// ��ȡtableInfo
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
     * ���ض��м�¼������ÿ�м�¼��װ��ָ��������
     * @param sql   ��ѯ���
     * @param clazz ��װ���ݵ�javabean���Class����
     * @param params    sql�Ĳ���
     * @return  ��ѯ���Ľ��
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
                        //��ñ������е��ֶ�,�����丳ֵ������
                        for(int i=0;i<resultSetMetaData.getColumnCount();i++){
                            //��ȡ�ֶ���
                            String columnName=resultSetMetaData.getColumnLabel(i+1);
                            //��ȡ�ֶ�����
                            Object columnValue=resultSet.getObject(i+1);
                            //��ֵ
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
     * ����һ�м�¼������һ�м�¼��װ��ָ��������
     * @param sql   ��ѯ���
     * @param clazz ��װ���ݵ�javabean���Class����
     * @param params    sql�Ĳ���
     * @return  ��ѯ���Ľ��
     */
    public Object queryUniqueRow(String sql,Class clazz,Object[] params){
        List result=queryRow(sql,clazz,params);
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }
	
    /**
     * ����һ��ֵ��һ��һ�У�
     * @param sql   ��ѯ���
     * @param params    sql�Ĳ���
     * @return  ��ѯ���Ľ��
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
                        //ֻ���ص�һ����¼�ĵ�һ���ֶε�ֵ
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
		
		//����insert
/*		TCategory t= new TCategory();
        t.setCountComments(123);
        t.setCountTopics(456);
        t.setId(233);
        t.setName("python");
		jt.insert(t);*/
		
		
		//����ɾ��
       TCategory t= new TCategory();
        t.setCountComments(123);
        t.setCountTopics(456);
        t.setId(233);
        t.setName("python");
        jt.delete(t);
		//���Ը���
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
