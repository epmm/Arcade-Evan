package com.machone.ArcadeEvan;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface leaderboardFace extends CrudRepository<leaderboard,Integer>{

	
	@Query(value="select * from leaderboard union select "
			+ "theWay.id,theWay.gid,theWay.uid,1,1,1,1,1,1,theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio "
			+ "from ArcadeEvan.dbo.bblb1 as theWay where gid =:gid order by wins desc, ratio desc", nativeQuery=true)
	Iterable<leaderboard> sortByRatioAndWins(@Param("gid") int gid);
	
	@Query(value="select * from leaderboard union select "
			+ "theWay.id,theWay.gid,theWay.uid,1,1,1,1,1,1,theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio "
			+ "from ArcadeEvan.dbo.bblb1 as theWay where gid =:gid and username like :user + '%' order by wins desc, ratio desc", nativeQuery=true)
	Iterable<leaderboard> sortByUserRatioAndWins(@Param("gid") int gid, @Param("user") String user);

	@Query(value="select top 100 * from leaderboard union select "
			+ "theWay.id,1,theWay.uid,theWay.victory,theWay.lvl,theWay.difficulty,theWay.time,1,theWay.livesremain,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 "
			+ "from ArcadeEvan.dbo.bblb2 as theWay order by victory desc, lvl desc, livesremain desc, time asc", nativeQuery=true)
	Iterable<leaderboard> top100ResultsBB();
	
	@Query(value="select top 100 * from leaderboard union select "
			+ "theWay.id,1,theWay.uid,theWay.victory,1,theWay.difficulty,theWay.time,theWay.turnstaken,1,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 "
			+ "from ArcadeEvan.dbo.bblb3 as theWay order by victory desc, time asc, turnstaken asc", nativeQuery=true)
	Iterable<leaderboard> top100ResultsKC();
	
	@Query(value="select * from leaderboard union select "
			+ "theWay.id,1,theWay.uid,theWay.victory,theWay.lvl,theWay.difficulty,theWay.time,1,theWay.livesremain,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 "
			+ "from ArcadeEvan.dbo.bblb2 as theWay where username like :user + '%' order by username, victory desc, lvl desc, livesremain desc, time asc", nativeQuery=true)
	Iterable<leaderboard> userResultsBB(@Param("user") String user);
	
	@Query(value="select * from leaderboard union select "
			+ "theWay.id,1,theWay.uid,theWay.victory,1,theWay.difficulty,theWay.time,theWay.turnstaken,1,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 "
			+ "from ArcadeEvan.dbo.bblb3 as theWay where username like :user + '%' order by username, victory desc, time asc, turnstaken asc", nativeQuery=true)
	Iterable<leaderboard> userResultsKC(@Param("user") String user);

}
