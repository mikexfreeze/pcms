(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopVoteDeleteController',PopVoteDeleteController);

    PopVoteDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopVote'];

    function PopVoteDeleteController($uibModalInstance, entity, PopVote) {
        var vm = this;

        vm.popVote = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopVote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
