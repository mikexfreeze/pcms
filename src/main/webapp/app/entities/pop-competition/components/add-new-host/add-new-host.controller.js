/**
 * Created by zhaimaojin on 17/5/11.
 */

angular
    .module('pcmsApp')
    .controller('AddHostCtrl', AddHostCtrl);

AddHostCtrl.$inject = ['$scope', '$uibModal', '$uibModalInstance', '$timeout','PopLive','User','$q','toaster'];

function AddHostCtrl($scope, $uibModal, $uibModalInstance, $timeout,PopLive,User,$q,toaster) {
    $scope.hostColor = '#d32f2f'
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    }

    console.log($scope.liveId)
    console.log($scope.hostId)
    $scope.save = function () {
        console.log($scope.competitionId)
        var queryData = {
            "liveId":$scope.liveId,
            "userId":$scope.userId
        };
        console.log(queryData);
        PopLive.addHost(queryData)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        $scope.hosts = result;
                        console.log("添加成功")
                        console.log($scope.hosts)
                    }).then(function () {
                    $uibModalInstance.close($scope.liveId);
                })
            })
    };


    function fetchHost () {
        User.getAdmins({
            userName: '',
            roleNames: "'ROLE_HOST'"
        }, onSuccess, onError);
    }
    function onSuccess(data, headers) {
        // vm.links = ParseLinks.parse(headers('link'));
        // vm.totalItems = headers('X-Total-Count') - hiddenUsersSize;
        console.log('111')
        console.log(data)
        $scope.hosts = data
    }

    function onError(error) {
        toaster.pop('err',error.message);
    }
    // $scope.searchHost = function(){
    //     fetchHost();
    // }
    fetchHost();
    $scope.hostSelected = function(data) {
        $scope.userId = data.id;
        $scope.userName = data.login;

    }
    function checkResult(result) {
        if(result.code > 1000){
            toaster.pop('error',"错误", result.msg);
            return $q.reject(result)
        }else{
            return $q.resolve(result)
        }
    }

}
