

select top 100 * from leaderboard union select theWay.id,1,theWay.uid,theWay.victory,theWay.lvl,theWay.difficulty,theWay.time,1,theWay.livesremain,theWay.username,theWay.gamename,theWay.version,1,1,1,1.0 from ArcadeEvan.dbo.bblb2 as theWay order by victory desc, lvl desc, livesremain desc