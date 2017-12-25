/**
 * Created by Yue Gu on 2017/3/23.
 */
angular
    .module('pcmsApp')
    .controller('JudgeControlCtrl', JudgeControlCtrl);

JudgeControlCtrl.$inject = ['$scope', 'CommonService', '$uibModalInstance', '$q', 'toaster', 'PopJudgeConfig', 'appraiseId'];

function JudgeControlCtrl($scope, CommonService, $uibModalInstance, $q, toaster, PopJudgeConfig, appraiseId) {
    var $ctrl = this;

    $ctrl.ok = function () {
        $uibModalInstance.close($ctrl.selected.item);
    };

    $scope.cancel = function () {
        $uibModalInstance.close();
    };


    var vm = this;
    $scope.vm = vm;
    vm.judges = [];

    fetchVotedJudges(appraiseId);
    vm.reset = reset;

    function reset(judge) {
        CommonService.judgeConfirmModal('该操作将删除所有该评委下的评选信息，确定继续？')
            .then(function () {
                PopJudgeConfig.deleteJudgeVoted(judge.id, appraiseId)
                    .then(function (result) {
                        checkResult(result)
                            .then(function (data) {
                                toaster.info('重置成功');
                            });
                    });
            },function () {
                console.log("取消");
            });
    }

    function fetchVotedJudges(appraiseId) {
        PopJudgeConfig.fetchVotedJudges(appraiseId)
            .then(function (result) {
                checkResult(result)
                    .then(function (data) {
                        vm.judges = data.data;
                    });
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
