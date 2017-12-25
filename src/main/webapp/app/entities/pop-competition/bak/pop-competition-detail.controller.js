(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopCompetitionDetailController', PopCompetitionDetailController);

    PopCompetitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopCompetition'];

    function PopCompetitionDetailController($scope, $rootScope, $stateParams, previousState, entity, PopCompetition) {
        var vm = this;

        vm.popCompetition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popCompetitionUpdate', function(event, result) {
            vm.popCompetition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
