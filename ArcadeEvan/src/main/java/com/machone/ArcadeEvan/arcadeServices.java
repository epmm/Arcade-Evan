package com.machone.ArcadeEvan;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;



public class arcadeServices {

	@Autowired
	gamesFace theGames;
	
	@Autowired
	userFace theUsers;
	
	@Autowired
	resultsFace theResults;
	
	@Autowired
	msgFace theMsgs;
	
	@Autowired
	leaderboardFace theLeaderboards;
	
	@Autowired
	msgboxFace theMsgboxs;
	
	Iterable<msgbox> getUserMsgs(String username){
		return theMsgboxs.msgsByUsername(username);
	}
	
	Iterable<leaderboard> leaderboardRatiosWinsByGID(int gid){
		return theLeaderboards.sortByRatioAndWins(gid);
	}
	
	Iterable<leaderboard> userRatiosWinsByGID(int gid, String username){
		return theLeaderboards.sortByUserRatioAndWins(gid, username);
	}
	
	Iterable<leaderboard> leaderboardResultsByGID(int gid){
		if(gid == 1) {
			return theLeaderboards.top100ResultsBB();
		}
		else if(gid == 2) {
			return theLeaderboards.top100ResultsKC();
		}
		return null;
	}
	
	Iterable<leaderboard> userResults(int gid, String username){
		if(gid == 1) {
			return theLeaderboards.userResultsBB(username);
		}
		else if(gid == 2) {
			return theLeaderboards.userResultsKC(username);
		}
		return null;
	}
	
	Iterable<userprofiles> listUsers(){
		return theUsers.findAll();
	}
	
	Iterable<userprofiles> getMaster(){
		return theUsers.findArcadeMaster();
	}
	
	Iterable<userprofiles> getLikeUserInfo(String username){
		return theUsers.findByLikeUsername(username);
	}
	
	public userprofiles getUserInfo(String username) {
		Optional<userprofiles> aUser = theUsers.findByUsername(username);
		userprofiles loggedUser;
		if(aUser.isPresent()) {
			loggedUser = aUser.get();
		}
		else {
			loggedUser = new userprofiles();
			loggedUser.setUsername("Error");
		}
		return loggedUser;
	}
	
	userprofiles loginAttempt(String username, String password) {

		Optional<userprofiles> aUser = theUsers.findByUsername(username);
		userprofiles loggedUser;
		if(aUser.isPresent()) { //check to see if username exists
			loggedUser = aUser.get();
			if(!loggedUser.getPassword().equals(password)) { //check to see if password matches with username
				loggedUser.setUsername("Error");
			}
		}
		else {
			loggedUser = new userprofiles();
			loggedUser.setUsername("Error");
		}
		
		return loggedUser;
	}
	
	userprofiles newUser(String firstname, String lastname, String username, String password, String pic) {

		if(username.equals("Error") || username.equals("error")) { // doesn't allow users to have the username "error" or "Error" 
			userprofiles failed = new userprofiles();
			failed.setUsername("Error");
			return failed;
		}
		
		userprofiles mockUser = new userprofiles();
		mockUser.setFirstname(firstname);
		mockUser.setLastname(lastname);
		mockUser.setUsername(username);
		mockUser.setPassword(password);
		mockUser.setPic(pic);
		
		Optional<userprofiles> aUser = theUsers.findByUsername(username);
		
		if(aUser.isPresent()) { // checks if username exists already 
			userprofiles failed = new userprofiles();
			failed.setUsername("Error");
			return failed;
		}
		
		return theUsers.save(mockUser);
	}
	
	public userprofiles updateUser(String oldUsername, String oldPassword, String firstname, String lastname, String username, String password, String pic) {
		
		userprofiles upUser = loginAttempt(oldUsername, oldPassword);
		if(upUser.getUsername().equals("Error")) { // check if old credentials are correct
			upUser.setUsername("Error");
			return upUser;
		}
		
		if(!oldUsername.equals(username)) {  // checks if new username exists already and if it is the same as oldUsername
			Optional<userprofiles> aUser = theUsers.findByUsername(username);
			if(aUser.isPresent()) { 
				upUser.setUsername("Error");
				return upUser;
			}
		}
		
		upUser.setUsername(username);
		upUser.setPassword(password);
		upUser.setFirstname(firstname);
		upUser.setLastname(lastname);
		upUser.setPic(pic);
		this.theUsers.save(upUser);
		
		return upUser;
	}
	
	public msg newMsg(int senduid, int receiveuid, String subject, String msgbody, String msgdate, String msgtime) {  
		
		msg theMsg = new msg(senduid, receiveuid, subject, msgbody, msgdate, msgtime);
		return this.theMsgs.save(theMsg);
	}
	
	public results newResults(int gid, int uid, boolean victory, int lvl, int difficulty, int time, int turnstaken, int livesremain) { 
		 
		results theResult = new results(gid, uid, victory, lvl, difficulty, time, turnstaken, livesremain);
		return this.theResults.save(theResult);
	}

	public userprofiles deleteUser(String username, String password) {
		
		userprofiles deletedUser = loginAttempt(username, password);
		if(deletedUser.getUsername().equals("Error")) {
			return deletedUser;
		}
	
		this.theMsgs.deleteBySenduid(deletedUser.getId());                
		this.theMsgs.deleteByReceiveuid(deletedUser.getId());
		this.theResults.deleteByUid(deletedUser.getId());                 
		this.theUsers.deleteById(deletedUser.getId());
		deletedUser.setUsername("goner");
		return deletedUser;
	}
	
}
