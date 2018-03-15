package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TComment{
	private Integer commentsUserId;
	private String content;
	private Integer id;
	private Integer commentsTopicId;
	private Integer floor;
	private Integer status;
	private Integer integral;
	private java.sql.Timestamp commentTime;


	public Integer getCommentsUserId(){
		return commentsUserId;
	}
	public String getContent(){
		return content;
	}
	public Integer getId(){
		return id;
	}
	public Integer getCommentsTopicId(){
		return commentsTopicId;
	}
	public Integer getFloor(){
		return floor;
	}
	public Integer getStatus(){
		return status;
	}
	public Integer getIntegral(){
		return integral;
	}
	public java.sql.Timestamp getCommentTime(){
		return commentTime;
	}


	public void setCommentsUserId(Integer commentsUserId){
		this.commentsUserId=commentsUserId;
	}
	public void setContent(String content){
		this.content=content;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setCommentsTopicId(Integer commentsTopicId){
		this.commentsTopicId=commentsTopicId;
	}
	public void setFloor(Integer floor){
		this.floor=floor;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public void setIntegral(Integer integral){
		this.integral=integral;
	}
	public void setCommentTime(java.sql.Timestamp commentTime){
		this.commentTime=commentTime;
	}


}