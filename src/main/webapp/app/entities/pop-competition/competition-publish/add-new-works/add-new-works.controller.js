/**
 * Created by zhaimaojin on 17/5/8.
 */
angular
    .module('pcmsApp')
    .controller('addNewWorksCtrl', addNewWorksCtrl)
    .filter("contributeType",function () {
        return function(status) {
            var out = ' ';
            switch (status) {
                case 'SINGLE':
                    out = '单幅';
                    break;
                case 'GROUP':
                    out = "组照";
                    break;
            }
            return out;
        }
    });

addNewWorksCtrl.$inject = ['$scope', '$uibModal', '$uibModalInstance', 'PopTheme', '$timeout', 'PopPublish'];

function addNewWorksCtrl($scope, $uibModal, $uibModalInstance, PopTheme, $timeout, PopPublish) {
    var $ctrl = this;
    var queryUserId = 0;

    console.log("zzzzz");
    console.log($scope.z);
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.searchUser = function () {
        PopPublish.searchByRealname($scope.realname)
            .then(function (result) {
                $scope.usersList = result;
                $scope.conbuteList = ''

            })
    };

    $scope.searchConbute = function (userId) {
        queryUserId = userId;
        PopPublish.getContributeList({
            "author":userId,
            "subjectId":$scope.x.id
        }).then(function (result) {
            $scope.conbuteList = result.data.content
        })
    };

    $scope.addConAward = function (contributeId) {
        var queryData = {
            "content": "string",
            "name": $scope.z.name,
            "background": "string",
            "status": "PUBLISH",
            "contributeId": contributeId,
            "popAwardConfigId": $scope.z.id,
            "userId": queryUserId
        };

        PopPublish.addConToAward(queryData)
            .then(function () {

            })
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
            name:$scope.z.name,
            content:"string",
            background:"string",
            status:"PUBLISH",
            contributeID:$scope.y.contributeID
        };

        var titleData = {
            "id": $scope.z.contributeId,
        };

        PopPublish.addNewWorks(nameData)
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
