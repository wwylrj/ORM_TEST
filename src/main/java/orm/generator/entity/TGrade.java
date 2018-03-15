package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TGrade{
	private Integer id;
	private String honor;


	public Integer getId(){
		return id;
	}
	public String getHonor(){
		return honor;
	}


	public void setId(Integer id){
		this.id=id;
	}
	public void setHonor(String honor){
		this.honor=honor;
	}


}