/**
 * Created by zhaimaojin on 17/3/22.
 */
(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('DelModalController', DelModalController)


    DelModalController.$inject = ['$scope', '$stateParams', '$state', '$uibModal','$uibModalInstance'];

    function DelModalController ($scope, $stateParams, $state, $uibModal, $uibModalInstance) {

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
