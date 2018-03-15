package orm.utils;

import orm.conver.TypeConvertor;
import orm.conver.impl.MySQLTypeConvertor;
import orm.generator.JavaFieldGetSet;
import orm.tableInfo.ColumnInfo;

public class TestJavaBeanGeneratorUtils {
     
	public static void main(String[] args) {
		ColumnInfo c = new ColumnInfo();
		c.setDataType("varchar");
		c.setKeyType(1);
		c.setName("id");
		TypeConvertor convertor = new MySQLTypeConvertor();
		JavaFieldGetSet f=JavaBeanGeneratorUtil.createFieldGetSetSRC(c, convertor);
		System.out.println(f.toString());
	}
}
