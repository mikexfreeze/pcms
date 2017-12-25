(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopContributeDetailController', PopContributeDetailController);

    PopContributeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopContribute', 'PopSubject'];

    function PopContributeDetailController($scope, $rootScope, $stateParams, previousState, entity, PopContribute, PopSubject) {
        var vm = this;

        vm.popContribute = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popContributeUpdate', function(event, result) {
            vm.popContribute = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
