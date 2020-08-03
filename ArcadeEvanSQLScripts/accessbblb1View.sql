

select * from leaderboard union select theWay.id,theWay.gid,theWay.uid,1,1,1,1,1,1,theWay.username,theWay.gamename,1,theWay.wins,theWay.losses,theWay.totalGames,theWay.ratio from ArcadeEvan.dbo.bblb1 as theWay order by ratio desc, wins desc