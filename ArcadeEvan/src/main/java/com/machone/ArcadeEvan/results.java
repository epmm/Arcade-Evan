package com.machone.ArcadeEvan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class results {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	int gid;
	int uid;
	boolean victory;
	int lvl;
	int difficulty;
	int time;
	int turnstaken;
	int livesremain;
	
	
	
	/**
	 * 
	 */
	public results() {
		super();
	}
	/**
	 * @param gid
	 * @param uid
	 * @param victory
	 * @param lvl
	 * @param difficulty
	 * @param time
	 * @param turnstaken
	 * @param livesremain
	 */
	public results(int gid, int uid, boolean victory, int lvl, int difficulty, int time, int turnstaken, int livesremain) {
		super();
		this.gid = gid;
		this.uid = uid;
		this.victory = victory;
		this.lvl = lvl;
		this.difficulty = difficulty;
		this.time = time;
		this.turnstaken = turnstaken;
		this.livesremain = livesremain;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public boolean isVictory() {
		return victory;
	}
	public void setVictory(boolean victory) {
		this.victory = victory;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getTurnstaken() {
		return turnstaken;
	}
	public void setTurnstaken(int turnstaken) {
		this.turnstaken = turnstaken;
	}
	public int getLivesremain() {
		return livesremain;
	}
	public void setLivesremain(int livesremain) {
		this.livesremain = livesremain;
	}
	
	
}
