package orm.generator;

public class JavaFieldGetSet {
    /**
     * ����Դ����Ϣ���磺private int id;
     */
     private String fieldInfo;
     
     /**
      * get����Դ����Ϣ���磺public int getId();
      */
     private String getInfo;
     
     /**
      * set����Դ����Ϣ���� �� public void setId(int id);
      */
     private String setInfo;
     
     public JavaFieldGetSet(){
    	 
     }
     
     public JavaFieldGetSet(String fieldInfo,String getInfo,String
    		 setInfo){
    	 this.fieldInfo = fieldInfo;
    	 this.getInfo = getInfo;
    	 this.setInfo = setInfo;
     }

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getGetInfo() {
		return getInfo;
	}

	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}

	public String getSetInfo() {
		return setInfo;
	}

	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}

	@Override
	public String toString() {
		return "JavaFieldGetSet [fieldInfo=" + fieldInfo + ", getInfo="
				+ getInfo + ", setInfo=" + setInfo + "]";
	}
     
     
}
