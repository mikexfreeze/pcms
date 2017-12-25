(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAppraiseArticleDetailController', PopAppraiseArticleDetailController);

    PopAppraiseArticleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopAppraiseArticle', 'PopAppraise', 'PopContribute'];

    function PopAppraiseArticleDetailController($scope, $rootScope, $stateParams, previousState, entity, PopAppraiseArticle, PopAppraise, PopContribute) {
        var vm = this;

        vm.popAppraiseArticle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popAppraiseArticleUpdate', function(event, result) {
            vm.popAppraiseArticle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
