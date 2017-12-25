/**
 * Created by Micheal Xiao on 2017/5/7.
 */
angular
    .module('pcmsApp')
    .controller('publishPrizeNameEditor', publishPrizeNameEditor);

publishPrizeNameEditor.$inject = ['$scope', '$uibModal', '$uibModalInstance', 'PopTheme', '$timeout', 'PopPublish'];

function publishPrizeNameEditor($scope, $uibModal, $uibModalInstance, PopTheme, $timeout, PopPublish) {
    var $ctrl = this;

    console.info("$scope")
    console.log($scope.y)

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    PopPublish.getUserName($scope.y.userId)
        .then(function (result) {
            $scope.realname = result.realname;
        });

    $scope.updateNames = function () {
        $scope.onXHR = true;

        var nameData = {
            userId:$scope.y.userId,
            realname: $scope.realname
        };

        var titleData = {
            "id": $scope.y.contributeId,
            "title": $scope.y.contributeTitle
        };

        PopPublish.updateUserName(nameData)
            .then(function () {
                return PopPublish.updateContubeName(titleData)
            })
            .then(function () {
                $uibModalInstance.dismiss('cancel')
            })
            .finally(function () {
                $scope.onXHR = false;
            });



        // if(!$scope.editorId){
        //     PopTheme.createTheme(queryData)
        //         .then(function () {
        //             $timeout(function () {
        //                 $uibModalInstance.close()
        //             },100)
        //         })
        //         .finally(function () {
        //             $scope.onXHR = false;
        //         })
        //
        // }else{
        //     queryData.id = $scope.id;
        //     PopTheme.updateTheme(queryData)
        //         .then(function () {
        //             $timeout(function () {
        //                 $uibModalInstance.close()
        //             },100)
        //         })
        //         .finally(function () {
        //             $scope.onXHR = false;
        //         })
        // }


    }

}
