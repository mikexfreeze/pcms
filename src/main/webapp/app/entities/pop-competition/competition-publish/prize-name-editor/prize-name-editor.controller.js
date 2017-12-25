/**
 * Created by zhaimaojin on 17/5/8.
 */

angular
    .module('pcmsApp')
    .controller('prizeNameEditorCtrl', prizeNameEditorCtrl);

prizeNameEditorCtrl.$inject = ['$scope', '$uibModal', '$uibModalInstance', 'PopTheme', '$timeout', 'PopPublish'];

function prizeNameEditorCtrl($scope, $uibModal, $uibModalInstance, PopTheme, $timeout, PopPublish) {
    var $ctrl = this;

    console.info("$scope")
    console.log("zzzzz")
    $scope.name = $scope.z.name

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    // PopPublish.getUserName($scope.z.userId)
    //     .then(function (result) {
    //         $scope.realname = result.realname;
    //     });

    $scope.updateNames = function () {
        $scope.onXHR = true;

        var nameData = {
            id:$scope.z.id,
            // realname: $scope.realname,
            name:$scope.name
        };

        var titleData = {
            "id": $scope.z.contributeId,
        };

        PopPublish.updatePrizeName(nameData)
            .then(function () {
                return PopPublish.updateContubeName(titleData)
            })
            .then(function () {
                $uibModalInstance.dismiss('cancel')
            })
            .finally(function () {
                $scope.onXHR = false;
            });
    }

}
