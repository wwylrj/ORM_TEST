package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TCategory{
	private Integer id;
	private String name;
	private Integer countTopics;
	private Integer countComments;


	public Integer getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public Integer getCountTopics(){
		return countTopics;
	}
	public Integer getCountComments(){
		return countComments;
	}


	public void setId(Integer id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setCountTopics(Integer countTopics){
		this.countTopics=countTopics;
	}
	public void setCountComments(Integer countComments){
		this.countComments=countComments;
	}


}