/**
 * Created by Micheal Xiao on 2017/4/17.
 */
(function() {

    angular
        .module('pcmsApp')
        .controller('topBarJudgeCtrl', topBarJudgeCtrl);

    topBarJudgeCtrl.$inject = ['Principal', '$scope', '$rootScope', '$stateParams', '$state', '$timeout', 'Auth', '$localStorage', 'topBarJudgeService','toaster','$uibModal','CommonService'];

    function topBarJudgeCtrl (Principal, $scope, $rootScope, $stateParams, $state, $timeout, Auth, $localStorage, topBarJudgeService,toaster,$uibModa,CommonService) {
        // $rootScope.initClass = "judge";
        // $rootScope.$apply()

        $scope.activeMenu = 'All';



        $scope.getPicList = function (condition) {
            $rootScope.$broadcast('cdgGetPicCdion', condition)
        };

        if($stateParams.competitionId){
            $scope.competitionId = $stateParams.competitionId
        }else{
            $scope.competitionId = ""
        }
        $rootScope.$on('$stateChangeStart',function (p1, p2) {
            console.log("路由change")
        })

        if ($stateParams.judgeId) {
            $scope.judgeId = $stateParams.judgeId;
        } else {
            $scope.judgeId = ""
        }

        if ($stateParams.subjectId) {
            $scope.subjectId = $stateParams.subjectId;
        } else {
            $scope.subjectId = ""
        }

        if ($stateParams.appraiseId) {
            $scope.appraiseId = $stateParams.appraiseId;
        } else {
            $scope.appraiseId = ""
        }

        $scope.parentAppraiseId = $stateParams.parentAppraiseId;

        $scope.subject = {};
        fetchSubjectDetails();
        $scope.appraise = {};
        fetchAppraiseDetails();
        $rootScope.votedCount = 0;
        $scope.judges = [];
        fetchJudgeVoteStatus();

        function fetchJudgeVoteStatus() {
            topBarJudgeService.fetchJudgeVoteStatus($scope.appraiseId)
                .then(function (result) {
                    $scope.judges = result.data;
                });
        }

        Principal.identity().then(function(account) {
            $scope.currentAccount = account;
            fetchUserCompetitionCount();
        });

        function fetchSubjectDetails() {
            topBarJudgeService.fetchSubjectDetails($scope.subjectId)
                .then(function (result) {
                    $scope.subject = result.data;
                });
        }

        function fetchAppraiseDetails() {
            topBarJudgeService.fetchAppraiseDetails($scope.appraiseId)
                .then(function (result) {
                    $scope.appraise = result.data;

                    if ($scope.appraise.remark) {
                        CommonService.infoModal($scope.appraise.remark, '评选说明');
                    }
                });
        }

        Principal.identity().then(function(account) {
            $scope.currentAccount = account;
            $scope.isAdmin = $.inArray('ROLE_ADMIN', account.authorities) >= 0;
        });

        function fetchUserCompetitionCount() {
            topBarJudgeService.fetchUserCompetitionCount($stateParams.judgeId, $scope.appraiseId)
                .then(function (data) {
                    // console.log(data);
                    $rootScope.votedCount = data.data;
                });
        }
        $rootScope.$on('vote',function () {
            fetchUserCompetitionCount();
        });

        $scope.submit = function () {
            CommonService.judgeConfirmModal("提交后您的评选结果将无法在修改，确认提交吗？")
                .then(function () {
                    topBarJudgeService.submit($scope.judgeId, $scope.appraiseId)
                        .then(function (data) {
                            $state.go("judge-controll", {subjectId: $scope.subjectId, appraiseId: $scope.appraiseId, competitionId: $scope.competitionId, branchAppraise: $scope.parentAppraiseId ? 'branch' : 'trunk'});
                        },
                        function () {
                            console.log("否")
                        });
                })
        };

        // function checkVote() {
        //
        // }

        // function modal() {
        //
        //     modalInstance = $uibModal.open({
        //         templateUrl: 'app/entities/pop-competition/components/add-new-host/add-new-host.html',
        //         controller: 'AddHostCtrl',
        //         scope:$scope,
        //     }).result.then(function (userId) {
        //         $scope.hostId = userId;
        //         console.log("userId");
        //         console.log(userId);
        //         getHostByUserId($scope.hostId)
        //     })
        // }
        // modal();


        // function checkNumber() {
        //     topBarJudgeService.fetchAppraiseDetails($scope.appraiseId)
        //         .then(function (result) {
        //             $scope.checkNum = result.data.pollNum;
        //             console.log($scope.checkNum)
        //
        //             if($scope.checkNum<$rootScope.votedCount){
        //                 // toaster.pop("error","超过最大可选数量");
        //             }
        //
        //         });
        // }
    }
})();
