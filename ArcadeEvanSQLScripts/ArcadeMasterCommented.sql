
select id,firstname, lastname,username,password,pic from userprofiles
/* ^^ grabs the user attributes of the Arcade Master(s) */
/* vv  joins the subquery (MasterRanks) composed of subqueries that finds each user's AvgRank */
join (select BBWinRank.username as BBWuname, BBWinRank.uid as BBWuid, KCWinRank.username as KCWuname, KCWinRank.uid as KCWuid,
	BBBests.username as BBBuname, BBBests.uid as BBBuid, KCBests.username as KCBuname, KCBests.uid as KCBuid, BBRank, KCRank, 
	BBBests.BBBestGame,KCBests.KCBestGame, (BBRank + KCRank + BBBests.BBBestGame + KCBests.KCBestGame)/4.0 as AvgRank 
	/* ^^ grabs username, uid, and rank from each of the 4 leaderboard views then calculates the AvgRank per user */

	from (select ROW_NUMBER() over(order by wins desc, ratio desc) as BBrank, * 
			from leaderboard union select ROW_NUMBER() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,
			theWay.uid,1,1,1,1,1,1,theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio 
			from ArcadeEvan.dbo.bblb1 as theWay where gid = 1) as BBWinRank
			/* ^^ query to get user wins/ratio leaderboard for BB and adds a user ranking column */

	join (select ROW_NUMBER() over(order by wins desc, ratio desc) as KCrank, * from leaderboard 
			union select ROW_NUMBER() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,theWay.uid,1,1,1,1,1,1,
			theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio 
			from ArcadeEvan.dbo.bblb1 as theWay where gid = 2) as KCWinRank
			/* ^^ query to get user wins/ratio leaderboard for KC and adds a user ranking column */
	on KCWinRank.uid = BBWinRank.uid
	
	join (select BBBestSolos.username, BBBestSolos.uid, min(BBBestSolos.BBSoloRank) as BBBestGame
		from (select ROW_NUMBER() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, * from leaderboard 
				union select ROW_NUMBER() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, theWay.id,1,
				theWay.uid,theWay.victory,theWay.lvl,theWay.difficulty,theWay.time,1,theWay.livesremain,theWay.username,theWay.gamename,
				theWay.version,1,1,1,1.0 from ArcadeEvan.dbo.bblb2 as theWay) as BBBestSolos
				group by BBBestSolos.username, BBBestSolos.uid) as BBBests
				/* ^^ query to get each user's best game of BB from the all individual results leaderboard and adds a ranking column */
	on BBBests.uid = KCWinRank.uid
	
	join (select KCBestSolos.username, KCBestSolos.uid, min(KCBestSolos.KCSoloRank) as KCBestGame 
		from (select ROW_NUMBER() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, * from leaderboard 
				union select ROW_NUMBER() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, theWay.id,1,theWay.uid,
				theWay.victory,1,theWay.difficulty,theWay.time,theWay.turnstaken,1,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 
				from ArcadeEvan.dbo.bblb3 as theWay) as KCBestSolos
				group by KCBestSolos.username, KCBestSolos.uid) as KCBests
				/* ^^ query to get each user's best game of KC from the all individual results leaderboard and adds a ranking column */
	on KCBests.uid = KCWinRank.uid) as MasterRanks
	on MasterRanks.BBBuid = userprofiles.id

where AvgRank = (select min(AvgRank) from /* <-- in case there is a tie between users for lowest AvgRank, this WHERE clause grabs all users with the lowest average rank
													by running the same subquery as above but only selecting the minimum average rank among the users for comparison */
	(select BBWinRank.username as BBWuname, BBWinRank.uid as BBWuid, KCWinRank.username as KCWuname, KCWinRank.uid as KCWuid,
	BBBests.username as BBBuname, BBBests.uid as BBBuid, KCBests.username as KCBuname, KCBests.uid as KCBuid, BBRank, KCRank, 
	BBBests.BBBestGame,KCBests.KCBestGame, (BBRank + KCRank + BBBests.BBBestGame + KCBests.KCBestGame)/4.0 as AvgRank 
	from (select ROW_NUMBER() over(order by wins desc, ratio desc) as BBrank, * 
			from leaderboard union select ROW_NUMBER() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,
			theWay.uid,1,1,1,1,1,1,theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio 
			from ArcadeEvan.dbo.bblb1 as theWay where gid = 1) as BBWinRank
	
	join (select ROW_NUMBER() over(order by wins desc, ratio desc) as KCrank, * from leaderboard 
			union select ROW_NUMBER() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,theWay.uid,1,1,1,1,1,1,
			theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio 
			from ArcadeEvan.dbo.bblb1 as theWay where gid = 2) as KCWinRank
	on KCWinRank.uid = BBWinRank.uid
	
	join (select BBBestSolos.username, BBBestSolos.uid, min(BBBestSolos.BBSoloRank) as BBBestGame
		from (select ROW_NUMBER() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, * from leaderboard 
				union select ROW_NUMBER() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, theWay.id,1,
				theWay.uid,theWay.victory,theWay.lvl,theWay.difficulty,theWay.time,1,theWay.livesremain,theWay.username,theWay.gamename,
				theWay.version,1,1,1,1.0 from ArcadeEvan.dbo.bblb2 as theWay) as BBBestSolos
				group by BBBestSolos.username, BBBestSolos.uid) as BBBests
	on BBBests.uid = KCWinRank.uid
	
	join (select KCBestSolos.username, KCBestSolos.uid, min(KCBestSolos.KCSoloRank) as KCBestGame 
		from (select ROW_NUMBER() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, * from leaderboard 
				union select ROW_NUMBER() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, theWay.id,1,theWay.uid,
				theWay.victory,1,theWay.difficulty,theWay.time,theWay.turnstaken,1,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 
				from ArcadeEvan.dbo.bblb3 as theWay) as KCBestSolos
				group by KCBestSolos.username, KCBestSolos.uid) as KCBests
	on KCBests.uid = KCWinRank.uid) as MasterRanks)

