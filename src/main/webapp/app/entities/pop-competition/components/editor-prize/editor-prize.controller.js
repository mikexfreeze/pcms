/**
 * Created by Micheal Xiao on 2017/3/22.
 */

angular
    .module('pcmsApp')
    .controller('PrizeEditorCtrl', PrizeEditorCtrl);

PrizeEditorCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal', '$uibModalInstance', 'PopPrize', '$timeout', 'UploadService'];

function PrizeEditorCtrl($scope, $stateParams, $state, $uibModal, $uibModalInstance, PopPrize, $timeout, UploadService) {
    var competitionId = console.log($stateParams.competitionId);

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    if($scope.editorId){
        PopPrize.getPrizeById($scope.editorId)
            .then(function (result) {
                angular.extend($scope, result.data)
            })
    }

    $scope.updatePrize = function () {
        var queryData = {
            "competitionId":$stateParams.competitionId,
            "name": $scope.name,
            "medalUrl": $scope.upLoadedImg
        };

        $scope.onXHR = true;
        if(!$scope.editorId){
            PopPrize.createPrize(queryData)
                .then(function () {
                    $timeout(function () {
                        $uibModalInstance.close()
                    },100)
                })
                .finally(function () {
                    $scope.onXHR = false;
                })

        }else{
            queryData.id = $scope.editorId;
            PopPrize.updatePrize(queryData)
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

    $scope.upLoadimg = function () {
        $scope.onXHR = true;
        UploadService.upLoadImg($scope.upLoadImg)
            .then(function (result) {
                $scope.upLoadedImg = result.data;
            })
            .then(function () {
                $scope.onXHR = false;
            });
    };

}
