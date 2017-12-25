(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAwardConfigDialogController', PopAwardConfigDialogController);

    PopAwardConfigDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopAwardConfig', 'PopSubject'];

    function PopAwardConfigDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopAwardConfig, PopSubject) {
        var vm = this;

        vm.popAwardConfig = entity;
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
            if (vm.popAwardConfig.id !== null) {
                PopAwardConfig.update(vm.popAwardConfig, onSaveSuccess, onSaveError);
            } else {
                PopAwardConfig.save(vm.popAwardConfig, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popAwardConfigUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
