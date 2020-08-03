var myArcade = angular.module('arcadeFront', ["ngRoute"]);


    myArcade.config(function($routeProvider){
        $routeProvider
        .when("/", {
            templateUrl : "templates/ArcHome.htm"
        })
        .when("/BB", {
            templateUrl : "templates/ArcBB.htm"
        })
        .when("/KC", {
            templateUrl : "templates/ArcKC.htm"
        })
        .when("/Leaderboards", {
            templateUrl : "templates/ArcLeaderboards.htm"
        })
        .when("/ArcAbout", {
            templateUrl : "templates/ArcAbout.htm"
        })
        .when("/ArcLogSign", {
            templateUrl : "templates/ArcLogSign.htm"
        })
        .when("/ArcUserPage", {
            templateUrl : "templates/ArcUserPage.htm"
        })
        .when("/ArcLogout", {
            templateUrl : "templates/ArcLogout.htm"
        })
        .otherwise({
            templateUrl : "templates/ArcHome.htm"
        });
    });


myArcade.controller('arcadeCon', function($scope, $http, $location){
    
    $scope.makeRanks = function(index, type) { //type 1 = user aggregate, type 2 = individual BB, type 3 = individual KC
        if(type == 1){
            var wins = $scope.leaderboard[index].wins;
            var ratio = $scope.leaderboard[index].ratio;

            $scope.tieCheck = function(index, wins, ratio){
                if(index == 0)
                    return index;
                else if(wins == $scope.leaderboard[index - 1].wins && ratio == $scope.leaderboard[index - 1].ratio)
                    return $scope.tieCheck(index-1, wins, ratio);
                return index;
            };

            return $scope.tieCheck(index, wins, ratio);
        }
        if(type == 2){
            var time = $scope.leaderboard[index].time;
            var livesremain = $scope.leaderboard[index].livesremain;
            var lvl = $scope.leaderboard[index].lvl;

            $scope.tieCheck = function(index, time, livesremain, lvl){
                if(index == 0)
                    return index;
                else if(time == $scope.leaderboard[index - 1].time && livesremain == $scope.leaderboard[index - 1].livesremain && lvl == $scope.leaderboard[index - 1].lvl)
                    return $scope.tieCheck(index-1, time, livesremain, lvl);
                return index;
            };

            return $scope.tieCheck(index, time, livesremain, lvl);
        }
        if(type == 3){
            var time = $scope.leaderboard[index].time;
            var victory = $scope.leaderboard[index].victory;
            var turnstaken = $scope.leaderboard[index].turnstaken;

            $scope.tieCheck = function(index, time, victory, turnstaken){
                if(index == 0)
                    return index;
                else if(time == $scope.leaderboard[index - 1].time && victory == $scope.leaderboard[index - 1].victory && turnstaken == $scope.leaderboard[index - 1].turnstaken)
                    return $scope.tieCheck(index-1, time, livesremain, lvl);
                return index;
            };

            return $scope.tieCheck(index, time, victory, turnstaken);
        }
    };
    
    $scope.myBB = false;
    $scope.toggleBB = function() {
        $scope.myBB = true;
    };
    
    $scope.myKC = false;
    $scope.toggleKC = function() {
        $scope.myKC = true;
    };
    
    $scope.resetGames = function() {
        $scope.myKC = false;
        $scope.myBB = false;
    };
    
    $scope.theLives = document.getElementById('livesLeft').value;
    $scope.theLevel = document.getElementById('level').value;
    $scope.resultBB = document.getElementById('victoryBB').value;
    $scope.gameOverBB = false;
    
    $scope.theTimer = document.getElementById('timer').value;
    $scope.theTurns = document.getElementById('turns').value;
    $scope.resultKC = document.getElementById('victoryKC').value;
    $scope.gameOverKC = false;
    
    $scope.updateBBData = function() {
        $scope.theTimer = document.getElementById('timerBB').value;
        $scope.theLives = document.getElementById('livesLeft').value.match(/(\d+)/);
        $scope.theLevel = document.getElementById('level').value.match(/(\d+)/);
        $scope.resultBB = document.getElementById('victoryBB').value;
        $scope.gameOverBB = true;
        $scope.victBB;
        if($scope.resultBB.indexOf("Win") > -1){
            $scope.victBB = true;
        }
        else{
            $scope.victBB = false;
        }
        if($scope.loggedIn){
            var url = '/results/new/?gid=1' + '&uid=' + $scope.currentUser.id + '&victory=' + $scope.victBB + '&lvl=' + $scope.theLevel[0] + '&difficulty=-1' + '&time=' + $scope.theTimer + '&turnstaken=-1' + '&livesremain=' + $scope.theLives[0]; 
            $http.post(url).then(function(response){
                var resultPost = response.data;
            });
        }
    };
    
    $scope.updateKCData = function() {
        $scope.theTimer = document.getElementById('timer').value;
        $scope.theTurns = document.getElementById('turns').value.match(/(\d+)/);
        $scope.resultKC = document.getElementById('victoryKC').value;
        $scope.gameOverKC = true;
        $scope.victKC;
        if($scope.resultKC.indexOf("Win") > -1){
            $scope.victKC = true;
        }
        else{
            $scope.victKC = false;
        }
        if($scope.loggedIn){
            var url = '/results/new/?gid=2' + '&uid=' + $scope.currentUser.id + '&victory=' + $scope.victKC + '&lvl=-1' + '&difficulty=-1' + '&time=' + $scope.theTimer + '&turnstaken=' + $scope.theTurns[0] + '&livesremain=-1'; 
            $http.post(url).then(function(response){
                var resultPost = response.data;
            });
        }
    };
    
    $scope.boxBB = {};
    $scope.boxKC = {};
    $scope.boxWins = {};
    $scope.boxResults = {};
    
    
    $scope.getWinsBoard = function(){
        if($scope.boxBB.checked){
            $scope.gameID = 1;
        }
        else if($scope.boxKC.checked){
            $scope.gameID = 2;
        }
        var url = '/leaderboard/byRatioAndWins/' + $scope.gameID;
        $http.get(url).then(function(response){
            var getWinsAttempt = response.data;
            $scope.leaderboard = getWinsAttempt;
        });  
    };
    
    $scope.getResultsBoard = function(){
        if($scope.boxBB.checked){
            $scope.gameID = 1;
        }
        else if($scope.boxKC.checked){
            $scope.gameID = 2;
        }
        var url = '/leaderboard/results/' + $scope.gameID;
        $http.get(url).then(function(response){
            var getResultsAttempt = response.data;
            $scope.leaderboard = getResultsAttempt;
        });  
    };
    
    $scope.getResultsByUser = function(){
        if($scope.boxBB.checked){
            $scope.gameID = 1;
        }
        else if($scope.boxKC.checked){
            $scope.gameID = 2;
        }
        
        if($scope.boxWins.checked){
            var url = '/leaderboard/byUserRatioAndWins/' + $scope.gameID + '/' + $scope.userResultsForm.username;
            $http.get(url).then(function(response){
                var getResultsAttempt = response.data;
                $scope.leaderboard = getResultsAttempt;
            });
        }
        else if($scope.boxResults.checked){
            var url = '/leaderboard/userResults/' + $scope.gameID + '/' + $scope.userResultsForm.username;
            $http.get(url).then(function(response){
                var getResultsAttempt = response.data;
                $scope.leaderboard = getResultsAttempt;
            });
        }
          
    };
    
    $scope.theMaster = "";
    
    $scope.getMasterInfo = function(){
        var url = '/user/ArcadeMaster/';
        $http.get(url).then(function(response){
            var getMasterAttempt = response.data;
            $scope.theMaster = "";
            $scope.theMaster = $scope.theMaster + getMasterAttempt[0].username;
            if(getMasterAttempt.length > 1){
                for(var i = 1; i < getMasterAttempt.length; ++i){
                    if(i = getMasterAttempt.length - 1){
                        $scope.theMaster = $scope.theMaster + ' and ' + getMasterAttempt[i].username
                    }
                    else{
                        $scope.theMaster = $scope.theMaster + getMasterAttempt[i].username + ', ';
                    }
                }
            }
        });
    };
    
    $scope.loggedIn = false;
    $scope.currentUser = {};
    $scope.loginForm = {};
    $scope.signupForm = {};
    $scope.updateForm = {};
    $scope.deleteForm = {};
    $scope.userResultsForm = {};
    $scope.messageForm = {};
    $scope.msgMenu = false;
    
    $scope.toggleMsgMenu = function(){
        $scope.msgMenu = !$scope.msgMenu;
        $scope.messageForm.recipient = null;
        $scope.messageForm.subject = null;
        $scope.messageForm.body = null;
    };
    
    $scope.findUsers = function(){
        var url = '/user/likeUsername/' + $scope.messageForm.username;
        $http.get(url).then(function(response){
            var getUserAttempt = response.data;
            $scope.userProfiles = getUserAttempt;
        });  
    };
    
    $scope.getUserInfo = function(){
        var url = '/user/byUsername/' + $scope.currentUser.username;
        $http.get(url).then(function(response){
            var getUserAttempt = response.data;
            $scope.currentUser.firstname = getUserAttempt.firstname;
            $scope.currentUser.lastname = getUserAttempt.lastname;
            $scope.currentUser.id = getUserAttempt.id;
            if(getUserAttempt.username == "Error")
                $scope.go("/");
        });  
    };
    
    $scope.sendMessage = function(){
        if($scope.messageForm.recipient == null || $scope.messageForm.recipient == undefined)
            return;
        if($scope.messageForm.subject == null || $scope.messageForm.subject == undefined)
            return;
        if($scope.messageForm.body == null || $scope.messageForm.body == undefined)
            return;
        var theDate = new Date();
        var theTime = '';
        var hh = String(theDate.getHours()).padStart(2, '0');
        var min = String(theDate.getMinutes()).padStart(2, '0');
        theTime = theTime + hh + min;
        var dd = String(theDate.getDate()).padStart(2, '0');
        var mm = String(theDate.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = theDate.getFullYear();
        theDate = mm + dd + yyyy;
        var url = '/msg/new/?senduid=' + $scope.currentUser.id + '&receiveuid=' + $scope.messageForm.recipient + '&subject=' + $scope.messageForm.subject + '&msgbody=' + $scope.messageForm.body+ '&msgdate=' + theDate + '&msgtime=' + theTime;
        $http.post(url).then(function(response){
            var newMessage = response.data;
            $scope.getMsgBox();
            $scope.toggleMsgMenu();
            $scope.go("/ArcUserPage");
        });  
    };
    
    $scope.getMsgBox = function(){
        var url = '/msgbox/get/' + $scope.currentUser.username;
        $http.get(url).then(function(response){
            var theMsgs = response.data;
            $scope.msgBox = theMsgs;
        });  
    };
    
    $scope.deleteAttempt = function(){
        if($scope.deleteForm.uname == null || $scope.deleteForm.uname == undefined)
            return;
        if($scope.deleteForm.pword == null || $scope.deleteForm.pword == undefined)
            return;
        var url = '/user/delete/?username=' + $scope.deleteForm.uname + '&password=' + $scope.deleteForm.pword;
        $http.delete(url).then(function(response){
            var delAttempt = response.data;
            if(delAttempt.username.indexOf("Error") > -1){
                $scope.deleteForm.uname = "Error - try again.";
                return;
            }
            else if(delAttempt.username.indexOf("goner") > -1){
                $scope.logout();
            }
        });
    };
    
    $scope.attemptUserUpdate = function(){
        var username = $scope.updateForm.uname;
        var pword = $scope.updateForm.pword;
        var fname = $scope.updateForm.fname;
        var lname = $scope.updateForm.lname;
        if($scope.updateForm.oldpword == null || $scope.updateForm.oldpword == undefined)
            return;
        if($scope.updateForm.uname == null || $scope.updateForm.uname == undefined)
            username = $scope.currentUser.username;
        if($scope.updateForm.pword == null || $scope.updateForm.pword == undefined)
            pword = $scope.updateForm.oldpword;
        if($scope.updateForm.fname == null || $scope.updateForm.fname == undefined)
            fname = $scope.currentUser.firstname;
        if($scope.updateForm.lname == null || $scope.updateForm.lname == undefined)
            lname = $scope.currentUser.lastname;
        
        var url = '/user/update/?oldUsername=' + $scope.currentUser.username + '&oldPassword=' + $scope.updateForm.oldpword + '&firstname=' + fname + '&lastname=' + lname + '&username=' + username + '&password=' + pword + '&pic=filler';
        $http.put(url).then(function(response){
            var updateAttempt = response.data;
            if(updateAttempt.username.indexOf("Error") > -1){
                $scope.updateForm.uname = "Error - try again.";
                return;
            }
            else{
                $scope.currentUser.username = username;
                if (typeof(Storage) !== "undefined") {
                    sessionStorage.setItem("username", $scope.currentUser.username);
                    sessionStorage.setItem("uid", $scope.currentUser.id);
                }
                $scope.reload();
            }
        });
    };
    
    $scope.attemptLogin = function(){
        if($scope.loginForm.uname == null || $scope.loginForm.uname == undefined)
            return;
        if($scope.loginForm.pword == null || $scope.loginForm.pword == undefined)
            return;
        var url = '/user/login/?username=' + $scope.loginForm.uname + '&password=' + $scope.loginForm.pword;
        $http.get(url).then(function(response){
            var logAttempt = response.data;
            if(logAttempt.username.indexOf("Error") > -1){
                $scope.loginForm.uname = "Error - try again.";
                return;
            }
            else{
                $scope.currentUser = logAttempt;
                $scope.loggedIn = true;
                if (typeof(Storage) !== "undefined") {
                    sessionStorage.setItem("username", $scope.currentUser.username);
                    sessionStorage.setItem("uid", $scope.currentUser.id);
                }
                $scope.go("/ArcUserPage");
            }
        });
    };
    
    $scope.attemptSignup = function(){
        if($scope.signupForm.uname == null || $scope.signupForm.uname == undefined)
            return;
        if($scope.signupForm.pword == null || $scope.signupForm.pword == undefined)
            return;
        if($scope.signupForm.fname == null || $scope.signupForm.fname == undefined)
            return;
        if($scope.signupForm.lname == null || $scope.signupForm.lname == undefined)
            return;
        var url = '/user/new/?firstname=' + $scope.signupForm.fname + '&lastname=' + $scope.signupForm.lname + '&username=' + $scope.signupForm.uname + '&password=' + $scope.signupForm.pword + '&pic=filler';
        $http.post(url).then(function(response){
            var signupAttempt = response.data;
            if(signupAttempt.username.indexOf("Error") > -1){
                $scope.signupForm.uname = "Error - try again.";
                return;
            }
            else{
                $scope.currentUser = signupAttempt;
                $scope.loggedIn = true;
                if (typeof(Storage) !== "undefined") {
                    sessionStorage.setItem("username", $scope.currentUser.username);
                    sessionStorage.setItem("uid", $scope.currentUser.id);
                }
                $scope.go("/ArcUserPage");
            }
        });
    };
    
    $scope.sessionInfo = function(){
        $scope.currentUser.username = sessionStorage.getItem("username");
        $scope.currentUser.id = sessionStorage.getItem("uid");
        if($scope.currentUser.username != null && $scope.currentUser.username != "null"){
            $scope.loggedIn = true;
        }
        else{
            $scope.loggedIn = false;
        }
    };
    
    $scope.go = function ( path ) {
        $location.path( path );
    };
    
    $scope.logout = function(){
        $scope.go("/");
        if (typeof(Storage) !== "undefined") {
            sessionStorage.setItem("username", null);
            sessionStorage.setItem("uid", null);
        }
        $scope.currentUser.username = null;
        $scope.currentUser.id = null;
        $scope.loggedIn = false;
        
    };
    
    $scope.reload = function() {
        if (typeof(Storage) !== "undefined") {
            sessionStorage.setItem("username", $scope.currentUser.username);
            sessionStorage.setItem("uid", $scope.currentUser.id);
        }
        if( window.localStorage ){
            if( !localStorage.getItem('firstLoad') ){
              localStorage['firstLoad'] = true;
              window.location.reload();
            }  
            else
              localStorage.removeItem('firstLoad');
        }
    };
});    



var time = 0;

setInterval(function() {
    
    var element = document.getElementById('timer');
    var element2 = document.getElementById('timerBB');
    
    if(document.getElementById('victoryKC').value.indexOf("Game on.") > -1 && !angular.element(document.getElementById('bigBody')).scope().gameOverKC) {
        element.value = time;
        time = time + 1;
    }
    
    if(document.getElementById('victoryBB').value.indexOf("Game on.") > -1 && !angular.element(document.getElementById('bigBody')).scope().gameOverBB) {
        element2.value = time;
        time = time + 1;
    }
    
    if(!angular.element(document.getElementById('bigBody')).scope().gameOverBB && (document.getElementById('victoryBB').value.indexOf("Win") > -1 || document.getElementById('victoryBB').value.indexOf("Los") > -1)) {
        angular.element(document.getElementById('bigBody')).scope().updateBBData();
    }
    
    if(!angular.element(document.getElementById('bigBody')).scope().gameOverKC && (document.getElementById('victoryKC').value.indexOf("Win") > -1 || document.getElementById('victoryKC').value.indexOf("Los") > -1)) {
        angular.element(document.getElementById('bigBody')).scope().updateKCData();
    }
    
}, 1000);

function startKC() {
    var element = document.getElementById('victoryKC');
    element.value = "Game on.";
}

function startBB() {
    var element = document.getElementById('victoryBB');
    element.value = "Game on.";
}








    