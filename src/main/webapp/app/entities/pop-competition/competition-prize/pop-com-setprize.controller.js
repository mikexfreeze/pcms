/**
 * Created by zhaimaojin on 17/3/20.
 */
angular
    .module('pcmsApp')
    .controller('CompetitionSetPrizeCtrl', CompetitionSetPrizeCtrl);

CompetitionSetPrizeCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal', 'PopPrize', 'CommonService', 'PopCompetition'];

function CompetitionSetPrizeCtrl($scope, $stateParams, $state, $uibModal, PopPrize, CommonService, PopCompetition) {

    console.log('tabPage页面路由参数：');
    console.log($stateParams);

    if($stateParams.competitionId){
        $scope.competitionId = $stateParams.competitionId
    }else{
        $scope.competitionId = ""
    }

    PopCompetition.getCompetitionById($scope.competitionId)
        .then(function (result) {
            $scope.competition = result.data;
        });

    var modalInstance;
    $scope.createPrizeModal = function () {
        $scope.editorId = "";
        CommonService.modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/editor-prize/editor-prize.html',
            controller: 'PrizeEditorCtrl',
            scope: $scope,
        });
    };

    $scope.editorPrizeModal = function (id) {
        $scope.editorId = id;
        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/editor-prize/editor-prize.html',
            controller: 'PrizeEditorCtrl',
            scope: $scope,
        });
    };
    
    $scope.deletePrize = function (id) {
        CommonService.confirmModal()
            .then(function () {
                PopPrize.deletePrize(id)
                    .then(function () {

                    });
            },function () {
                console.log("取消")
            });
    };

    getPrizeList($scope.competitionId);
    function getPrizeList(id) {
        PopPrize.getPrize(id)
            .then(function (result) {
                $scope.prizes = result.data
            })
    }


}
