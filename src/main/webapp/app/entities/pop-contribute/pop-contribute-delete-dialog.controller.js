(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopContributeDeleteController',PopContributeDeleteController);

    PopContributeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopContribute'];

    function PopContributeDeleteController($uibModalInstance, entity, PopContribute) {
        var vm = this;

        vm.popContribute = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopContribute.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
