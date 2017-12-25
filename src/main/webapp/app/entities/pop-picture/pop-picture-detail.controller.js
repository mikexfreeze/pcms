(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopPictureDetailController', PopPictureDetailController);

    PopPictureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopPicture', 'PopContribute'];

    function PopPictureDetailController($scope, $rootScope, $stateParams, previousState, entity, PopPicture, PopContribute) {
        var vm = this;

        vm.popPicture = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pcmsApp:popPictureUpdate', function(event, result) {
            vm.popPicture = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
