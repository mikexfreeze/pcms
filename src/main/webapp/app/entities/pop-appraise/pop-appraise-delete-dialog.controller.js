(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAppraiseDeleteController',PopAppraiseDeleteController);

    PopAppraiseDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopAppraise'];

    function PopAppraiseDeleteController($uibModalInstance, entity, PopAppraise) {
        var vm = this;

        vm.popAppraise = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopAppraise.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
