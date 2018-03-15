package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TType{
	private Integer id;
	private String name;
	private Integer typesCategoryId;
	private Integer countTopics;
	private Integer countComments;
	private Integer isAdminType;


	public Integer getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public Integer getTypesCategoryId(){
		return typesCategoryId;
	}
	public Integer getCountTopics(){
		return countTopics;
	}
	public Integer getCountComments(){
		return countComments;
	}
	public Integer getIsAdminType(){
		return isAdminType;
	}


	public void setId(Integer id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setTypesCategoryId(Integer typesCategoryId){
		this.typesCategoryId=typesCategoryId;
	}
	public void setCountTopics(Integer countTopics){
		this.countTopics=countTopics;
	}
	public void setCountComments(Integer countComments){
		this.countComments=countComments;
	}
	public void setIsAdminType(Integer isAdminType){
		this.isAdminType=isAdminType;
	}


}