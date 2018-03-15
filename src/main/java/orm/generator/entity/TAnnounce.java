package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TAnnounce{
	private Integer id;
	private String title;
	private String announcement;
	private java.sql.Timestamp newtime;


	public Integer getId(){
		return id;
	}
	public String getTitle(){
		return title;
	}
	public String getAnnouncement(){
		return announcement;
	}
	public java.sql.Timestamp getNewtime(){
		return newtime;
	}


	public void setId(Integer id){
		this.id=id;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public void setAnnouncement(String announcement){
		this.announcement=announcement;
	}
	public void setNewtime(java.sql.Timestamp newtime){
		this.newtime=newtime;
	}


}