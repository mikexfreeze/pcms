/**
 * Created by zhaimaojin on 17/3/20.
 */
angular
    .module('pcmsApp')
    .controller('CompetitionsPublishCtrl', CompetitionsPublishCtrl)
    .controller('publishAwardsCtrl', publishAwardsCtrl)
    .controller('publishAwardsLevelCtrl', publishAwardsLevelCtrl)
    .controller('publishUserNameCtrl', publishUserNameCtrl)
    .filter('conType', function () {
        return function (input) {
            var out;
            switch (input) {
                case "SINGLE":
                    out = "单幅";
                    break;
                case "GROUP":
                    out = "组照";
                    break;
                default:
                    out = "";
                    break;
            }

            return out;
        };
    });

CompetitionsPublishCtrl.$inject = ['$scope', '$stateParams', '$state', 'PopPublish', 'PopTheme', 'Member', 'Message', 'PopCompetition', '$window'];

function CompetitionsPublishCtrl($scope, $stateParams, $state, PopPublish, PopTheme, Member, Message, PopCompetition, $window) {

    // console.log('tabPage页面路由参数：');
    // console.log($stateParams);
    if($stateParams.competitionId){
        $scope.competitionId = $stateParams.competitionId
    }else{
        $scope.competitionId = ""
    }

    PopCompetition.getCompetitionById($scope.competitionId)
        .then(function (result) {
            $scope.competition = result.data;
        });

    PopTheme.getTheme($scope.competitionId)
        .then(function (result) {
            $scope.themes = result.data;
        });


    //导出按钮
    $scope.exportWorks = function () {
        $scope.onXHR = true;
        PopPublish.exportWorks($scope.competitionId)
            .then(function (result) {
                $window.open(result.data)
            })
            .finally(function () {
                $scope.onXHR = false;
            })
    }

    $scope.announce = function () { // 公告
        $scope.$broadcast('announce');
        Member.competitionUsers({
            "appId": "string",
            "channelId": "string",
            "data": {
                competitionId: $scope.competitionId,
                type:"FILLING_RELA"

            }
        }, function (data) {
            console.log('所有活动相关用户：' + data);
            var users = data.data;
            users.forEach(function (item, index) {
                Message.save(memberMessageFromUser(item, '公布通告', '活动"' + $scope.competition.title + '"结果已经公布。'));
            });
        }, function (error) {

        });
    };

    $scope.publish = function () { // 公示
        // $scope.$broadcast('publish');
        Member.competitionUsers({
            "appId": "string",
            "channelId": "string",
            "data": {
                competitionId: $scope.competitionId,
                type:"FILLING_PUBLIC"
            }
        }, function (data) {
            console.log('所有活动相关用户投稿：' + data);
            var users = data.data;
            users.forEach(function (item, index) {
                Message.save(memberMessageFromUser(item, '公示通告', '活动"' + $scope.competition.title + '"结果已经公示。'));
            });
        }, function (error) {

        });
    };

    function memberMessageFromUser(item, title, content) {
        return {
            status: 'UNREAD',
            content: content,
            title: title,
            userId: item.userId,
            // memberId: user.id,

        };
    }

}

publishAwardsLevelCtrl.$inject = ['$scope', '$stateParams', '$state', 'PopPrize', '$uibModal'];

function publishAwardsLevelCtrl($scope, $stateParams, $state, PopPrize, $uibModal) {

    PopPrize.getPrize($scope.competitionId)
        .then(function (result) {
            $scope.Prizes = result.data;
            // console.log("prize");
            // console.log($scope.Prizes);
        });
    $scope.editThemeModel = function (data) {
        $scope.z = data;
        var modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/competition-publish/prize-name-editor/prize-name-editor.html',
            controller: 'prizeNameEditorCtrl',
            size: 'sm',
            scope: $scope,
        });
        return modalInstance.result.then(function (flag) {

        });
    };
    $scope.addThemeModel = function (data) {
        $scope.z = data;
        var modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/competition-publish/add-new-works/add-new-works.html',
            controller: 'addNewWorksCtrl',
            size: 'lg',
            scope: $scope,
        });
        return modalInstance.result.then(function (flag) {

        });
    }




}

//奖项列表 controller
publishAwardsCtrl.$inject = ['$scope', '$stateParams', '$state', 'PopPublish', 'PopTheme', '$uibModal','CommonService', 'Message'];

function publishAwardsCtrl($scope, $stateParams, $state, PopPublish, PopTheme, $uibModal,CommonService, Message) {

    var queryData = {
        "competitionId": $scope.competitionId,
        "subjectId":$scope.x.id,
        "awardConfigId":$scope.z.id
    };
    getPublish(queryData);
    function getPublish(queryData) {
        PopPublish.getPublish(queryData)
            .then(function (result) {
                console.log("awards")
                console.log(result)
                $scope.awards = result.data.content;
            });
    }

    $scope.editorNameModal = function (data) {
        $scope.y = data
        var modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/competition-publish/publish-editor/publish-editor-name.html',
            controller: 'publishPrizeNameEditor',
            size: 'sm',
            scope: $scope,
        });
        return modalInstance.result.then(function (flag) {

        });
    };
    $scope.deleteWorks = function (data) {
        CommonService.confirmModal()
            .then(function () {
                PopPublish.deleteWorksByContributeId(data);
            }, function () {
                console.log("取消");
            })
    }

    // 给获奖用户发送获奖消息
    $scope.$on('announce', function () {
        $scope.awards.forEach(function (item, index) {
            Message.save(memberMessageForAward(item, '获奖消息', '恭喜您的作品"' + item.contributeTitle + '"获得了"' + $scope.competition.title + '"活动"' + item.subjectName + '"主题"' + item.name + '"奖项。'));
        });
    })

    function memberMessageForAward(item, title, content) {
        return {
            status: 'UNREAD',
            content: content,
            title: title,
            userId: item.userId,
            // memberId: user.id,

        };
    }

}

publishUserNameCtrl.$inject = ['$scope', '$stateParams', '$state', 'PopPublish', 'PopTheme'];

function publishUserNameCtrl($scope, $stateParams, $state, PopPublish, PopTheme) {

    PopPublish.getUserName($scope.y.userId)
        .then(function (result) {
            $scope.user = result;
        });


}

