(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAwardConfigDeleteController',PopAwardConfigDeleteController);

    PopAwardConfigDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopAwardConfig'];

    function PopAwardConfigDeleteController($uibModalInstance, entity, PopAwardConfig) {
        var vm = this;

        vm.popAwardConfig = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopAwardConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
