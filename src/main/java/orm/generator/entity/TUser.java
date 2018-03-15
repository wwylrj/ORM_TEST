package orm.generator.entity;
import java.sql.*;
import java.util.*;

public class TUser{
	private Integer commentCount;
	private String sex;
	private java.sql.Timestamp registerTime;
	private Integer roleId;
	private Integer status;
	private Integer topicCount;
	private Integer clock;
	private String nickname;
	private String profession;
	private String password;
	private Integer integral;
	private Integer id;
	private String picture;
	private String username;
	private String email;
	private Integer usersGradeId;
	private Integer gradeIntegal;
	private String comeFrom;
	private String introduction;


	public Integer getCommentCount(){
		return commentCount;
	}
	public String getSex(){
		return sex;
	}
	public java.sql.Timestamp getRegisterTime(){
		return registerTime;
	}
	public Integer getRoleId(){
		return roleId;
	}
	public Integer getStatus(){
		return status;
	}
	public Integer getTopicCount(){
		return topicCount;
	}
	public Integer getClock(){
		return clock;
	}
	public String getNickname(){
		return nickname;
	}
	public String getProfession(){
		return profession;
	}
	public String getPassword(){
		return password;
	}
	public Integer getIntegral(){
		return integral;
	}
	public Integer getId(){
		return id;
	}
	public String getPicture(){
		return picture;
	}
	public String getUsername(){
		return username;
	}
	public String getEmail(){
		return email;
	}
	public Integer getUsersGradeId(){
		return usersGradeId;
	}
	public Integer getGradeIntegal(){
		return gradeIntegal;
	}
	public String getComeFrom(){
		return comeFrom;
	}
	public String getIntroduction(){
		return introduction;
	}


	public void setCommentCount(Integer commentCount){
		this.commentCount=commentCount;
	}
	public void setSex(String sex){
		this.sex=sex;
	}
	public void setRegisterTime(java.sql.Timestamp registerTime){
		this.registerTime=registerTime;
	}
	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public void setTopicCount(Integer topicCount){
		this.topicCount=topicCount;
	}
	public void setClock(Integer clock){
		this.clock=clock;
	}
	public void setNickname(String nickname){
		this.nickname=nickname;
	}
	public void setProfession(String profession){
		this.profession=profession;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public void setIntegral(Integer integral){
		this.integral=integral;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setPicture(String picture){
		this.picture=picture;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public void setUsersGradeId(Integer usersGradeId){
		this.usersGradeId=usersGradeId;
	}
	public void setGradeIntegal(Integer gradeIntegal){
		this.gradeIntegal=gradeIntegal;
	}
	public void setComeFrom(String comeFrom){
		this.comeFrom=comeFrom;
	}
	public void setIntroduction(String introduction){
		this.introduction=introduction;
	}


}