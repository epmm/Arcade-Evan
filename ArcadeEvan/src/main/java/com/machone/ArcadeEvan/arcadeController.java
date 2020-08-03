package com.machone.ArcadeEvan;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class arcadeController extends arcadeServices{

	/********START GET**************************************************************************************************************************************************************/
	
	@GetMapping("/user/all")
	Iterable<userprofiles> listUser(){
		return this.listUsers();
	}
	
	@GetMapping("/user/byUsername/{username}")
	public userprofiles getUser(@PathVariable String username){
		return this.getUserInfo(username);
	}
	
	@GetMapping("/user/likeUsername/{username}")
	Iterable<userprofiles> getLikeUser(@PathVariable String username){
		return this.getLikeUserInfo(username);
	}
	
	@GetMapping("/user/ArcadeMaster/")
	Iterable<userprofiles> getArcadeMaster(){
		return this.getMaster();
	}
	
	@GetMapping("/msgbox/get/{username}")
	Iterable<msgbox> getMsgs(@PathVariable String username){  // use Angular to filter based on date and time 
		return this.getUserMsgs(username);
	}
	
	@GetMapping("/user/login/")
	public userprofiles userLogin(@RequestParam String username, @RequestParam String password){
		return this.loginAttempt(username, password);
	}
	
	@GetMapping("/leaderboard/byRatioAndWins/{gid}")
	Iterable<leaderboard> listLeaderboardByGID(@PathVariable int gid){
		return this.leaderboardRatiosWinsByGID(gid);
	}
	
	@GetMapping("/leaderboard/byUserRatioAndWins/{gid}/{username}")
	Iterable<leaderboard> listLeaderboardByUser(@PathVariable int gid, @PathVariable String username){
		return this.userRatiosWinsByGID(gid, username);
	}
	
	@GetMapping("/leaderboard/results/{gid}")
	Iterable<leaderboard> listLeaderboardResulsByGID(@PathVariable int gid){
		return this.leaderboardResultsByGID(gid);
	}
	
	@GetMapping("/leaderboard/userResults/{gid}/{username}")
	Iterable<leaderboard> listUserResulsByGID(@PathVariable int gid, @PathVariable String username){
		return this.userResults(gid, username);
	}
	
	/*-------END GET---------------------------------------------------------------------------------------------------------------*/
	
	
	
	/********START POST*************************************************************************************************************/
	
	@PostMapping("/user/new/")
	public userprofiles createUser(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String username, @RequestParam String password, @RequestParam String pic) {
		return this.newUser(firstname, lastname, username, password, pic);
	}
	
	@PostMapping("/msg/new/")
	public msg createMsg(@RequestParam int senduid, @RequestParam int receiveuid, @RequestParam String subject, @RequestParam String msgbody, @RequestParam String msgdate, @RequestParam String msgtime) {
		return this.newMsg(senduid, receiveuid, subject, msgbody, msgdate, msgtime);
	}
	
	@PostMapping("/results/new/")
	public results createResults(@RequestParam int gid, @RequestParam int uid, @RequestParam boolean victory, @RequestParam int lvl, @RequestParam int difficulty, @RequestParam int time, @RequestParam int turnstaken, @RequestParam int livesremain) {
		return this.newResults(gid, uid, victory, lvl, difficulty, time, turnstaken, livesremain);
	}
	
	/*-------END POST-------------------------------------------------------------------------------------------------------------------*/
	
	/********START PUT*******************************************************************************************************************/
	
	@PutMapping("/user/update/")   
	public userprofiles updateUserprofiles(@RequestParam String oldUsername, @RequestParam String oldPassword, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String username, @RequestParam String password, @RequestParam String pic) {
		return this.updateUser(oldUsername, oldPassword, firstname, lastname, username, password, pic);
	}
	
	
	/*-------END PUT--------------------------------------------------------------------------------------------------------------------*/
	
	/********START DELETE****************************************************************************************************************/
	
	@DeleteMapping("/user/delete/")
	public userprofiles deleteUserProfiles(@RequestParam String username, @RequestParam String password) {
		return this.deleteUser(username, password);
	}
	
	
	/*-------END DELETE-----------------------------------------------------------------------------------------------------------------*/
}
