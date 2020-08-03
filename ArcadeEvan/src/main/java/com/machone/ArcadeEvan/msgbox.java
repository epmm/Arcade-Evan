package com.machone.ArcadeEvan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class msgbox {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	int senduid;
	int receiveuid;
	String sender;
	String receiver;
	String subject;
	String msgbody;
	String msgdate;
	String msgtime;
	
	/**
	 * 
	 */
	public msgbox() {
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
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
