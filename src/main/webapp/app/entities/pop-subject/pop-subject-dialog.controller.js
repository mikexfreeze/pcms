(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopSubjectDialogController', PopSubjectDialogController);

    PopSubjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopSubject', 'PopCompetition'];

    function PopSubjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopSubject, PopCompetition) {
        var vm = this;

        vm.popSubject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.popcompetitions = PopCompetition.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popSubject.id !== null) {
                PopSubject.update(vm.popSubject, onSaveSuccess, onSaveError);
            } else {
                PopSubject.save(vm.popSubject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popSubjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
