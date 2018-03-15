package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TTopic{
	private Integer commentCount;
	private String content;
	private Integer id;
	private String title;
	private Integer status;
	private Integer niceTopic;
	private Integer topicsUserId;
	private java.sql.Timestamp topicTime;
	private Integer topicsTypeId;
	private Integer integral;


	public Integer getCommentCount(){
		return commentCount;
	}
	public String getContent(){
		return content;
	}
	public Integer getId(){
		return id;
	}
	public String getTitle(){
		return title;
	}
	public Integer getStatus(){
		return status;
	}
	public Integer getNiceTopic(){
		return niceTopic;
	}
	public Integer getTopicsUserId(){
		return topicsUserId;
	}
	public java.sql.Timestamp getTopicTime(){
		return topicTime;
	}
	public Integer getTopicsTypeId(){
		return topicsTypeId;
	}
	public Integer getIntegral(){
		return integral;
	}


	public void setCommentCount(Integer commentCount){
		this.commentCount=commentCount;
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
	public void setStatus(Integer status){
		this.status=status;
	}
	public void setNiceTopic(Integer niceTopic){
		this.niceTopic=niceTopic;
	}
	public void setTopicsUserId(Integer topicsUserId){
		this.topicsUserId=topicsUserId;
	}
	public void setTopicTime(java.sql.Timestamp topicTime){
		this.topicTime=topicTime;
	}
	public void setTopicsTypeId(Integer topicsTypeId){
		this.topicsTypeId=topicsTypeId;
	}
	public void setIntegral(Integer integral){
		this.integral=integral;
	}


}