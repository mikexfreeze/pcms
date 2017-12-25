(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopJudgeDeleteController',PopJudgeDeleteController);

    PopJudgeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopJudge'];

    function PopJudgeDeleteController($uibModalInstance, entity, PopJudge) {
        var vm = this;

        vm.popJudge = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopJudge.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
