(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAppraiseDialogController', PopAppraiseDialogController);

    PopAppraiseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopAppraise', 'PopSubject'];

    function PopAppraiseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopAppraise, PopSubject) {
        var vm = this;

        vm.popAppraise = entity;
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
            if (vm.popAppraise.id !== null) {
                PopAppraise.update(vm.popAppraise, onSaveSuccess, onSaveError);
            } else {
                PopAppraise.save(vm.popAppraise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popAppraiseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
