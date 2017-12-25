(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAppraiseArticleDeleteController',PopAppraiseArticleDeleteController);

    PopAppraiseArticleDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopAppraiseArticle'];

    function PopAppraiseArticleDeleteController($uibModalInstance, entity, PopAppraiseArticle) {
        var vm = this;

        vm.popAppraiseArticle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopAppraiseArticle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
