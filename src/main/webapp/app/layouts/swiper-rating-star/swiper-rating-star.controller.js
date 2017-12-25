/**
 * Created by zhaimaojin on 2017/6/12.
 */

(function () {

    angular
        .module('pcmsApp')
        .controller('swiperRatingStarCtrl', swiperRatingStarCtrl);

    swiperRatingStarCtrl.$inject = ['$scope', 'Vote', '$stateParams', 'topBarJudgeService', '$rootScope', 'toaster', 'Swpier'];


    function swiperRatingStarCtrl($scope, Vote, $stateParams, topBarJudgeService, $rootScope, toaster, Swpier) {
        $scope.starColor = "#ddd";
        $scope.appraiseId = $stateParams.appraiseId;
        $scope.judgeId = $stateParams.judgeId;
        var flowType = "trunk";
        flowType = $scope.$parent.flowType;
        var condition = "";
        $scope.$on('cdgGetPicCdion', function (event, cond) {
            condition = cond;
        });

        //swiper init variable

        $scope.totalPages = 0;
        $scope.currentId = 0;


        function getPicPosition() {
            return Swpier.getPicPosition({
                subjectId: $scope.subjectId,
                appraiseId: $scope.appraiseId,
                voteStatus: condition,
                picId: $scope.picId,
                flowType: flowType
            }).then(function (result) {
                setSlide(result)
                // console.log('return res')
                // console.log(result)
                return result
                // // $scope.img.contributeId = result.data.content[0].pictureList[0].id;
            });
        }

        function getStarColor() {
            getPicPosition()
                .then(function (result) {

                    // $scope.contributeId = $scope.slide.id
                    console.log('contributeId')
                    console.log($scope.slide.id)

                    var queryData = {
                        "appraiseId": $scope.appraiseId,
                        "judgeId": $scope.judgeId,
                        "contributeId": $scope.slide.id,
                        "voteStatus": "NOT_SUBMITTED",
                        "flowType": $scope.flowType
                    };


                    Vote.getVote(queryData)
                        .then(function (result) {
                            if (result.data.content.length == 0) {
                                console.log("不涂色")
                                $scope.starColor = "#ddd";
                            } else {
                                console.log("涂色")
                                $scope.starColor = result.data.content[0].judgeList[0].colorFlag;
                                $scope.voteId = result.data.content[0].id
                            }
                        });
                })
        }

        console.log($scope.$parent)
        $scope.$parent.$watch("slide", function (newValue, oldValue, scope) {
            // getStarColor();
            // console.log(newValue.contributeId)
            $scope.picId = newValue.contributeId
            $scope.slide.id = newValue.id;
            getStarColor()
        });


        function setSlide(result) {
            $scope.slide = {
                image: result.data.content[0].pictureList[0].picPath,
                title: result.data.content[0].title,
                text: result.data.content[0].pictureList[0].remark,
                shootAddress: result.data.content[0].pictureList[0].shootAddress,
                shootDate: result.data.content[0].pictureList[0].shootDate,
                positionId: result.data.content[0].pictureList[0].picLocation,
                groupSize: result.data.content[0].groupSize,
                picLocationInGroup: result.data.content[0].picLocationInGroup,
                id: result.data.content[0].id,
                contributeId: result.data.content[0].pictureList[0].id,
            };
            // $scope.currentId = result.data.content[0].pictureList[0].picLocation
        }

        function fetchCountJudgesPolNum() {
            topBarJudgeService.countJudgesPolNum($scope.appraiseId, $scope.judgeId)
                .then(function (result) {
                    console.log("count接口")
                    console.log(result.data)
                    $scope.currentNum = result.data.round
                    $scope.pollNum = result.data.pollNum
                    console.log($scope.currentNum)
                })
        }

        fetchCountJudgesPolNum()

        $scope._vote = function (id, voteId) {
            fetchCountJudgesPolNum()
            var data = {
                "appraiseId": Number($scope.appraiseId),
                "contributeId": Number(id),
                // "id": 0,
                "judgeId": Number($scope.judgeId),
                "status": "SELECTED",
                "voteType": $scope.parentAppraiseId ? "BRANCH" : "TRUNK"
            };

            console.log("pol&&cur")
            console.log($scope.currentNum);
            console.log($scope.pollNum);

            // if ($scope.pollNum == 0) {
                if ($scope.starColor == "#ddd") {
                    $scope.starColor = $scope.judgeColor;
                    Vote.vote(data)
                        .then(function (result) {
                            console.log("vote------------")
                            console.log(result)
                            fetchCountJudgesPolNum();
                            console.log($scope.currentNum);
                            $scope.voteId = result.data.id

                        })
                }
                else {
                    $scope.starColor = "#ddd";
                    Vote.unVote(voteId)
                        .then(function (result) {
                            fetchCountJudgesPolNum();
                            console.log("unvote------------")
                            console.log($scope.currentNum);
                            $scope.voteId = result.data.id

                        })
                }
            // }
            // else {
            //     if ($scope.pollNum > $scope.currentNum) {
            //         if ($scope.starColor == "#ddd") {
            //             $scope.starColor = $scope.judgeColor;
            //             Vote.vote(data)
            //                 .then(function (result) {
            //                     console.log("vote------------")
            //                     fetchCountJudgesPolNum();
            //                     console.log($scope.currentNum);
            //                     $scope.voteId = result.data.id
            //                 })
            //         }
            //         else {
            //             $scope.starColor = "#ddd";
            //             Vote.unVote(voteId)
            //                 .then(function (result) {
            //                     fetchCountJudgesPolNum();
            //                     console.log("unvote------------")
            //                     console.log($scope.currentNum);
            //                     $scope.voteId = result.data.id
            //                 })
            //         }
            //     }

                // else if ($scope.pollNum == $scope.currentNum) {
                //     if ($scope.starColor == "#ddd") {
                //         toaster.pop("error", "票数用完")
                //     } else {
                //         $scope.starColor = "#ddd";
                //         Vote.unVote(voteId)
                //             .then(function (result) {
                //                 fetchCountJudgesPolNum();
                //                 $scope.voteId = result.data.id
                //
                //             })
                //         console.log("unvote------------")
                //         console.log($scope.currentNum);
                //
                //
                //     }
                // }
            // }
        }


    }
})();
