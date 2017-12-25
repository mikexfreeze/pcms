(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopCompetitionController', PopCompetitionController)
        .filter("popCompetitonFilter",function () {
            return function(status) {
                var out = ' ';
                switch (status) {
                    case 'NOTSTART':
                        out = '未开始';
                        break;
                    case 'FILLING_IN':
                        out = '征稿中';
                        break;
                    case 'FILLING_END':
                        out = "已截稿";
                        break;
                    case 'LIVE_IN':
                        out = "直播中";
                        break;
                    case 'SELECTION':
                        out = "评选中";
                        break;
                    case 'FILLING_PUBLIC':
                        out = "公示中";
                        break;
                    case 'FILLING_RELA':
                        out = "已公布"
                        break;
                    case "FILLING_DELAY":
                        out = "延期截稿";
                        break;
                }
                return out;
            }
    });

    PopCompetitionController.$inject = ['$scope', '$state', 'PopCompetition', '$timeout', '$stateParams', '$uibModal', '$q', 'CommonService'];

    function PopCompetitionController ($scope, $state, PopCompetition, $timeout, $stateParams, $uibModal, $q, CommonService) {

        var queryData = {page: 0};
        $scope.status = "";
        getCompetitionByPage();

        $scope.itemsPerPage = 10;

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
            console.log(pageNo)
        };

        $scope.pageChanged = function () {
            getCompetitionByPage({page:$scope.currentPage - 1});
        };

        $scope.getByKeyword = function () {
            $timeout(function () {
                getCompetitionByPage({keyword:$scope.keyword})
            },1000)
        };

        $scope.getByStatus = function () {
            getCompetitionByPage({status:$scope.status})
        };


        function getCompetitionByPage(query) {
            if(query){
                angular.extend(queryData, query)
            }
            PopCompetition.getCompetition(queryData)
                .then(function (result) {
                    $scope.popCompetition = result.data.content;
                    $scope.totalItems = result.data.totalElements;
                    $scope.totalPages = result.data.totalPages;
                });
        }

        // $scope.deleteCompetition = function (id) {
        //     $scope.id = id;
        //     PopCompetition.deleteCompetition($scope.id);
        //     $state.go('pop-competition', null, { reload: 'pop-competition' });
        // };
        // var modalInstance;

        $scope.delModel = function (id) {
            $scope.editorId = "";
            $scope.flag = "";
            CommonService.confirmModal()
                .then(
                    function () {
                    console.log("确认");
                    $scope.editorid = id;
                    console.log("获取enditorId");
                    console.log($scope.editorid);
                    PopCompetition.deleteCompetition($scope.editorid);
                    $state.go('pop-competition', null, { reload: 'pop-competition' });

                },function () {
                    console.log("取消");
                })
        };

        $scope.go = function (id) {
            $state.go('pop-competition.tabPage',{ competitionId:id});
            $state.go('com-tab-page-editor',{ competitionId:id})
        };

        // function confirmModal() {
        //     var modalInstance = $uibModal.open({
        //         templateUrl: 'app/entities/pop-competition/components/delete/del-modal.html',
        //         controller: 'DelModalController',
        //         size: 'sm',
        //         scope: $scope,
        //     });
        //     return modalInstance.result.then(function (flag) {
        //         console.log(("获取flag"));
        //         console.log(flag.$value);
        //         if (flag.$value) {
        //             return $q.resolve()
        //         } else {
        //             return $q.reject()
        //         }
        //     });
        // }




    }



})();





