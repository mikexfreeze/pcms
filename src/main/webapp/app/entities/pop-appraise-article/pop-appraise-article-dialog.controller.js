(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAppraiseArticleDialogController', PopAppraiseArticleDialogController);

    PopAppraiseArticleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopAppraiseArticle', 'PopAppraise', 'PopContribute'];

    function PopAppraiseArticleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopAppraiseArticle, PopAppraise, PopContribute) {
        var vm = this;

        vm.popAppraiseArticle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.popappraises = PopAppraise.query();
        vm.popcontributes = PopContribute.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popAppraiseArticle.id !== null) {
                PopAppraiseArticle.update(vm.popAppraiseArticle, onSaveSuccess, onSaveError);
            } else {
                PopAppraiseArticle.save(vm.popAppraiseArticle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pcmsApp:popAppraiseArticleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
