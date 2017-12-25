/**
 * Created by Micheal Xiao on 2017/3/15.
 */
angular
    .module('pcmsApp')
    .controller('CompetitionsCtrl', CompetitionsCtrl);

CompetitionsCtrl.$inject = ['$scope', '$stateParams', '$state', '$rootScope'];

function CompetitionsCtrl($scope, $stateParams, $state, $rootScope) {

    $scope.curState = $state.current;

    $rootScope.$on('$locationChangeStart', function(event, toUrl, fromUrl) {
        // console.log("路由改变");
        $scope.curState = $state.current;
        // console.log($scope);
        // $scope.tabPageIndex = function () {
        //     switch ($state.current.name){
        //         case "com-tab-page-editor":
        //             return 0;
        //             break;
        //         case "com-tab-page-theme":
        //             return 1;
        //             break;
        //         case "com-tab-page-prize":
        //             return 2;
        //             break;
        //         case "com-tab-page-publish":
        //             return 3;
        //             break;
        //         case "com-tab-page-live":
        //             return 4;
        //             break;
        //     }
        //
        // }();
    });


    if($stateParams.competitionId){
        $scope.competitionId = $stateParams.competitionId
    }else{
        $scope.competitionId = ""
    }

}
