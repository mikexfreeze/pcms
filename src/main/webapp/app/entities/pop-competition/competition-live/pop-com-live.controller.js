/**
 * Created by Micheal Xiao on 2017/3/15.
 */
angular
    .module('pcmsApp')
    .controller('CompetitionLiveCtrl', CompetitionLiveCtrl);


CompetitionLiveCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal','$filter', 'PopCompetition','PopLive','CommonService']

function CompetitionLiveCtrl($scope, $stateParams, $state, $uibModal,$filter, PopCompetition,PopLive,CommonService) {
    var modalInstance;
    $scope.flag = false;
    $scope.host = "主持人姓名";
    var competitionId = $scope.competitionId;

    console.log("date");
    $scope.comData= {};


    $scope.addLiveModel = function () {
        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/new-live/live-editor.html',
            controller: 'LiveEditorCtrl',
            scope:$scope,
        }).result.then(function () {
            getGuestByLiveId($scope.liveId)
        })
    };

    getLiveByCompetitionId(competitionId)
        .then(function () {
            getHostByUserId($scope.liveId)
            getGuestByLiveId($scope.liveId)
        })




    //  根据competitionid查询live

    function getLiveByCompetitionId(id) {
        return PopLive.queryLive(id)
            .then(function (result) {
                console.log("live信息");
                console.log(result)
                $scope.flagNum = result.data[0].id;
                $scope.liveId = result.data[0].id;
                $scope.liveDate = result.data[0].liveDate;
                $scope.inputStartDate =  moment(new Date($scope.liveDate));
                console.log($scope.inputStartDate);

            })
    }


    //根据liveid查询嘉宾
    function getGuestByLiveId(id) {
        PopLive.quryGuest(id)
            .then(function (result) {
                console.log("嘉宾信息")
                console.log(result.data)
                $scope.guests = result.data
            })
    }

    //根据liveid查询主持人
    function getHostByUserId(id) {
        PopLive.getHostByLiveId(id)
            .then(function (result) {
                console.log("host信息")
                console.log(result.data)
                $scope.hostId = result.data.content[0].id
                $scope.host = result.data.content[0].userName
            })
    }
    PopCompetition.getCompetitionById($scope.competitionId)
        .then(function (result) {
            $scope.competition = result.data;
            $scope.comData = result.data
            console.log("comdata")
            console.log($scope.comData.startDate)
        });

    $scope.addHost = function () {

        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/add-new-host/add-new-host.html',
            controller: 'AddHostCtrl',
            scope:$scope,
        }).result.then(function (userId) {
            $scope.hostId = userId;
            console.log("userId");
            console.log(userId);
            getHostByUserId($scope.hostId)
        })
    }
    $scope.addGuest = function () {
        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/add-guest/add-guest.html',
            controller: 'AddGuestCtrl',
            scope:$scope,
        }).result.then(function () {
        })
    }

    //删除嘉宾
    $scope.delGuest = function (id) {
        CommonService.confirmModal()
            .then(function () {
                PopLive.deleteGuest(id)
                    .then(function () {
                        getGuestByLiveId($scope.liveId)

                    })
            })
    }

    //删除主持人
    $scope.delHost = function (id) {
        CommonService.confirmModal()
            .then(function () {
                PopLive.deleteHost(id)
                    .then(function () {
                        $scope.hostId = false
                    })
            })
    }
    

    //进入直播
    $scope.startLiveStatus = function () {
        $scope.comData.status = "LIVE_IN";
        console.log("------------")
        console.log($scope.comData)
        PopCompetition.updateCompetitionStatus($scope.comData)
            .then(function (result) {
                console.log("开启直播")
                console.log(result)
            })
    }

    $scope.savePopLive = function (id) {
        console.log("dddd")
        console.log($scope.liveId)
        $scope.startDate = moment($scope.inputStartDate).format("YYYY-MM-DDTHH:mm:ss");
        console.log("日期格式化之后")
        console.log($scope.startDate)
        var queryData = {
            "competitionId": id,
            "host": $scope.host,
            "hostId": $scope.hostId,
            "liveDate": $scope.startDate,
            // "remark": "string"
        };

        var updateData = {
            "competitionId": id,
            "host": 'test',
            "hostId": $scope.hostId,
            "liveDate": $scope.startDate,
            "id":$scope.liveId
        }

        if($scope.liveId){

            PopLive.updateLive(updateData)
                .then(function (result) {
                    console.log("更新live")
                    console.log(result)
                    // $scope.startId = result.data.id
                    // $scope.flagNum = result.data.id
                })
        }else{
        PopLive.addLive(queryData)
            .then(function (result) {
                console.log("+++ live")
                console.log(result.data.id)
                // $scope.startId = result.data.id
                $scope.flagNum = result.data.id
            })
        }
    }


}
