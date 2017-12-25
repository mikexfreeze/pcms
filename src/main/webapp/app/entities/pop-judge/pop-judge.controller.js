(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopJudgeController', PopJudgeController);

    PopJudgeController.$inject = ['Principal', '$scope', '$state', 'PopJudge'];

    function PopJudgeController (Principal, $scope, $state, PopJudge) {
        var vm = this;

        $scope.status = "VOTING";
        Principal.identity().then(function(account) {
            vm.currentAccount = account;
            $scope.isObserver = $.inArray('ROLE_OBSERVER', account.authorities) >= 0;
            fetchSubjectsByJudgeIdAndStatus();
        });

        $scope.getByStatus = function () {
            fetchSubjectsByJudgeIdAndStatus();
        };

        var fetchSubjectsByJudgeIdAndStatus = function () {
            PopJudge.getJudgeDetails(vm.currentAccount.id, $scope.status)
                .then(function (result) {
                    $scope.subjects = result.data.judgeList.concat(result.data.observerList);
                });
        };

        $scope.start = function (x) {
            if (x.voteStatus == 'SUBMITTED' || $scope.isObserver) {
                $state.go("judge-controll", {subjectId: x.subjectId, appraiseId: x.appraiseId, competitionId: x.competitionId, branchAppraise: x.parentAppraiseId ? 'branch' : 'trunk'});
            } else {
                $state.go("judge", {judgeId:x.id,appraiseId:x.appraiseId,subjectId:x.subjectId,parentAppraiseId:x.parentAppraiseId});
            }
        }
    }
})();
