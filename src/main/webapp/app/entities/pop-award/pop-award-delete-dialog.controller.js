(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAwardDeleteController',PopAwardDeleteController);

    PopAwardDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopAward'];

    function PopAwardDeleteController($uibModalInstance, entity, PopAward) {
        var vm = this;

        vm.popAward = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopAward.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
