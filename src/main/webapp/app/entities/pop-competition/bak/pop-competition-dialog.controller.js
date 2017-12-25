(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopCompetitionDialogController', PopCompetitionDialogController)


    PopCompetitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopCompetition'];

    function PopCompetitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopCompetition) {
        var vm = this;

        vm.popCompetition = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popCompetition.id !== null) {
                PopCompetition.update(vm.popCompetition, onSaveSuccess, onSaveError);
            } else {
                PopCompetition.save(vm.popCompetition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popCompetitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.stopDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
