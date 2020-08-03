/* this is a shortened/improved version of ArcadeMasterCommented.sql */

select top 1 with ties id, firstname, lastname, username, password, pic from userprofiles
/* vv  joins the subquery (MasterRanks) composed of subqueries that finds each user's AvgRank */
join (select BBWinRank.username as BBWuname, BBWinRank.uid as BBWuid, KCWinRank.username as KCWuname, KCWinRank.uid as KCWuid,
	BBBests.username as BBBuname, BBBests.uid as BBBuid, KCBests.username as KCBuname, KCBests.uid as KCBuid, BBRank, KCRank, 
	BBBests.BBBestGame,KCBests.KCBestGame, (BBRank + KCRank + BBBests.BBBestGame + KCBests.KCBestGame)/4.0 as AvgRank 
	/* ^^ grabs username, uid, and rank from each of the 4 leaderboard views then calculates the AvgRank per user */

	from (select Rank() over(order by wins desc, ratio desc) as BBrank, * 
			from leaderboard union select Rank() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,
			theWay.uid,1,1,1,1,1,1,theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio 
			from ArcadeEvan.dbo.bblb1 as theWay where gid = 1) as BBWinRank
			/* ^^ query to get user wins/ratio leaderboard for BB and adds a user ranking column */

	join (select Rank() over(order by wins desc, ratio desc) as KCrank, * from leaderboard 
			union select Rank() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,theWay.uid,1,1,1,1,1,1,
			theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio 
			from ArcadeEvan.dbo.bblb1 as theWay where gid = 2) as KCWinRank
			/* ^^ query to get user wins/ratio leaderboard for KC and adds a user ranking column */
	on KCWinRank.uid = BBWinRank.uid
	
	join (select BBBestSolos.username, BBBestSolos.uid, min(BBBestSolos.BBSoloRank) as BBBestGame
		from (select Rank() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, * from leaderboard 
				union select Rank() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, theWay.id,1,
				theWay.uid,theWay.victory,theWay.lvl,theWay.difficulty,theWay.time,1,theWay.livesremain,theWay.username,theWay.gamename,
				theWay.version,1,1,1,1.0 from ArcadeEvan.dbo.bblb2 as theWay) as BBBestSolos
				group by BBBestSolos.username, BBBestSolos.uid) as BBBests
				/* ^^ query to get each user's best game of BB from the all individual results leaderboard and adds a ranking column */
	on BBBests.uid = KCWinRank.uid
	
	join (select KCBestSolos.username, KCBestSolos.uid, min(KCBestSolos.KCSoloRank) as KCBestGame 
		from (select Rank() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, * from leaderboard 
				union select Rank() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, theWay.id,1,theWay.uid,
				theWay.victory,1,theWay.difficulty,theWay.time,theWay.turnstaken,1,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 
				from ArcadeEvan.dbo.bblb3 as theWay) as KCBestSolos
				group by KCBestSolos.username, KCBestSolos.uid) as KCBests
				/* ^^ query to get each user's best game of KC from the all individual results leaderboard and adds a ranking column */
	on KCBests.uid = KCWinRank.uid) as MasterRanks
	on MasterRanks.BBBuid = userprofiles.id
order by AvgRank