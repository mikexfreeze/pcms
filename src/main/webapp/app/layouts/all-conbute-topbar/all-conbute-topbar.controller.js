/**
 * Created by Micheal Xiao on 2017/5/8.
 */
(function() {

    angular
        .module('pcmsApp')
        .controller('allConTopCtrl', allConTopCtrl);

    allConTopCtrl.$inject = ['Principal', '$scope', '$rootScope', '$stateParams', '$state', '$timeout', 'Auth', '$localStorage', 'topBarJudgeService'];

    function allConTopCtrl (Principal, $scope, $rootScope, $stateParams, $state, $timeout, Auth, $localStorage, topBarJudgeService) {

        if($stateParams.competitionId){
            $scope.competitionId = $stateParams.competitionId
        }else{
            $scope.competitionId = ""
        }

        if ($localStorage.user){
            $scope.roleArr = $localStorage.user.userLogin.authorities[1];
            if($scope.roleArr == 'ROLE_ADMIN' && $stateParams.role){
                $scope.showFlag = true;
                $scope.indexLink = '#'
            }else{
                $scope.showFlag = false;
                $rootScope.initClass = "allContribute";
                $scope.indexLink = 'http://118.187.50.42/content/popphoto/home.html'
            }
        }else {
            $scope.roleArr = false
        }

        console.log($scope.showFlag)

        Principal.identity().then(function(account) {
            // $scope.currentAccount = account;

        });

        $scope.delete = function () {
            $rootScope.$broadcast('delConbute')
        }


    }
})();
