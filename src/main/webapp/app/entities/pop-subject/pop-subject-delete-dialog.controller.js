(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopSubjectDeleteController',PopSubjectDeleteController);

    PopSubjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopSubject'];

    function PopSubjectDeleteController($uibModalInstance, entity, PopSubject) {
        var vm = this;

        vm.popSubject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopSubject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
