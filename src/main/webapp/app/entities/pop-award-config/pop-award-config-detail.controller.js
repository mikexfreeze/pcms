(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAwardConfigDetailController', PopAwardConfigDetailController);

    PopAwardConfigDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopAwardConfig', 'PopSubject'];

    function PopAwardConfigDetailController($scope, $rootScope, $stateParams, previousState, entity, PopAwardConfig, PopSubject) {
        var vm = this;

        vm.popAwardConfig = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popAwardConfigUpdate', function(event, result) {
            vm.popAwardConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
