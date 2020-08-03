
create table [dbo].[userprofiles](
	[id] [int] primary key IDENTITY(1,1) NOT NULL,
	[firstname] [varchar](max) NULL,
	[lastname] [varchar](max) NULL,
	[username] [varchar](max) NULL,
	[password] [varchar](max) NULL,
	[pic] [varchar](max) NULL)

go

create table [dbo].[games](
	[id] [int] primary key IDENTITY(1,1) NOT NULL,
	[gamename] [varchar](max) NULL,
	[haslvls] [bit] NULL,
	[hasdifficulty] [bit] NULL,
	[hasturns] [bit] NULL,
	[hastimer] [bit] NULL,
	[haslives] [bit] NULL,
	[version] [int] NULL)

go

create table [dbo].[results](
	[id] [int] primary key IDENTITY(1,1) NOT NULL,
	[gid] [int] NOT NULL,
	[uid] [int] NOT NULL,
	[victory] [bit] NULL,
	[lvl] [int] NULL,
	[difficulty] [int] NULL,
	[time] [int] NULL,
	[turnstaken] [int] NULL,
	[livesremain] [int] NULL)

go

create table [dbo].[msg](
	[id] [int] primary key IDENTITY(1,1) NOT NULL,
	[senduid] [int] NOT NULL,
	[receiveuid] [int] NOT NULL,
	[subject] [varchar](max) NULL,
	[msgbody] [varchar](max) NULL,
	[msgdate] [varchar](max) NULL,
	[msgtime] [varchar](max) NULL)

go

create table [dbo].[leaderboard](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[gid] [int] NOT NULL,
	[uid] [int] NOT NULL,
	[victory] [bit] NULL,
	[lvl] [int] NULL,
	[difficulty] [int] NULL,
	[time] [int] NULL,
	[turnstaken] [int] NULL,
	[livesremain] [int] NULL,
	[username] [varchar](max) NULL,
	[gamename] [varchar](max) NULL,
	[version] [int] NULL,
	[wins] [int] NULL,
	[losses] [int] NULL,
	[totalgames] [int] NULL,
	[ratio] [float] NULL)

go

create table [dbo].[msgbox](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[senduid] [int] NOT NULL,
	[receiveuid] [int] NOT NULL,
	[sender] [varchar](max) NULL,
	[receiver] [varchar](max) NULL,
	[subject] [varchar](max) NULL,
	[msgbody] [varchar](max) NULL,
	[msgdate] [varchar](max) NULL,
	[msgtime] [varchar](max) NULL)


go

create view bblb1 as 

select ROW_NUMBER() over(order by username) as id, id as uid,gid,username, gamename,wins,losses,totalGames,
	round(cast(wins as float)/nullif(cast(totalGames as float),0),3) as ratio
from (SELECT u.id, g.id as gid, g.gamename, u.username,
    count(*) AS totalGames,
    sum(case when victory = 1 then 1 else 0 end) AS wins,
    sum(case when victory = 0 then 1 else 0 end) AS losses
	FROM results
	inner join games as g on results.gid = g.id
	inner join userprofiles as u on results.uid = u.id
	GROUP BY u.id, g.id,username, gamename)  as sub
	/*order by ratio desc, wins desc*/

go

create view bblb2 as

select ROW_NUMBER() over(order by username) as id,u.id as uid, u.username, g.gamename, victory, time, lvl, livesremain, g.version, difficulty from results
inner join games as g on results.gid = g.id
inner join userprofiles as u on results.uid = u.id
where gid = 1
/*order by victory desc, lvl desc, livesremain desc*/

go

create view bblb3 as

select ROW_NUMBER() over(order by username) as id,u.id as uid, u.username, g.gamename, victory, time, turnstaken, g.version, difficulty from results
inner join games as g on results.gid = g.id
inner join userprofiles as u on results.uid = u.id
where gid = 2
/*order by victory desc, time asc, turnstaken asc*/

go

create view msg1 as 

select ROW_NUMBER() over(order by id) as id, senduid, receiveuid, sender, receiver, subject, msgbody, msgdate, msgtime from
	(select msg.id, senduid, receiveuid, subject, msgbody, msgdate, msgtime, usend.username as sender, urec.username as receiver from msg
	inner join userprofiles as usend on msg.senduid = usend.id
	inner join userprofiles as urec on msg.receiveuid = urec.id) as sub

go
