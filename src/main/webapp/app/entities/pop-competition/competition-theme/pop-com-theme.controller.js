/**
 * Created by Micheal Xiao on 2017/3/17.
 */
angular
    .module('pcmsApp')
    .controller('CompetitionThemeCtrl', CompetitionThemeCtrl);

CompetitionThemeCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal','PopTheme','CommonService', 'PopCompetition'];

function CompetitionThemeCtrl($scope, $stateParams, $state, $uibModal,PopTheme, CommonService, PopCompetition) {

    var modalInstance;
    $scope.addThemeModel = function () {
        $scope.editorId = "";
        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/new-theme/theme-editor.html',
            controller: 'ThemeEditorCtrl',
            controllerAs: '$ctrl',
            scope: $scope,
        });
    };

    $scope.updateThemeModel = function (id) {
        $scope.editorId = id;
        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/new-theme/theme-editor.html',
            controller: 'ThemeEditorCtrl',
            controllerAs: '$ctrl',
            scope: $scope,
        });
    };

    var  competitionId=$scope.competitionId;
    //请求参数
    getTheme(competitionId);
    function getTheme(id) {
        PopTheme.getTheme(id)
            .then(function (result) {
                $scope.themeData = result.data;
            });
    }

    PopCompetition.getCompetitionById(competitionId)
        .then(function (result) {
            $scope.competition = result.data;
        });

    //删除
    $scope.deleteTheme = function (id) {
        CommonService.confirmModal()
            .then(function () {
                PopTheme.deleteTheme(id);
            },function () {
                console.log("取消");
            })

    };

    // 全部重置
    $scope.resetAll = function (id) {
        CommonService.judgeConfirmModal('该操作将删除所有本主题下的评选信息，确定继续？')
            .then(function () {
                PopTheme.deleteThemeAppraises(id);
            },function () {
                console.log("取消");
            });
    };

    // 重启评选
    $scope.reset = function (id) {
        CommonService.judgeConfirmModal('该操作将重新启动评选过程，确定继续？')
            .then(function () {
                PopTheme.resetSubject(id);
            },function () {
                console.log("取消");
            });
    };

}
