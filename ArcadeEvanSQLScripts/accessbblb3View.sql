

select top 100 * from leaderboard union select theWay.id,1,theWay.uid,theWay.victory,1,theWay.difficulty,theWay.time,theWay.turnstaken,1,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 from ArcadeEvan.dbo.bblb3 as theWay order by victory desc, time asc, turnstaken asc