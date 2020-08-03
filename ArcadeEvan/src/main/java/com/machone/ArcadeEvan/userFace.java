package com.machone.ArcadeEvan;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface userFace extends CrudRepository<userprofiles, Integer>{

	Optional<userprofiles> findByUsername(String username);
	
	@Query(value="select * from userprofiles where username like '%' + :user + '%'", nativeQuery=true)
	Iterable<userprofiles> findByLikeUsername(@Param("user") String user);
	
	@Query(value="select top 1 with ties id, firstname, lastname, username, password, pic from userprofiles\r\n" + 
			"join (select BBWinRank.username as BBWuname, BBWinRank.uid as BBWuid, KCWinRank.username as KCWuname, KCWinRank.uid as KCWuid,\r\n" + 
			"	BBBests.username as BBBuname, BBBests.uid as BBBuid, KCBests.username as KCBuname, KCBests.uid as KCBuid, BBRank, KCRank, \r\n" + 
			"	BBBests.BBBestGame,KCBests.KCBestGame, (BBRank + KCRank + BBBests.BBBestGame + KCBests.KCBestGame)/4.0 as AvgRank \r\n" + 
			"	from (select Rank() over(order by wins desc, ratio desc) as BBrank, * \r\n" + 
			"			from leaderboard union select Rank() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,\r\n" + 
			"			theWay.uid,1,1,1,1,1,1,theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio \r\n" + 
			"			from ArcadeEvan.dbo.bblb1 as theWay where gid = 1) as BBWinRank\r\n" + 
			"	join (select Rank() over(order by wins desc, ratio desc) as KCrank, * from leaderboard \r\n" + 
			"			union select Rank() over(order by wins desc, ratio desc) as BBrank, theWay.id,theWay.gid,theWay.uid,1,1,1,1,1,1,\r\n" + 
			"			theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio \r\n" + 
			"			from ArcadeEvan.dbo.bblb1 as theWay where gid = 2) as KCWinRank\r\n" + 
			"	on KCWinRank.uid = BBWinRank.uid\r\n" + 
			"	join (select BBBestSolos.username, BBBestSolos.uid, min(BBBestSolos.BBSoloRank) as BBBestGame\r\n" + 
			"		from (select Rank() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, * from leaderboard \r\n" + 
			"				union select Rank() over(order by victory desc, lvl desc, livesremain desc, time) as BBSoloRank, theWay.id,1,\r\n" + 
			"				theWay.uid,theWay.victory,theWay.lvl,theWay.difficulty,theWay.time,1,theWay.livesremain,theWay.username,theWay.gamename,\r\n" + 
			"				theWay.version,1,1,1,1.0 from ArcadeEvan.dbo.bblb2 as theWay) as BBBestSolos\r\n" + 
			"				group by BBBestSolos.username, BBBestSolos.uid) as BBBests\r\n" + 
			"	on BBBests.uid = KCWinRank.uid\r\n" + 
			"	join (select KCBestSolos.username, KCBestSolos.uid, min(KCBestSolos.KCSoloRank) as KCBestGame \r\n" + 
			"		from (select Rank() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, * from leaderboard \r\n" + 
			"				union select Rank() over(order by victory desc, time asc, turnstaken asc) as KCSoloRank, theWay.id,1,theWay.uid,\r\n" + 
			"				theWay.victory,1,theWay.difficulty,theWay.time,theWay.turnstaken,1,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 \r\n" + 
			"				from ArcadeEvan.dbo.bblb3 as theWay) as KCBestSolos\r\n" + 
			"				group by KCBestSolos.username, KCBestSolos.uid) as KCBests\r\n" + 
			"	on KCBests.uid = KCWinRank.uid) as MasterRanks\r\n" + 
			"	on MasterRanks.BBBuid = userprofiles.id\r\n" + 
			"order by AvgRank", nativeQuery=true)
	Iterable<userprofiles> findArcadeMaster();
	
	
	
}
