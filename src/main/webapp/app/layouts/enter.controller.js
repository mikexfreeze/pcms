/**
 * Created by Yue Gu on 2017/4/20.
 */
(function() {

    angular
        .module('pcmsApp')
        .controller('enterCtrl', enterCtrl);

    enterCtrl.$inject = ['$scope', '$rootScope', 'Enter', '$stateParams'];

    function enterCtrl ($scope, $rootScope, Enter, $stateParams) {

        if($stateParams.branchAppraise){
            $scope.flowType = $stateParams.branchAppraise
        }else{
            $scope.flowType = "trunk";
        }

        $rootScope.$on('enterAppraise', function (event, appraiseId) {
            console.log(":d")
            if ($scope.bool === true) {
                enter(appraiseId);
            }
        });
        $rootScope.$on('abandonAppraise', function (event, appraiseId) {
            if ($scope.bool) {
                abandon(appraiseId);
            }
        });
        $rootScope.$on('deleteAppraise', function (event) {
            if ($scope.bool) {
                dele();
            }
        });
        $rootScope.$on('branchAppraise', function (event, appraiseId) {
            if ($scope.bool) {
                branch(appraiseId);
            }
        });



        Enter.getEnter($scope.appraiseId, $scope.img.contributeId,$scope.flowType)
            .then(function (result) {
                if (result.data) {
                    // console.log('获取入选状态' + result.data.id);
                    $scope.enterId = result.data.id;
                } else {
                    $scope.enterId = null;
                }
            });
        function enter(appraiseId) {
            // if ($scope.enterId) {
            //     Enter.unEnterAppraise($scope.enterId)
            //         .then(function (result) {
            //             $scope.enterId = null;
            //             $scope.bool = false;
            //         });
            //
            // } else {
            // console.log("$scope.img.contributeId");
            // console.log($scope.img.contributeId);

                Enter.enterAppraise(appraiseId, $scope.img.contributeId, $scope.flowType)
                    .then(function (result) {
                        console.log("res")
                        console.log(result)
                        $scope.enterId = result.data.id;
                        $scope.bool = false;
                        $rootScope.$broadcast('enter');
                    });

            // }
        }

        function abandon(appraiseId) {
            Enter.abandonAppraise(appraiseId, $scope.img.contributeId)
                .then(function (result) {
                    $scope.img.mask = {
                        show:true,
                        text:'已弃选'
                    }

                    $rootScope.$broadcast('enter');
                });
        }

        function dele() {
            Enter.deleteAppraise($scope.appraiseId, $scope.img.contributeId)
                .then(function (result) {
                    $scope.img.mask = {
                        show:true,
                        text:'已删除'
                    }

                    $rootScope.$broadcast('enter');
                });
        }

        function branch(appraiseId) {
            Enter.branchAppraise(appraiseId, $scope.img.contributeId)
                .then(function (result) {
                    $scope.img.mask = {
                        show:true,
                        text:'分支'
                    }

                    $rootScope.$broadcast('enter');
                });
        }

    }
})();
