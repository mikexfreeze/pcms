(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopObserverDeleteController',PopObserverDeleteController);

    PopObserverDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopObserver'];

    function PopObserverDeleteController($uibModalInstance, entity, PopObserver) {
        var vm = this;

        vm.popObserver = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopObserver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
