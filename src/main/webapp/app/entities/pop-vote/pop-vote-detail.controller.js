(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopVoteDetailController', PopVoteDetailController);

    PopVoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopVote', 'PopJudge', 'PopContribute', 'PopAppraise'];

    function PopVoteDetailController($scope, $rootScope, $stateParams, previousState, entity, PopVote, PopJudge, PopContribute, PopAppraise) {
        var vm = this;

        vm.popVote = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popVoteUpdate', function(event, result) {
            vm.popVote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
