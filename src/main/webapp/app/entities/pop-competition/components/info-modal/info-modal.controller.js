/**
 * Created by zhaimaojin on 2017/6/7.
 */
(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('InfoModalController', InfoModalController)


    InfoModalController.$inject = ['$scope', '$stateParams', '$state', '$uibModal','$uibModalInstance', 'title', 'bodyDescription'];

    function InfoModalController ($scope, $stateParams, $state, $uibModal, $uibModalInstance, title, bodyDescription) {

        // $scope.message = '提交后您的评选结果将无法在修改，确认提交吗？'
        $scope.title = title;
        $scope.description = bodyDescription;
        // var  competitionId=$scope.competitionId;
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
