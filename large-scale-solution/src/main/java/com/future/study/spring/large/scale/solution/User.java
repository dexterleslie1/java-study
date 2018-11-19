package com.future.study.spring.large.scale.solution;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Dexterleslie.Chan
 */
@Entity
@Table(name="t_user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -455392667421971562L;

	@Id
	private int id;
	private String nickname;
	private String phone;
	private String password;
	private int sex;
	private Date createTime;
	private String ticket;
	private String email;
	private byte[] avatar;
	private String loginname;
	private int isLoginnameReset;
	private byte[] largeAvatar;
	private int avatarVersion;
	
	public byte[] getLargeAvatar() {
		return largeAvatar;
	}
	public void setLargeAvatar(byte[] largeAvatar) {
		this.largeAvatar = largeAvatar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getAvatar() {
		return avatar;
	}
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public int getIsLoginnameReset() {
		return isLoginnameReset;
	}
	public void setIsLoginnameReset(int isLoginnameReset) {
		this.isLoginnameReset = isLoginnameReset;
	}
	public int getAvatarVersion() {
		return avatarVersion;
	}
	public void setAvatarVersion(int avatarVersion) {
		this.avatarVersion = avatarVersion;
	}
}
