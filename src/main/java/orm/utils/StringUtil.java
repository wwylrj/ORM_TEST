package orm.utils;

public class StringUtil {
	/**
	 * 下划线转为大驼峰命
	 */
   public static String underlineToBigCamel(String str){
	   return underlineToSmallCamel(str.toUpperCase().substring(0,1)+str.substring(1));
   }
	
	public static String underlineToSmallCamel(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}

		int length = str.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c == '_' && ++i < length) {//S1= test_new   S2= testN
				sb.append(Character.toUpperCase(str.charAt(i)));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	//数据库的名字和字段的名字都是采用下划线分割法，但字段名是采用小驼峰，所以要进行一次转换
	public static String camelToUnderline(String str){
		if(str == null || "".equals(str)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		
		sb.append(str.substring(0,1).toLowerCase());
		for (int i = 1;i<str.length();i++){
			String s = str.substring(i, i+1);
			if(s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))){
				sb.append("_");
			}
				//其他字符全部转成小写
			
			sb.append(s.toLowerCase());
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String s = "test_new";
		String s1=StringUtil.underlineToSmallCamel(s);
		String s2=StringUtil.underlineToBigCamel(s);
		System.out.println(s1);//testNew
		System.out.println(s2);//TestNew
		String s3= StringUtil.camelToUnderline(s1);
		System.out.println(s3);
	}
}
