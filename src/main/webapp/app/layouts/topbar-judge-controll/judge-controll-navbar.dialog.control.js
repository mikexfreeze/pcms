/**
 * Created by Yue Gu on 2017/3/23.
 */
angular
    .module('pcmsApp')
    .controller('judgeControllTopBarJudgeDialogCtrl', judgeControllTopBarJudgeDialogCtrl);

judgeControllTopBarJudgeDialogCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal', '$uibModalInstance', '$q', 'toaster', 'topBarJudgeService', 'subjectId', 'appraiseId', 'isNext', 'round', '$timeout'];

function judgeControllTopBarJudgeDialogCtrl($scope, $stateParams, $state, $uibModal, $uibModalInstance, $q, toaster, topBarJudgeService, subjectId, appraiseId, isNext, round, $timeout) {
    var $ctrl = this;

    $ctrl.ok = function () {
        $uibModalInstance.close($ctrl.selected.item);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.confirm = function () {
        if ($scope.selectedAppraise) {
            $uibModalInstance.close($scope.selectedAppraise);
        } else {
            toaster.pop('error',"错误", '请选择轮次');
        }
    };

    if (isNext) {
        fetchNextAppraises(appraiseId, round);
    } else {
        fetchPreviousAppraises(appraiseId, round);
    }

    function fetchPreviousAppraises(appraiseId) {

        topBarJudgeService.fetchPreviousAppraises(appraiseId, round)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        $scope.appraises = result.data;
                    });
            }, function (error) {
                toaster.pop('error',"错误", result.msg);
            });
    }

    function fetchNextAppraises(appraiseId, round) {

        topBarJudgeService.fetchNextAppraises(appraiseId, round)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        $scope.appraises = result.data;
                    });
            }, function (error) {
                toaster.pop('error',"错误", result.msg);
            });
    }

    function checkResult(result) {
        if(result.code > 1000){
            toaster.pop('error',"错误", result.msg);
            return $q.reject(result)
        }else{
            return $q.resolve(result)
        }
    }

}
