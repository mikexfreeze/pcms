(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopContributeDialogController', PopContributeDialogController);

    PopContributeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopContribute', 'PopSubject'];

    function PopContributeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopContribute, PopSubject) {
        var vm = this;

        vm.popContribute = entity;
        vm.clear = clear;
        vm.save = save;
        vm.popsubjects = PopSubject.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popContribute.id !== null) {
                PopContribute.update(vm.popContribute, onSaveSuccess, onSaveError);
            } else {
                PopContribute.save(vm.popContribute, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popContributeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
