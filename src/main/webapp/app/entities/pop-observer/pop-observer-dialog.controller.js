(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopObserverDialogController', PopObserverDialogController);

    PopObserverDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopObserver', 'PopAppraise'];

    function PopObserverDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopObserver, PopAppraise) {
        var vm = this;

        vm.popObserver = entity;
        vm.clear = clear;
        vm.save = save;
        vm.popappraises = PopAppraise.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popObserver.id !== null) {
                PopObserver.update(vm.popObserver, onSaveSuccess, onSaveError);
            } else {
                PopObserver.save(vm.popObserver, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popObserverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
