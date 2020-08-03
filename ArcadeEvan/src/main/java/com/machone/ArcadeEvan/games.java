package com.machone.ArcadeEvan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class games {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	String gamename;
	int version;
	boolean hastimer;
	boolean hasturns;
	boolean haslvls;
	boolean hasdifficulty;
	boolean haslives;
	
	
	/**
	 * 
	 */
	public games() {
		super();
	}
	/**
	 * @param gamename
	 * @param version
	 * @param hastimer
	 * @param hasturns
	 * @param haslvls
	 * @param hasdifficulty
	 * @param haslives
	 */
	public games(String gamename, int version, boolean hastimer, boolean hasturns, boolean haslvls,
			boolean hasdifficulty, boolean haslives) {
		super();
		this.gamename = gamename;
		this.version = version;
		this.hastimer = hastimer;
		this.hasturns = hasturns;
		this.haslvls = haslvls;
		this.hasdifficulty = hasdifficulty;
		this.haslives = haslives;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isHastimer() {
		return hastimer;
	}
	public void setHastimer(boolean hastimer) {
		this.hastimer = hastimer;
	}
	public boolean isHasturns() {
		return hasturns;
	}
	public void setHasturns(boolean hasturns) {
		this.hasturns = hasturns;
	}
	public boolean isHaslvls() {
		return haslvls;
	}
	public void setHaslvls(boolean haslvls) {
		this.haslvls = haslvls;
	}
	public boolean isHasdifficulty() {
		return hasdifficulty;
	}
	public void setHasdifficulty(boolean hasdifficulty) {
		this.hasdifficulty = hasdifficulty;
	}
	public boolean isHaslives() {
		return haslives;
	}
	public void setHaslives(boolean haslives) {
		this.haslives = haslives;
	}
	
	
	
}
