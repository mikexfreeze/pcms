(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopJudgeDialogController', PopJudgeDialogController);

    PopJudgeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopJudge', 'PopAppraise'];

    function PopJudgeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopJudge, PopAppraise) {
        var vm = this;

        vm.popJudge = entity;
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
            if (vm.popJudge.id !== null) {
                PopJudge.update(vm.popJudge, onSaveSuccess, onSaveError);
            } else {
                PopJudge.save(vm.popJudge, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popJudgeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
