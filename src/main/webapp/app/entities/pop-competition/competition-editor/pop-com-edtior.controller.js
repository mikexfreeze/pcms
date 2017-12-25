/**
 * Created by Micheal Xiao on 2017/3/16.
 */
angular
    .module('pcmsApp')
    .controller('CompetitionEditorCtrl', CompetitionEditorCtrl);

CompetitionEditorCtrl.$inject = ['$scope', '$state', 'PopCompetition', 'Member', 'Message', 'AlertService', '$filter', 'toaster', 'moment', '$window', 'UploadService'];

function CompetitionEditorCtrl ($scope, $state, PopCompetition, Member, Message, AlertService, $filter, toaster, moment, $window, UploadService){

    $scope.comData= {
        "articleType": "SINGLE_GROUP",
        "assetDir": "",
        "competitionType": "ONLINE",
        // "contentUrl": "",
        "id": "",
        // "remark": "",
        "resultUrl": "",
        // "startDate": "",
        "status": "NOTSTART",
        // "stopDate": "",
        "title": ""
    };

    function isNoNeedToDelay() {
        return $scope.comData.inputStopDate.isBefore(moment());
    }

    // $scope.$watch('comData', function (newValue, oldValue) {
    //     console.log(newValue);
    //     if ($scope.comData.inputStopDate) {
    //         $scope.isNoNeedToDelay = isNoNeedToDelay();
    //     }
    // });

    $scope.changeStopDate = function () {
        // console.log($scope.comData.inputStopDate)
        $scope.isNoNeedToDelay = isNoNeedToDelay();
    };

    if($scope.competitionId){
        PopCompetition.getCompetitionById($scope.competitionId)
            .then(function (result) {
                $scope.comData = result.data;
                $scope.comData.inputStartDate = moment(new Date($scope.comData.startDate));
                $scope.comData.inputStopDate = moment(new Date($scope.comData.stopDate));
            })
    }

    $scope.update = function () {
        $scope.onXHR = true;
        // $scope.comData.startDate = $filter('date')($scope.comData.inputStartDate, "yyyy-MM-ddTHH:mm:ss");
        $scope.comData.startDate = moment($scope.comData.inputStartDate).format("YYYY-MM-DDTHH:mm:ss");
        // $scope.comData.stopDate = $filter('date')($scope.comData.inputStopDate, "yyyy-MM-ddTHH:mm:ss");
        $scope.comData.stopDate = moment($scope.comData.inputStopDate).format("YYYY-MM-DDTHH:mm:ss");
        console.log($scope.comData.status)
        console.log($scope.comData);
        if(!$scope.competitionId){
            PopCompetition.createCompetition($scope.comData)
                .finally(function () {
                    $scope.onXHR = false;
                })
        }else{
            PopCompetition.updateCompetition($scope.comData)
                .finally(function () {
                    $scope.onXHR = false;
                })
        }
    }

    $scope.delayCompetition = function () {
        $scope.comData.startDate = $filter('date')($scope.comData.inputStartDate, "yyyy-MM-ddTHH:mm:ss");
        $scope.comData.stopDate = $filter('date')($scope.comData.inputStopDate, "yyyy-MM-ddTHH:mm:ss");
        $scope.comData.status = "FILLING_DELAY"
        PopCompetition.updateCompetitionStatus($scope.comData)
            .then(function (result) {
                console.log("延期成功")
                console.log(result)
            })

        sendDelayMessages();
    }

    $scope.exportCompetition = function () {
        PopCompetition.exportCompetition($scope.competitionId)
            .then(function (result) {
                console.log("导出成功");
                console.log(result);
                $window.open(result.data);
            });
    }

    function memberMessageFromUser(item, title, content) {
        return {
            status: 'UNREAD',
            content: content,
            title: title,
            userId: item.userId,
            // memberId: user.id,

        };
    }

    function sendDelayMessages() {
        Member.competitionUsers({
            "appId": "string",
            "channelId": "string",
            "data": {
                competitionId: $scope.competitionId
            }
        }, function (data) {
            console.log('所有活动相关用户：' + data);
            var users = data.data;
            users.forEach(function (item, index) {
                Message.save(memberMessageFromUser(item, '延期截稿通知', '活动"' + $scope.comData.title + '"已延期截稿至：' + moment($scope.comData.stopDate).format("YYYY年MM月DD日 HH:mm") + '。'));
            });
        }, function (error) {

        });
    }
    
    $scope.importCompetition = function (upLoadfile) {
        UploadService.uploadCompetition(upLoadfile)
    }

}


