package com.machone.ArcadeEvan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class msg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	int senduid;
	int receiveuid;
	String subject;
	String msgbody;
	String msgdate;
	String msgtime;
	
	/**
	 * @param senduid
	 * @param receiveuid
	 * @param subject
	 * @param msgbody
	 * @param msgdate
	 * @param msgtime
	 */
	public msg(int senduid, int receiveuid, String subject, String msgbody, String msgdate, String msgtime) {
		super();
		this.senduid = senduid;
		this.receiveuid = receiveuid;
		this.subject = subject;
		this.msgbody = msgbody;
		this.msgdate = msgdate;
		this.msgtime = msgtime;
	}

	/**
	 * 
	 */
	public msg() {
		super();
	}
	
	
	public String getMsgdate() {
		return msgdate;
	}

	public void setMsgdate(String msgdate) {
		this.msgdate = msgdate;
	}

	public String getMsgtime() {
		return msgtime;
	}

	public void setMsgtime(String msgtime) {
		this.msgtime = msgtime;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSenduid() {
		return senduid;
	}
	public void setSenduid(int senduid) {
		this.senduid = senduid;
	}
	public int getReceiveuid() {
		return receiveuid;
	}
	public void setReceiveuid(int receiveuid) {
		this.receiveuid = receiveuid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsgbody() {
		return msgbody;
	}
	public void setMsgbody(String msgbody) {
		this.msgbody = msgbody;
	}
	
	
}
