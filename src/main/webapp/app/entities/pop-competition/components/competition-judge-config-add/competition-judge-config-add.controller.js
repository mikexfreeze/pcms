/**
 * Created by Yue Gu on 2017/3/23.
 */
angular
    .module('pcmsApp')
    .controller('JudgeConfigAddCtrl', JudgeConfigAddCtrl);

JudgeConfigAddCtrl.$inject = ['$scope', '$stateParams', '$state', '$uibModal', '$uibModalInstance', '$q', 'toaster', 'PopJudgeConfig', 'User', 'configId', 'judgeId', '$timeout'];

function JudgeConfigAddCtrl($scope, $stateParams, $state, $uibModal, $uibModalInstance, $q, toaster, PopJudgeConfig, User, configId, judgeId, $timeout) {
    var $ctrl = this;

    $ctrl.ok = function () {
        $uibModalInstance.close($ctrl.selected.item);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.openColorPicker = function () {
        $(".hidden-colorpicke").focus();
    }

    var vm = this;
    $scope.vm = vm;
    vm.judges = [];
    vm.search = "";
    vm.fetchJudges = fetchJudges;
    vm.searchJudges = searchJudges;
    vm.fetchExistingJudgeById =  fetchExistingJudgeById;
    vm.selectedJudge = {};
    vm.judgeSelected = judgeSelected;
    vm.save = save;
    vm.updateJudge = updateJudge;

    vm.judge = {};

    vm.fetchJudges();
    if (judgeId) {
        vm.fetchExistingJudgeById(judgeId);
    }

    function fetchJudges () {
        User.getAdmins({
            userName: vm.search,
            roleNames: "'ROLE_JUDGE'"
        }, onSuccess, onError);
    }
    function searchJudges() {
        fetchJudges();
    }

    function fetchExistingJudgeById(id) {
        PopJudgeConfig.getJudgeById(id)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        console.log("judge")
                       vm.judge = result.data;
                    });
            });
        // vm.judge = {id: id, userId: 1, colorFlag: '#f71717', userName: '大神', appraiseId: 1};
    }
    


    function onSuccess(data, headers) {
        // vm.links = ParseLinks.parse(headers('link'));
        // vm.totalItems = headers('X-Total-Count') - hiddenUsersSize;
        vm.judges = data;
        console.log(data)
    }

    function onError(error) {
        toaster.pop('err',error.message);
    }

    function judgeSelected(judge) {
        vm.selectedJudge = judge;
        vm.judge.userId = judge.id;
        vm.judge.userName = judge.login;
        vm.judge.pollNum = 0;
    }

    function saveJudge() {
        var judge = {
            userId: vm.selectedJudge.id,
            userName: vm.selectedJudge.login,
            appraiseId: configId,
            colorFlag: vm.judge.colorFlag,
            voteStatus: 'NOT_SUBMITTED',
            pollNum: vm.judge.pollNum
        };
        PopJudgeConfig.saveJudge(judge)
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

    function updateJudge() {
        PopJudgeConfig.updateJudge(vm.judge)
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

    function save() {
        if (judgeId) {
            updateJudge();
        } else {
            saveJudge();
        }
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
