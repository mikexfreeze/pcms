(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAppraiseDetailController', PopAppraiseDetailController);

    PopAppraiseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopAppraise', 'PopSubject'];

    function PopAppraiseDetailController($scope, $rootScope, $stateParams, previousState, entity, PopAppraise, PopSubject) {
        var vm = this;

        vm.popAppraise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popAppraiseUpdate', function(event, result) {
            vm.popAppraise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
