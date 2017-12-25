/**
 * Created by Micheal Xiao on 2017/3/17.
 */
angular
    .module('pcmsApp')
    .controller('ThemeEditorCtrl', ThemeEditorCtrl);

ThemeEditorCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal', '$uibModalInstance', 'PopTheme', '$timeout'];

function ThemeEditorCtrl($scope, $stateParams, $state, $uibModal, $uibModalInstance, PopTheme, $timeout) {
    var $ctrl = this;
    console.log($scope.competitionId);

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    if($scope.editorId){
        PopTheme.getThemeById($scope.editorId)
            .then(function (result) {
                angular.extend($scope, result.data)
            })
    }

    $scope.updateSubject = function () {
        var queryData = {
            "competitionId": $scope.competitionId,
            "groupMaxLimit": $scope.groupMaxLimit,
            "id":"",
            "maxLimit": $scope.maxLimit,
            "name": $scope.name,
        };

        $scope.onXHR = true;
        if(!$scope.editorId){
            PopTheme.createTheme(queryData)
                .then(function () {
                    $timeout(function () {
                        $uibModalInstance.close()
                    },100)
                })
                .finally(function () {
                    $scope.onXHR = false;
                })

        }else{
            queryData.id = $scope.id;
            PopTheme.updateTheme(queryData)
                .then(function () {
                    $timeout(function () {
                        $uibModalInstance.close()
                    },100)
                })
                .finally(function () {
                    $scope.onXHR = false;
                })
        }


    }

}
