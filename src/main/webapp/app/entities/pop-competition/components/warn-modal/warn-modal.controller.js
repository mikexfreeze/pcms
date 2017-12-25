/**
 * Created by zhaimaojin on 2017/6/7.
 */
(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('warnModalController', warnModalController)


    warnModalController.$inject = ['$scope', '$stateParams', '$state', '$uibModal','$uibModalInstance','bodyDescription'];

    function warnModalController ($scope, $stateParams, $state, $uibModal, $uibModalInstance,bodyDescription) {

        // $scope.message = '提交后您的评选结果将无法在修改，确认提交吗？'
        $scope.description = bodyDescription;
        var  competitionId=$scope.competitionId;
        // console.log(competitionId)
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
            $uibModalInstance.close({$value: false})

        };
        $scope.confirm =function (flag) {
            $uibModalInstance.close({$value: true})
        }
    }

})();
