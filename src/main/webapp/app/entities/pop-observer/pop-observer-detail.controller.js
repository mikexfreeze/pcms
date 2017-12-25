(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopObserverDetailController', PopObserverDetailController);

    PopObserverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopObserver', 'PopAppraise'];

    function PopObserverDetailController($scope, $rootScope, $stateParams, previousState, entity, PopObserver, PopAppraise) {
        var vm = this;

        vm.popObserver = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popObserverUpdate', function(event, result) {
            vm.popObserver = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
