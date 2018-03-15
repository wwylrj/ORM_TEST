package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TNew{
	private Integer id;
	private Integer newsTopicId;
	private java.sql.Timestamp newTime;
	private Integer status;
	private Integer newsCommentUserId;


	public Integer getId(){
		return id;
	}
	public Integer getNewsTopicId(){
		return newsTopicId;
	}
	public java.sql.Timestamp getNewTime(){
		return newTime;
	}
	public Integer getStatus(){
		return status;
	}
	public Integer getNewsCommentUserId(){
		return newsCommentUserId;
	}


	public void setId(Integer id){
		this.id=id;
	}
	public void setNewsTopicId(Integer newsTopicId){
		this.newsTopicId=newsTopicId;
	}
	public void setNewTime(java.sql.Timestamp newTime){
		this.newTime=newTime;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public void setNewsCommentUserId(Integer newsCommentUserId){
		this.newsCommentUserId=newsCommentUserId;
	}


}