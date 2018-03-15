package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class THelp{
	private String content;
	private Integer id;
	private String title;
	private java.sql.Timestamp newtime;


	public String getContent(){
		return content;
	}
	public Integer getId(){
		return id;
	}
	public String getTitle(){
		return title;
	}
	public java.sql.Timestamp getNewtime(){
		return newtime;
	}


	public void setContent(String content){
		this.content=content;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public void setNewtime(java.sql.Timestamp newtime){
		this.newtime=newtime;
	}


}