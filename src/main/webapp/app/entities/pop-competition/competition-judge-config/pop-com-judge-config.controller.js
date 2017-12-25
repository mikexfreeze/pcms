/**
 * Created by zhaimaojin on 17/3/21.
 */
angular
    .module('pcmsApp')
    .controller('PopCompetitionJudgeConfigController', PopCompetitionJudgeConfigController);

PopCompetitionJudgeConfigController.$inject = ['$scope', '$stateParams', '$state', '$uibModal', '$q', 'toaster', 'PopJudgeConfig','CommonService'];

function PopCompetitionJudgeConfigController($scope, $stateParams, $state, $uibModal, $q, toaster, PopJudgeConfig, CommonService) {

    // console.log('tabPage页面路由参数：');
    // console.log($stateParams);
    if($stateParams.competitionId){
        $scope.competitionId = $stateParams.competitionId
    }else{
        $scope.competitionId = ""
    }

    if ($stateParams.subjectId) {
        $scope.subjectId = $stateParams.subjectId;
    } else {
        $scope.subjectId = ""
    }
    $scope.pAppraiseId = $stateParams.pAppraiseId;
    $scope.pAppraiseRound = $stateParams.pAppraiseRound;
    $scope.parentAppraiseId = $stateParams.parentAppraiseId;
    $scope.parentAppraiseRound = $stateParams.parentAppraiseRound;

    var modalInstance;
    $scope.addJudgeModel = function (id) {
        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/competition-judge-config-add/competition-judge-config-add.html',
            controller: 'JudgeConfigAddCtrl',
            controllerAs: '$ctrl',
            scope:$scope,
            resolve: {
                configId: function() {
                        return $scope.vm.id;
                    },
                judgeId: function () {
                    return id;
                }
            }
        });
        modalInstance.result.then(function () {
            fetchJudgesByConfigId($scope.vm.id);
        });
    };
    $scope.addObserver = function () {
        modalInstance = $uibModal.open({
            templateUrl: 'app/entities/pop-competition/components/competition-judge-config-add-observer/competition-judge-config-add-observer.html',
            controller: 'JudgeConfigAddObserverCtrl',
            controllerAs: '$ctrl',
            scope:$scope,
            resolve: {
                configId: function() {
                    return $scope.vm.id;
                },
                observerId: function () {
                    return 1;
                }
            }
        });
        modalInstance.result.then(function () {
            fetchObserversByConfigId($scope.vm.id);
        });
    };

    $scope.save = function () {
        saveConfig();
    };

    $scope.existingConfigs = [];
    $scope.availableAwards = [];
    var vm = {};
    $scope.vm = vm;

    vm.subjectId = $scope.subjectId;

    vm.round = 1;
    vm.appraiseType = "投票";
    vm.status = 'BEGIN';
    vm.pollNum = 0;
    $scope.isEditable = true;
    // $scope.awardConfig = {};
    $scope.$watch('awardConfig', function (newValue, oldValue) {
        if ($scope.awardConfig) {
            $scope.vm.awardConfigId = $scope.awardConfig.id;
            $scope.vm.awardName = $scope.awardConfig.name;
        }
    });

    function fetchAvailableAwards() {
        PopJudgeConfig.getAwardConfigsByCompetitionId($scope.competitionId)
            .then(function (result) {
                $scope.availableAwards = result.data;

                fetchAppraiseConfigDetails();

            }, function (error) {

            });
    }

    fetchAvailableAwards()



    function fetchAppraiseConfigDetails() {
        if ($scope.parentAppraiseId) {
            vm.parentAppraise = $scope.parentAppraiseId;
            vm.round = Number($scope.parentAppraiseRound);

        }
        else if ($scope.pAppraiseId) {
            // 下一轮
            vm.round = Number($scope.pAppraiseRound) + 1;
        } else {
            (function () {
                PopJudgeConfig.getLatestConfigBySubjectId($scope.subjectId)
                    .then(function (result) {
                        var lastest = result.data;
                        if (lastest) {
                            // 已经设置的轮次
                            $scope.vm = lastest;



                            if ($scope.availableAwards && $scope.vm.awardConfigId) {
                                $scope.availableAwards.unshift({id: '', name: ''});
                            }
                            $scope.isEditable = lastest.status=='BEGIN';
                            $scope.awardConfig = awardConfigForId($scope.vm.awardConfigId);
                            fetchObserversByConfigId($scope.vm.id);
                            fetchJudgesByConfigId($scope.vm.id);
                            console.log("subjectId")
                            console.log($scope.vm.subjectId)
                            console.log($scope.vm.id)
                            fetchContributeNum($scope.vm.id,$scope.vm.subjectId)
                        } else {
                            // 首轮未设置
                        }

                    }, function (error) {

                    });
            })();
        }

    }

    //获取作品总数

    function fetchContributeNum(appraiseId,subjectId) {
        PopJudgeConfig.findContributeNum(appraiseId,subjectId)
            .then(function (result) {
                console.log("number")
                console.log(result)
                $scope.worksNumber = result
            })
    }

    // function fetchExistingConfigs() {
    //     PopJudgeConfig.getExistingConfigsBySubjectId($scope.subjectId)
    //         .then(function (result) {
    //             var existingConfigs = result.data.content;
    //             if (mainConfig(existingConfigs)) {
    //                 $scope.vm = mainConfig(existingConfigs);
    //                 $scope.awardConfig = awardConfigForId($scope.vm.awardConfigId);
    //                 fetchObserversByConfigId($scope.vm.id);
    //                 fetchJudgesByConfigId($scope.vm.id);
    //             } else {
    //
    //             }
    //
    //         }, function (error) {
    //
    //         });
    // }
    //
    // fetchExistingConfigs();

    function saveConfig() {
        var existingId = vm.id;
        PopJudgeConfig.saveConfig($scope.vm)
            .then(function (result) {
                console.log(result)
                checkResult(result)
                    .then(function (result) {
                        vm.id = result.data.id;
                        if ($scope.pAppraiseId && !existingId) {
                            PopJudgeConfig.autoFillJudgesForNewRound(vm.round, $scope.subjectId, vm.id)
                                .then(function (result) {
                                    checkResult(result)
                                        .then(function (result) {
                                            fetchObserversByConfigId($scope.vm.id);
                                            fetchJudgesByConfigId($scope.vm.id);
                                            toaster.pop('info', "保存成功");
                                        });
                                });
                        } else {
                            fetchObserversByConfigId($scope.vm.id);
                            fetchJudgesByConfigId($scope.vm.id);
                            toaster.pop('info', "保存成功");
                        }
                    });
            }, function (error) {
                toaster.pop('error',"错误", error.msg);
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

    // function mainConfig(existingConfigs) {
    //     if (existingConfigs instanceof Array) {
    //         var s = existingConfigs;
    //         if (s.length > 0) {
    //             for (var i = 0; i < s.length; i++) {
    //                 if (!(s[i].parentAppraise > 0)) {
    //                     return s[i];
    //                 }
    //             }
    //         }
    //     }
    //
    //     return null;
    // }

    function awardConfigForId(id) {
        if ($scope.availableAwards instanceof Array) {
            var s = $scope.availableAwards;
            if (s.length > 0) {
                for (var i = 0; i < s.length; i++) {
                    if (s[i].id == id) {
                        return s[i];
                    }
                }
            }
        }

        return {};
    }

    $scope.judgeVM = {
        judges: [],
        removeJudge: removeJudge,
        editJudge: editJudge,
    };

    function fetchJudgesByConfigId (id) {
        PopJudgeConfig.getJudgesByConfigId(id)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        $scope.judgeVM.judges = result.data.content;
                    });
            }, function (error) {
                toaster.pop('error',"错误", result.msg);
            });
    }

    function removeJudge(judge) {
        CommonService.confirmModal()
            .then(function () {
                PopJudgeConfig.removeJudge(judge)
                    .then(function (result) {
                        checkResult(result)
                            .then(function (result) {
                                fetchJudgesByConfigId($scope.vm.id);
                            });
                    }, function (error) {
                        toaster.pop('error',"错误", result.msg);
                    });
            },function () {
            });

    }

    function editJudge(judge) {
        PopJudgeConfig.saveJudge(judge)
            .then(function (result) {
                console.log(result)
                checkResult(result)
                    .then(function (result) {
                        vm.id = result.data.id;
                        toaster.pop('info',"保存成功");
                    });
            }, function (error) {
                toaster.pop('error',"错误", result.msg);
            });
    }


    $scope.observerVM = {
        observers: [],
        removeObserver: removeObserver,

    };


    function fetchObserversByConfigId (id) {
        PopJudgeConfig.getObserversByConfigId(id)
            .then(function (result) {
                checkResult(result)
                    .then(function (result) {
                        $scope.observerVM.observers = result.data;
                    });
            }, function (error) {
                toaster.pop('error',"错误", result.msg);
            });
    }

    function removeObserver(observer) {
        CommonService.confirmModal()
            .then(function () {
                PopJudgeConfig.removeObserver(observer)
                    .then(function (result) {
                        checkResult(result)
                            .then(function (result) {
                                fetchObserversByConfigId($scope.vm.id);
                            });
                    }, function (error) {
                        toaster.pop('error',"错误", result.msg);
                    });
            },function () {
            });
    }

    $scope.start = function () {
        if ($scope.vm.status != 'VOTING') {
            $scope.vm.status = 'VOTING';
        } else {
            enter();
            return;
        }
        PopJudgeConfig.saveConfig($scope.vm)
            .then(function (result) {
                console.log("设置轮次状态为开始");
                console.log(result);
                checkResult(result)
                    .then(function (result) {
                        vm.id = result.data.id;
                        toaster.pop('info',"保存成功");
                        enter();
                    });
            }, function (error) {
                toaster.pop('error',"错误", error.msg);
            });
    };

    $scope.cancel = function () {
        history.back();
    };

    function enter() {
        $state.go("judge-controll", {subjectId: $scope.subjectId, appraiseId: $scope.vm.id, competitionId: $scope.competitionId, branchAppraise:"trunk"});
    }

}
