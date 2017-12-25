/**
 * Created by zhaimaojin on 17/4/19.
 */

(function() {

    angular
        .module('pcmsApp')
        .controller('judgeControllTopBarJudgeCtrl', judgeControllTopBarJudgeCtrl);

    judgeControllTopBarJudgeCtrl.$inject = ['Principal', '$rootScope', '$scope', '$state', '$stateParams', '$window', '$timeout', 'Auth', '$localStorage', 'topBarJudgeService','CommonService', '$uibModal', '$interval', 'PopJudgeConfig', '$q', 'toaster'];

    function judgeControllTopBarJudgeCtrl (Principal, $rootScope, $scope, $state, $stateParams,$window, $timeout, Auth, $localStorage, topBarJudgeService, CommonService, $uibModal, $interval, PopJudgeConfig, $q, toaster) {

        var flowType = $stateParams.branchAppraise;
        $scope.flowTypeFlag = $stateParams.branchAppraise || "trunk"
        $interval(function () {
            // if ($scope.allVoted) {
            //     $interval.cancel();
            // } else {
            //     $state.reload();
            // }
        }, 1000*10);

        // 待处理的图片列表
        var selectedImages = [];

        $rootScope.initClass = "judge-controll";
        $rootScope.$on('checkPic', function (event,imgs) {
            console.log("imgs");
            console.log(imgs);
            selectedImages = imgs;
        });

        $rootScope.$on('enter',function () {
            fetchUserCompetitionCount($scope.appraiseId);
        });
        $rootScope.$on('abandon',function () {
            fetchUserCompetitionCount($scope.appraiseId);
        });


        function checkResult(result) {
            if(result.code > 1000){
                toaster.pop('error',"错误", result.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }
        $scope.competitionId = $stateParams.competitionId;
        $scope.appraiseId = $stateParams.appraiseId;
        $scope.subjectId = $stateParams.subjectId;
        $scope.judgeId = $stateParams.judgeId;
        $scope.branchAppraise = $stateParams.branchAppraise;
        $scope.userId = $localStorage.user.userLogin.id;
        $scope.votedJudgeList = []

        // function loadPageVar (sVar) {
        //     return decodeURI($window.location.hash.replace(new RegExp("^(?:.*[&\\?]" + encodeURI(sVar).replace(/[\.\+\*]/g, "\\$&") + "(?:\\=([^&]*))?)?.*$", "i"), "$1"));
        // }
        //
        // console.log("appraiseId");
        // console.log($window.location);
        // console.log(loadPageVar("appraiseId"));
        // console.log($window.location.origin + $window.location.origin + $window.location.pathname)
        //检查是否需要刷新或跳转
        var stop = false
        function checkRefresh() {
            $rootScope.$on('$stateChangeStart',function (p1, p2) { stop = true })
            PopJudgeConfig.checkRefresh( {
                "appraiseId": $scope.appraiseId,
                "flowType": $scope.flowTypeFlag,
                "judgeUserId": $scope.votedJudgeList,
                "userId": $scope.userId,
                "subjectId": $scope.subjectId
            }).then(function (response) {
                var code = response.data.code
                var params = response.data.object
                if(response.code > "1000"){toaster.pop('error', "错误", response.msg);}
                // if(!response.data.success){
                if(code == "0000"){
                    if(!stop){
                        $timeout(function () {
                            checkRefresh();
                        },5000)
                    }
                }else{
                    if(code == "0004"){
                        $state.go($state.current, {}, {
                            reload: true
                        }).then(function(){
                            $timeout(function() {
                                $window.location.reload(true);
                            }, 100);
                        });
                    }else if(code == "0003"){
                        var branckTrunk = "";
                        if(params.flowType){
                            if(params.flowType == "branch"){
                                branckTrunk = "branch"
                            }
                        }else{
                            if($scope.flowTypeFlag == "branch"){
                                branckTrunk = "branch"
                            }
                        }
                        $state.go('judge', {
                            "appraiseId": params.appraiseId ? params.appraiseId : $scope.appraiseId,
                            "flowType": params.flowType ? params.flowType : $scope.flowTypeFlag,
                            "subjectId": params.subjectId ? params.subjectId : $scope.subjectId,
                            "competitionId": params.competitionId ? params.competitionId : $scope.competitionId,
                            "judgeId": params.judgeId ? params.judgeId : $scope.judgeId,
                            "parentAppraiseId": params.parentAppraiseId ? params.parentAppraiseId : "",
                        }, {
                            reload: true
                        }).then(function(){
                            $timeout(function() {
                                $window.location.reload(true);
                            }, 100);
                        });

                    }else if(code == "0002"){
                        $state.go($state.current, {
                            "appraiseId": params.appraiseId ? params.appraiseId : $scope.appraiseId,
                            "flowType": params.flowType ? params.flowType : $scope.flowTypeFlag,
                            "branchAppraise": params.flowType ? params.flowType : $scope.flowTypeFlag,
                            "subjectId": params.subjectId ? params.subjectId : $scope.subjectId,
                            "competitionId": params.competitionId ? params.competitionId : $scope.competitionId,
                        }, {
                            reload: true
                        }).then(function(){
                            $timeout(function() {
                                $window.location.reload(true);
                            }, 100);
                        });
                        //跳转最新轮次
                    }else if(code == "0001"){
                        //请跳转列表页
                        $state.go('pop-judge').then(function(){
                            $timeout(function() {
                                $window.location.reload(true);
                            }, 100);
                        });
                    }
                }

            })
        }

        if ($scope.branchAppraise == 'branch') {
            fetchBranchAppraise();
        } else {
            fetchAppraiseRelatedData($scope.appraiseId);
        }

        $scope.subject = {};
        fetchSubjectDetails();
        $scope.appraise = {};

        function fetchAppraiseDetails(appraiseId) {
            topBarJudgeService.fetchAppraiseDetails(appraiseId)
                .then(function (result) {
                    $scope.appraise = result.data;
                });
        }

        function fetchBranchAppraise() {
            topBarJudgeService.fetchBranchAppraise($scope.appraiseId)
                .then(function (result) {
                    if (result.data) {
                        $scope.branchAppraiseId = result.data;
                        fetchAppraiseRelatedData($scope.branchAppraiseId);
                    } else {
                        fetchAppraiseRelatedData($scope.appraiseId);
                    }

                });
        }

        function fetchAppraiseRelatedData(appraiseId) {
            fetchAppraiseDetails(appraiseId);
            fetchUserCompetitionCount(appraiseId);
            fetchJudgeVoteStatus(appraiseId);
        }

        $scope.userCompetitonCount = 0;

        $scope.judges = [];

        function fetchJudgeVoteStatus(appraiseId) {
            topBarJudgeService.fetchJudgeVoteStatus(appraiseId)
                .then(function (result) {
                    if (result.data) {
                        var results = result.data;
                        var submittedJudges = [];
                        var unSubmittedJudges = [];
                        for (var i = 0; i < results.length; i++) {
                            if (results[i].voteStatus == 'SUBMITTED') {
                                submittedJudges.push(results[i]);
                            } else {
                                unSubmittedJudges.push(results[i]);
                            }
                        }
                        $rootScope.submittedJudges = submittedJudges;
                        $rootScope.unSubmittedJudges = unSubmittedJudges;
                        if(unSubmittedJudges.length <= 0) {
                            $scope.allVoted = true;
                        } else {
                            $scope.allVoted = false;
                        }
                        $scope.votedJudgeList = [];
                        result.data.forEach(function (val) {
                            if(val.voteStatus == "SUBMITTED"){
                                $scope.votedJudgeList.push(val.userId)
                            }
                        })
                        checkRefresh()
                    }
                });
        }

        Principal.identity().then(function(account) {
            $scope.currentAccount = account;
            $scope.isAdmin = $.inArray('ROLE_ADMIN', account.authorities) >= 0;
            if ($scope.isAdmin){
                $scope.logoHref = 'http://118.187.50.42/pcms-web/#/'
            }else{
                $scope.logoHref = 'http://118.187.50.42/content/popphoto/home.html'
            }
        });



        function fetchSubjectDetails() {
            topBarJudgeService.fetchSubjectDetails($scope.subjectId)
                .then(function (result) {
                    $scope.subject = result.data;
                });
        }

        function fetchUserCompetitionCount(appraiseId) {
            topBarJudgeService.fetchEnteredCount(appraiseId, flowType)
                .then(function (result) {
                    console.log("userCompetitonCount")
                    console.log(result)
                    $scope.userCompetitonCount = result.data;
                });
        }

        var modalInstance;
        function openAppraiseSelector(isNext, callback) {
            modalInstance = $uibModal.open({
                templateUrl: 'app/layouts/topbar-judge-controll/judge-controll-navbar.dialog.html',
                controller: 'judgeControllTopBarJudgeDialogCtrl',
                controllerAs: '$ctrl',
                scope:$scope,
                resolve: {
                    isNext: function () {
                        return isNext;
                    },
                    round: function () {
                        return $scope.appraise.round;
                    },
                    subjectId: function() {
                        return $stateParams.subjectId;
                    },
                    appraiseId: function () {
                        return $stateParams.appraiseId;
                    }
                }
            });
            modalInstance.result.then(function (selectedAppraise) {
                console.log("selectedAppraise")
                console.log(selectedAppraise)
                callback(selectedAppraise);
            });
        }

        $scope.enter = function () {
            //校验是否有勾选checkbox
            var block = 0;
            $(":checkbox").each(function (n, val) {
                if($(val).prop("checked")){
                    var nerbor = $(val).siblings(".selected-img")
                    if(nerbor.length > 0){
                        toaster.pop("error","错误","选中作品中有已入选作品！")
                        console.log(nerbor)
                        block = 0;
                        return false;
                    }else{
                        block ++;
                    }
                }
            })
            if(block < 1){
                return
            }
            openAppraiseSelector(true, function (selectedAppraise) {
                $rootScope.$broadcast('enterAppraise', selectedAppraise.id);
            })

        };

        $scope.abandon = function () {
            openAppraiseSelector(false, function (selectedAppraise) {
                $rootScope.$broadcast('abandonAppraise', selectedAppraise.id);
            });
            // console.log("appraisedID")
            // console.log($scope.appraiseId)
        };

        $scope.delete = function () {
            CommonService.confirmModal()
                .then(function () {
                    $rootScope.$broadcast('deleteAppraise');
                });
        };

        $scope.nextRound = function () {

            $state.go('pop-competition.pop-com-judge-config', {pAppraiseId: $scope.appraiseId, pAppraiseRound: $scope.appraise.round, subjectId: $scope.subjectId, competitionId: $scope.competitionId});
        };

        $scope.branch = function () {

            //校验是否有勾选checkbox且不是已入选作品
            var block = 0;
            $(":checkbox").each(function (n, val) {
                if($(val).prop("checked")){
                    var nerbor = $(val).siblings(".selected-img")
                    if(nerbor.length > 0){
                        toaster.pop("error","错误","不能对已入选的作品进行投票！")
                        console.log(nerbor)
                        block = 0;
                        return false;
                    }else{
                        block ++;
                    }
                }
            })
            if(block < 2){
                return false
            }
            var tempAppraiseId = $scope.appraise.id;
            $scope.appraise.parentAppraise = tempAppraiseId;
            $scope.appraise.id = null;
            PopJudgeConfig.saveConfig($scope.appraise)
                .then(function (result) {
                    checkResult(result)
                        .then(function (result) {
                            $scope.appraise.id = tempAppraiseId;
                            var newAppraiseId = result.data.id;
                            $state.go('judge-controll', {appraiseId: tempAppraiseId, branchAppraise: "branch", subjectId: $scope.subjectId, competitionId: $scope.competitionId});
                            toaster.pop('info',"保存成功");
                            $rootScope.$broadcast('branchAppraise', newAppraiseId);
                        });
                }, function (error) {
                    $scope.appraise.id = tempAppraiseId;
                    toaster.pop('error',"错误", error.msg);
                });
            // $state.go('pop-competition.pop-com-judge-config', {parentAppraiseId: $scope.appraiseId, parentAppraiseRound: $scope.appraise.round, subjectId: $scope.subjectId, competitionId: $scope.competitionId});
        };

        $scope.finish = function () {
            // topBarJudgeService.finishAppraiseById($scope.appraise.id)
            topBarJudgeService.finishSubjectById($scope.subjectId)
                .then(function (result) {
                    $state.go('pop-competition-theme-pop-appraise', {competitionId: $scope.competitionId, subjectId: $scope.subjectId});
                });
            // topBarJudgeService.finishSubjectById($scope.subjectId)
            //     .then(function (result) {
            //         $state.go('com-tab-page-publish', {competitionId: $scope.competitionId});
            //     });
        };

        // 显示已投评委列表
        var modalInstance;
        $scope.showJudgeList = function () {
            modalInstance = $uibModal.open({
                templateUrl: 'app/layouts/topbar-judge-controll/judge-controll-list/judge-controll-list.html',
                controller: 'JudgeControlCtrl',
                controllerAs: '$ctrl',
                scope:$scope,
                resolve: {
                    appraiseId: function() {
                        return $scope.appraiseId;
                    }
                }
            });
            modalInstance.result.then(function () {
                fetchJudgeVoteStatus($scope.appraiseId);
            });
        };


    }
})();
