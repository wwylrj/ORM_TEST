package orm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import orm.configuration.DBManager;
import orm.conver.TypeConvertor;
import orm.conver.impl.MySQLTypeConvertor;
import orm.generator.JavaFieldGetSet;
import orm.tableInfo.ColumnInfo;
import orm.tableInfo.TableContext;
import orm.tableInfo.TableInfo;

/**
 * javaBean���ɹ���
 * һ�����͵�template
 * @author Administrator
 *
 */
public class JavaBeanGeneratorUtil {
    /**
     * �����ֶ���Ϣ����java������Ϣ���磺var username-->private String username;��Ӧ��set��get����Դ��
     * @param column    �ֶ���Ϣ
     * @param convertor ����ת����
     * @return  java���Ե�set/get����
     */
    public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor){
        JavaFieldGetSet jfgs=new JavaFieldGetSet();
        //���ֶ�תΪjava����
        String javaFiledType= convertor.databaseType2JavaType(column.getDataType());
        String colunmName=StringUtil.underlineToSmallCamel(column.getName());
        //�����ֶ���Ϣ
        jfgs.setFieldInfo("\tprivate "+javaFiledType+" "+colunmName+";\n");
        //����getter
        StringBuilder getSrc=new StringBuilder();
        getSrc.append("\tpublic "+javaFiledType+" get"+StringUtil.underlineToBigCamel(column.getName())+"(){\n");
        getSrc.append("\t\treturn "+colunmName+";\n");
        getSrc.append("\t}\n");
        jfgs.setGetInfo(getSrc.toString());
        //����setter
        StringBuilder setSrc=new StringBuilder();
        setSrc.append("\tpublic void set"+StringUtil.underlineToBigCamel(column.getName())+"("+javaFiledType+" "+colunmName+"){\n");
        setSrc.append("\t\tthis."+colunmName+"="+colunmName+";\n");
        setSrc.append("\t}\n");
        jfgs.setSetInfo(setSrc.toString());
        return jfgs;
    }

    /**
     * ���ݱ���Ϣ����javaԴ��
     * @param tableInfo ����Ϣ
     * @param convertor ��������ת����
     * @return  java���Դ����
     */
    public static String createJavaSrc(TableInfo tableInfo,TypeConvertor convertor){
        Map<String,ColumnInfo>columns=tableInfo.getColumns();
        List<JavaFieldGetSet> javaFields=new ArrayList<>();
        for (ColumnInfo columnInfo:columns.values()){
            JavaFieldGetSet javaFieldGetSet=createFieldGetSetSRC(columnInfo,convertor);
            javaFields.add(javaFieldGetSet);
        }
        StringBuilder src=new StringBuilder();
        //����package���
        src.append("package "+ DBManager.getConf().getPackageName()+";\n");
        //����import���
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");
        //�������������
        src.append("public class "+StringUtil.underlineToBigCamel(tableInfo.getTname())+"{\n");
        //���������б�
        for (JavaFieldGetSet javaFieldGetSet:javaFields){
            src.append(javaFieldGetSet.getFieldInfo());
        }
        src.append("\n\n");
        //����get�����б�
        for (JavaFieldGetSet javaFieldGetSet:javaFields){
            src.append(javaFieldGetSet.getGetInfo());
        }
        src.append("\n\n");
        //����set�����б�
        for (JavaFieldGetSet javaFieldGetSet:javaFields){
            src.append(javaFieldGetSet.getSetInfo());
        }
        src.append("\n\n");
        //���ɽ�����
        src.append("}");
        return src.toString();
    }

    /**
     * ����java�ļ�
     * @param tableInfo ����Ϣ
     * @param convertor ����ת����
     */
    public static void createJavaPoFile(TableInfo tableInfo,TypeConvertor convertor){
        //��ȡԴ��
        String src=createJavaSrc(tableInfo,convertor);
        String srcPath=DBManager.getConf().getSrcPath()+"/";
        //������ת��Ϊ�ļ�����Ȼ���srcPathƴ��
        String packagePath=DBManager.getConf().getPackageName().replace(".","/");
        System.out.println(srcPath+packagePath);
        File f=new File(srcPath+packagePath);
        if (!f.exists()){
            f.mkdirs();
        }
        BufferedWriter bw=null;
        try {
            //��Դ��д���ļ�
            bw=new BufferedWriter(new FileWriter(f.getAbsoluteFile()+"/"+StringUtil.underlineToBigCamel(tableInfo.getTname())+".java"));
            bw.write(src);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ���±�ṹ
     */
    public static void updateJavaPOFile(){
        Map<String,TableInfo>tables=TableContext.tables;
        for(TableInfo tableInfo:tables.values()){
            createJavaPoFile(tableInfo,new MySQLTypeConvertor());
        }
    }
    
    /**
     * �������С����
     * ���ݴ�������ݿ��Զ��������еı��get set����
     * @param args
     */
    public static void main(String[] args) {
    	//�������ɵ���javaBean
    	ColumnInfo c = new ColumnInfo();
		c.setDataType("varchar");
		c.setKeyType(1);
		c.setName("id");
		Map<String,ColumnInfo> m = new HashMap<String, ColumnInfo>();
		m.put("t_test", c);
		TypeConvertor convertor = new MySQLTypeConvertor();
		JavaFieldGetSet f=JavaBeanGeneratorUtil.createFieldGetSetSRC(c, convertor);
		System.out.println(f.toString());
		TableInfo tableInfo = new TableInfo();
		tableInfo.setTname("t_test");
		tableInfo.setOnlyPriKey(c);
		tableInfo.setColumns(m);
		JavaBeanGeneratorUtil.createJavaSrc(tableInfo, convertor);
		JavaBeanGeneratorUtil.createJavaPoFile(tableInfo, convertor);
    	//�����������ݿ�����tables --> ��Ӧ��javaBean
    	/*Map<String,TableInfo> map=TableContext.tables;
    	Set<String> s= map.keySet();
		TypeConvertor convertor = new MySQLTypeConvertor();
    	for(String tableName:s){
    		TableInfo t = map.get(tableName);
    		Map<String,ColumnInfo> m=t.getColumns();
    		JavaBeanGeneratorUtil.createJavaSrc(t, convertor);
    		JavaBeanGeneratorUtil.createJavaPoFile(t, convertor);
    	}*/
	}
}