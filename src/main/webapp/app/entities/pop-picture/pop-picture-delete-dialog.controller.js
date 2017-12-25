(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopPictureDeleteController',PopPictureDeleteController);

    PopPictureDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopPicture'];

    function PopPictureDeleteController($uibModalInstance, entity, PopPicture) {
        var vm = this;

        vm.popPicture = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopPicture.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
