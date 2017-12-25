(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopPictureDialogController', PopPictureDialogController);

    PopPictureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopPicture', 'PopContribute'];

    function PopPictureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopPicture, PopContribute) {
        var vm = this;

        vm.popPicture = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.popPicture.id !== null) {
                PopPicture.update(vm.popPicture, onSaveSuccess, onSaveError);
            } else {
                PopPicture.save(vm.popPicture, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popPictureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.shootDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
