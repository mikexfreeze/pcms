(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopCompetitionDeleteController',PopCompetitionDeleteController);

    PopCompetitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopCompetition'];

    function PopCompetitionDeleteController($uibModalInstance, entity, PopCompetition) {
        var vm = this;

        vm.popCompetition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopCompetition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
