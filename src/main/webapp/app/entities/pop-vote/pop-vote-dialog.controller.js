(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopVoteDialogController', PopVoteDialogController);

    PopVoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PopVote', 'PopJudge', 'PopContribute', 'PopAppraise'];

    function PopVoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PopVote, PopJudge, PopContribute, PopAppraise) {
        var vm = this;

        vm.popVote = entity;
        vm.clear = clear;
        vm.save = save;
        vm.popjudges = PopJudge.query();
        vm.contributes = PopContribute.query({filter: 'popvote-is-null'});
        $q.all([vm.popVote.$promise, vm.contributes.$promise]).then(function() {
            if (!vm.popVote.contributeId) {
                return $q.reject();
            }
            return PopContribute.get({id : vm.popVote.contributeId}).$promise;
        }).then(function(contribute) {
            vm.contributes.push(contribute);
        });
        vm.popappraises = PopAppraise.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popVote.id !== null) {
                PopVote.update(vm.popVote, onSaveSuccess, onSaveError);
            } else {
                PopVote.save(vm.popVote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popVoteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
