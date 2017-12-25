(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAwardDialogController', PopAwardDialogController);

    PopAwardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopAward', 'PopContribute'];

    function PopAwardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopAward, PopContribute) {
        var vm = this;

        vm.popAward = entity;
        vm.clear = clear;
        vm.save = save;
        vm.popcontributes = PopContribute.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popAward.id !== null) {
                PopAward.update(vm.popAward, onSaveSuccess, onSaveError);
            } else {
                PopAward.save(vm.popAward, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popAwardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
