/**
 * Created by Yue Gu on 2017/3/23.
 */
angular
    .module('pcmsApp')
    .controller('PopAppraiseAwardConfigCtrl', PopAppraiseAwardConfigCtrl);

PopAppraiseAwardConfigCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal', '$uibModalInstance', '$q', 'toaster', 'PopJudgeConfig', 'User', '$timeout', 'entity'];

function PopAppraiseAwardConfigCtrl($scope, $stateParams, $state, $uibModal, $uibModalInstance, $q, toaster, PopJudgeConfig, User, $timeout, entity) {
    var $ctrl = this;

    $ctrl.ok = function () {
        $uibModalInstance.close($ctrl.selected.item);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    var vm = this;
    $scope.vm = vm;
    vm.appraise = entity;
    vm.awardConfig = {};

    function fetchAvailableAwards() {
        PopJudgeConfig.getAwardConfigsByCompetitionId($stateParams.competitionId)
            .then(function (result) {
                $scope.availableAwards = result.data;
                if ($scope.availableAwards && entity.awardConfigId) {
                    $scope.availableAwards.unshift({id: '', name: ''});
                }
                vm.awardConfig = awardConfigForId(entity.awardConfigId);

            }, function (error) {

            });
    }

    fetchAvailableAwards();

    function saveConfig() {
        entity.awardConfigId = vm.awardConfig.id;
        entity.awardName = vm.awardConfig.name;
        PopJudgeConfig.saveConfig(entity)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        vm.id = result.data.id;
                        toaster.pop('info',"保存成功");
                        $timeout(function () {
                            $uibModalInstance.close();
                        },100)
                    });
            }, function (error) {
                toaster.pop('error',"错误", error.msg);
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

    vm.save = saveConfig;

    function awardConfigForId(id) {
        if ($scope.availableAwards instanceof Array) {
            var s = $scope.availableAwards;
            if (s.length > 0) {
                for (var i = 0; i < s.length; i++) {
                    if (s[i].id == id) {
                        return s[i];
                    }
                }
            }
        }

        return {};
    }

}
