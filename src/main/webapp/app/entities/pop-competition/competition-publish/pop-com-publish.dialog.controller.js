/**
 * Created by Yue Gu on 2017/3/23.
 */
angular
    .module('pcmsApp')
    .controller('CompetitionsPublishDialogCtrl', CompetitionsPublishDialogCtrl);

CompetitionsPublishDialogCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal', '$uibModalInstance', '$q', 'toaster', 'PopJudgeConfig', 'User', 'configId', '$timeout'];

function CompetitionsPublishDialogCtrl($scope, $stateParams, $state, $uibModal, $uibModalInstance, $q, toaster, PopJudgeConfig, User, configId, $timeout) {
    var $ctrl = this;

    $ctrl.ok = function () {
        $uibModalInstance.close($ctrl.selected.item);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    var vm = this;
    $scope.vm = vm;
    vm.observers = [];
    vm.search = "";
    vm.fetchObserver = fetchObserver;
    vm.searchObservers = searchObservers;
    vm.selectedObserver = {};
    vm.observerSelected = observerSelected;

    vm.fetchObserver();
    vm.saveObserver = saveObserver;

    function fetchObserver () {
        User.query({
            page: 0,
            size: 9999,
            sort: ['login', 'asc']
        }, onSuccess, onError);
    }
    
    function searchObservers() {
        if (vm.search.length > 0) {
            PopJudgeConfig.searchJudgesByUsername(vm.search)
                .then(function (result) {
                    checkResult(result)
                        .then(function (result) {
                            vm.observers = result;
                        });
                });
        } else {
            fetchObserver();
        }
    }

    function onSuccess(data, headers) {
        // vm.links = ParseLinks.parse(headers('link'));
        // vm.totalItems = headers('X-Total-Count') - hiddenUsersSize;
        vm.observers = data;
    }

    function onError(error) {
        toaster.pop('err',error.message);
    }

    function observerSelected(observer) {
        vm.selectedObserver= observer;
    }

    function saveObserver() {
        var observer = {
            userId: vm.selectedObserver.id,
            userName: vm.selectedObserver.login,
            appraiseId: configId
        };
        PopJudgeConfig.saveObserver(observer)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        vm.id = result.data.id;
                        toaster.pop('info',"保存成功");
                        $timeout(function () {
                            $uibModalInstance.close();
                        },100)
                    });
            }, function (error) {
                toaster.pop('error',"错误", result.msg);
            });
    }

    function checkResult(result) {
        if(result.code > 1000){
            toaster.pop('error',"错误", result.msg);
            return $q.reject(result)
        }else{
            return $q.resolve(result)
        }
    }

}
