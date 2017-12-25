/**
 * Created by zhaimaojin on 17/5/19.
 */
(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopLiveListCtrl', PopLiveListCtrl);

    PopLiveListCtrl.$inject = ['Principal', '$scope', '$state', 'PopJudge','$localStorage','PopLiveList'];

    function PopLiveListCtrl (Principal, $scope, $state, PopJudge,$localStorage,PopLiveList) {
        // var userId = $localStorage.user.userLogin.id
        // var roleArr = $localStorage.user.userLogin.authorities
        // var role = roleArr.join(',')
        $scope.userId = $localStorage.user.userLogin.id
        $scope.getLiveList = function() {
            var userId = $localStorage.user.userLogin.id
            var roleArr = $localStorage.user.userLogin.authorities
            var role = roleArr.join(',')
            PopLiveList.getPopLiveList(role,userId)
                .then(function (result) {
                    console.log("---")
                    // console.log(result.data)
                    $scope.list = result.data
                    console.log($scope.list)
                })
        }
        $scope.getLiveList();
    }
})();
