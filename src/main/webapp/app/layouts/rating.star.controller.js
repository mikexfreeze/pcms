/**
 * Created by Micheal Xiao on 2017/4/20.
 */
(function () {

    angular
        .module('pcmsApp')
        .controller('ratingStarCtrl', ratingStarCtrl);

    ratingStarCtrl.$inject = ['$scope', 'Vote', '$stateParams', 'topBarJudgeService', '$rootScope', 'toaster', '$q'];

    function ratingStarCtrl($scope, Vote, $stateParams, topBarJudgeService, $rootScope, toaster, $q) {
        $scope.starColor = "#ddd";
        $scope.appraiseId = $stateParams.appraiseId;
        $scope.judgeId = $stateParams.judgeId;


        // console.log("img")
        // console.log($scope.img)

        var queryData = {
            "appraiseId": $scope.appraiseId,
            "judgeId": $scope.judgeId,
            "contributeId": $scope.img.contributeId,
            "voteStatus": "NOT_SUBMITTED",
            "flowType": $scope.flowType
        };

        function getVote() {
            Vote.getVote(queryData)
                .then(function (result) {
                    if (result.data.content.length == 0) {
                        $scope.starColor = "#ddd"
                    } else {
                        $scope.starColor = result.data.content[0].judgeList[0].colorFlag;
                        $scope.voteId = result.data.content[0].id
                    }
                });
        }

        getVote();

        // if($scope.$parent.swiper.show == false){
        //     getVote();
        // }

        $scope.$parent.$watch('swiper.show', function () {
            getVote();
        })

        $scope._vote = function (id, voteId) {

            var data = {
                "appraiseId": Number($scope.appraiseId),
                "contributeId": Number(id),
                // "id": 0,
                "judgeId": Number($scope.judgeId),
                "status": "SELECTED",
                "voteType": $scope.parentAppraiseId ? "BRANCH" : "TRUNK"
            };

            if ($scope.starColor == "#ddd") {
                topBarJudgeService.countJudgesPolNum($scope.appraiseId, $scope.judgeId)
                    .then(function (result) {
                        if (result.data.round < result.data.pollNum || result.data.pollNum == 0) {
                            return $q.resolve()
                        } else {
                            toaster.pop('error', '投票数已达上限')
                            return $q.reject()
                        }
                    })
                    .then(function () {
                        return Vote.vote(data)
                    })
                    .then(function (result) {
                        $scope.voteId = result.data.id
                        $scope.starColor = $scope.judgeColor;
                    })
            }
            else {
                Vote.unVote(voteId)
                    .then(function (result) {
                        $scope.starColor = "#ddd";
                    })
            }


        }

    }
})();
