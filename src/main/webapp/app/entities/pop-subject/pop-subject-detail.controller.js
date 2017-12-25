(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopSubjectDetailController', PopSubjectDetailController);

    PopSubjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopSubject', 'PopCompetition'];

    function PopSubjectDetailController($scope, $rootScope, $stateParams, previousState, entity, PopSubject, PopCompetition) {
        var vm = this;

        vm.popSubject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popSubjectUpdate', function(event, result) {
            vm.popSubject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
