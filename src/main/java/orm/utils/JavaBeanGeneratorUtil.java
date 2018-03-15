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
 * javaBean生成工具
 * 一个典型的template
 * @author Administrator
 *
 */
public class JavaBeanGeneratorUtil {
    /**
     * 根据字段信息生成java属性信息，如：var username-->private String username;相应的set和get方法源码
     * @param column    字段信息
     * @param convertor 类型转换器
     * @return  java属性的set/get方法
     */
    public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor){
        JavaFieldGetSet jfgs=new JavaFieldGetSet();
        //将字段转为java属性
        String javaFiledType= convertor.databaseType2JavaType(column.getDataType());
        String colunmName=StringUtil.underlineToSmallCamel(column.getName());
        //生成字段信息
        jfgs.setFieldInfo("\tprivate "+javaFiledType+" "+colunmName+";\n");
        //生成getter
        StringBuilder getSrc=new StringBuilder();
        getSrc.append("\tpublic "+javaFiledType+" get"+StringUtil.underlineToBigCamel(column.getName())+"(){\n");
        getSrc.append("\t\treturn "+colunmName+";\n");
        getSrc.append("\t}\n");
        jfgs.setGetInfo(getSrc.toString());
        //生成setter
        StringBuilder setSrc=new StringBuilder();
        setSrc.append("\tpublic void set"+StringUtil.underlineToBigCamel(column.getName())+"("+javaFiledType+" "+colunmName+"){\n");
        setSrc.append("\t\tthis."+colunmName+"="+colunmName+";\n");
        setSrc.append("\t}\n");
        jfgs.setSetInfo(setSrc.toString());
        return jfgs;
    }

    /**
     * 根据表信息生成java源码
     * @param tableInfo 表信息
     * @param convertor 数据类型转换器
     * @return  java类的源代码
     */
    public static String createJavaSrc(TableInfo tableInfo,TypeConvertor convertor){
        Map<String,ColumnInfo>columns=tableInfo.getColumns();
        List<JavaFieldGetSet> javaFields=new ArrayList<>();
        for (ColumnInfo columnInfo:columns.values()){
            JavaFieldGetSet javaFieldGetSet=createFieldGetSetSRC(columnInfo,convertor);
            javaFields.add(javaFieldGetSet);
        }
        StringBuilder src=new StringBuilder();
        //生成package语句
        src.append("package "+ DBManager.getConf().getPackageName()+";\n");
        //生成import语句
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");
        //生成类声明语句
        src.append("public class "+StringUtil.underlineToBigCamel(tableInfo.getTname())+"{\n");
        //生成属性列表
        for (JavaFieldGetSet javaFieldGetSet:javaFields){
            src.append(javaFieldGetSet.getFieldInfo());
        }
        src.append("\n\n");
        //生成get方法列表
        for (JavaFieldGetSet javaFieldGetSet:javaFields){
            src.append(javaFieldGetSet.getGetInfo());
        }
        src.append("\n\n");
        //生成set方法列表
        for (JavaFieldGetSet javaFieldGetSet:javaFields){
            src.append(javaFieldGetSet.getSetInfo());
        }
        src.append("\n\n");
        //生成结束符
        src.append("}");
        return src.toString();
    }

    /**
     * 生成java文件
     * @param tableInfo 表信息
     * @param convertor 类型转换器
     */
    public static void createJavaPoFile(TableInfo tableInfo,TypeConvertor convertor){
        //获取源码
        String src=createJavaSrc(tableInfo,convertor);
        String srcPath=DBManager.getConf().getSrcPath()+"/";
        //将包名转换为文件名，然后和srcPath拼接
        String packagePath=DBManager.getConf().getPackageName().replace(".","/");
        System.out.println(srcPath+packagePath);
        File f=new File(srcPath+packagePath);
        if (!f.exists()){
            f.mkdirs();
        }
        BufferedWriter bw=null;
        try {
            //将源码写入文件
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
     * 更新表结构
     */
    public static void updateJavaPOFile(){
        Map<String,TableInfo>tables=TableContext.tables;
        for(TableInfo tableInfo:tables.values()){
            createJavaPoFile(tableInfo,new MySQLTypeConvertor());
        }
    }
    
    /**
     * 可以提个小需求
     * 根据传入的数据库自动生成所有的表的get set方法
     * @param args
     */
    public static void main(String[] args) {
    	//测试生成单个javaBean
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
    	//测试生成数据库所有tables --> 对应的javaBean
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