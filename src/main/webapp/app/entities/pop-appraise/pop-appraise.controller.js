(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .controller('PopAppraiseController', PopAppraiseController);

    PopAppraiseController.$inject = ['$scope', '$state', '$stateParams', '$window', '$uibModal', 'PopAppraise', 'topBarJudgeService', 'ParseLinks', 'AlertService'];

    function PopAppraiseController ($scope, $state, $stateParams, $window, $uibModal, PopAppraise, topBarJudgeService, AlertService) {

        $scope.subjectId = $stateParams.subjectId;
        $scope.competitionId = $stateParams.competitionId;

        var modalInstance;
        $scope.configAward = function (appraise) {
            modalInstance = $uibModal.open({
                templateUrl: 'app/entities/pop-appraise/pop-appraise-award-config/pop-appraise-award-config.html',
                controller: 'PopAppraiseAwardConfigCtrl',
                controllerAs: '$ctrl',
                scope:$scope,
                resolve: {
                    entity: function() {
                        return appraise;
                    }
                }
            });
            modalInstance.result.then(function () {
                loadAll();
            });
        };
        $scope.export = function (appraise) {
            $scope.onXHR = true;
            topBarJudgeService.exportAppraiseData(appraise.id, appraise.round, appraise.subjectId)
                .then(function (result) {
                    $scope.onXHR = false;
                    if (result.data) {
                        $window.open(result.data)
                    }
                });
        };

        $scope.generate = function () {
            $scope.onXHR = true;
            topBarJudgeService.finishSubjectById($scope.subjectId)
                .then(function (result) {
                    $scope.onXHR = false;
                    $state.go('com-tab-page-publish', {competitionId: $scope.competitionId});
                });
        };

        var vm = this;

        loadAll();

        function loadAll () {
            PopAppraise.getSubjectAppraises({
                "appId": "string",
                "channelId": "string",
                "data": {
                    "subjectId": $scope.subjectId
                }
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                console.log(data.data)
                vm.popAppraises = data.data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        $scope.vm = vm;

    }
})();
