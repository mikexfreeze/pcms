(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAwardDetailController', PopAwardDetailController);

    PopAwardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopAward', 'PopContribute'];

    function PopAwardDetailController($scope, $rootScope, $stateParams, previousState, entity, PopAward, PopContribute) {
        var vm = this;

        vm.popAward = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popAwardUpdate', function(event, result) {
            vm.popAward = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
