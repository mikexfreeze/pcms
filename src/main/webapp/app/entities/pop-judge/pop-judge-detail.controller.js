(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopJudgeDetailController', PopJudgeDetailController);

    PopJudgeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopJudge', 'PopAppraise'];

    function PopJudgeDetailController($scope, $rootScope, $stateParams, previousState, entity, PopJudge, PopAppraise) {
        var vm = this;

        vm.popJudge = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popJudgeUpdate', function(event, result) {
            vm.popJudge = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
