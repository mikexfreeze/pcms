/**
 * Created by Micheal Xiao on 2017/5/25.
 */
(function() {

    angular
        .module('pcmsApp')
        .controller('allConSwiper', allConSwiper);

    allConSwiper.$inject = ['Swpier', '$scope', '$rootScope', '$state', 'Auth', '$localStorage', '$q', '$stateParams','$document'];

    function allConSwiper (Swpier, $scope, $rootScope, $state, Auth, $localStorage, $q, $stateParams,$document) {

        $scope.appraiseId = $stateParams.appraiseId;
        $scope.subjectId = $stateParams.subjectId;
        $scope.judgeId = $stateParams.judgeId;
        $scope.competitionId = $stateParams.competitionId;

        var flowType = "trunk";
        flowType = $scope.flowType;
        var condition = "";
        $scope.$on('cdgGetPicCdion', function (event, cond) {
            condition = cond;
        });

        //swiper init variable

        $scope.totalPages = 0;
        $scope.currentId = 0;

        getPicPosition();
        function getPicPosition() {
            return Swpier.getPicPositAll({
                competitionId:$scope.competitionId,
                // appraiseId:$scope.appraiseId,
                // voteStatus: condition,
                picId:$scope.picId,
                // flowType:flowType
            }).then(function (result) {
                setSlide(result);
                $scope.totalPages = result.data.content[0].pictureList[0].totalPage;
                $scope.currentId = result.data.content[0].pictureList[0].picLocation;
            });

        }

        $scope.next = function () {
            if($scope.currentId < $scope.totalPages){
                return getPicByPosition($scope.currentId += 1)
                    .then(function (result) {
                        setSlide(result)
                    })
            }
        };

        $scope.prev = function () {
            if($scope.currentId > 1){
                return getPicByPosition($scope.currentId -= 1)
                    .then(function (result) {
                        setSlide(result)
                    })
            }
        };

        // function keyDown(e)
        // {
        //     var e = event || window.event || arguments.callee.caller.arguments[0];
        //     if(e && e.keyCode==37){ // 按 Esc
        //
        //         $scope.prev();
        //
        //     }
        //     if(e && e.keyCode==39){ // 按 F2
        //
        //         $scope.prnext();
        //
        //     }
        //
        // }

        function getPicByPosition(positionId) {
            return Swpier.getPicByPositAll({
                competitionId:$scope.competitionId,
                // subjectId:$scope.subjectId,
                // appraiseId:$scope.appraiseId,
                // voteStatus: condition,
                picLocation:positionId,
                // flowType:flowType
            })
        }

        function setSlide(result) {
            $scope.slide = {
                image: result.data.content[0].pictureList[0].picPath,
                title: result.data.content[0].title,
                text: result.data.content[0].pictureList[0].remark,
                shootAddress:result.data.content[0].pictureList[0].shootAddress,
                shootDate:result.data.content[0].pictureList[0].shootDate,
                positionId: result.data.content[0].pictureList[0].picLocation,
                groupSize: result.data.content[0].groupSize,
                picLocationInGroup: result.data.content[0].picLocationInGroup,
                contributeType: result.data.content[0].contributeType
            };
            // $scope.currentId = result.data.content[0].pictureList[0].picLocation
        }

        function preGetNextPosition() {
            if($scope.currentId + diff < $scope.totalPages){
                return getPicByPosition($scope.currentId + 1 + diff)
                    .then(function (result) {
                        addSlide(result)
                    })
            }
        }

        function preGetPrevPosition() {
            if($scope.currentIdNeg + diff > 1){
                return getPicByPosition($scope.currentIdNeg - 1 + diff)
                    .then(function (result) {
                        addPreSlide(result)
                    })
            }
        }

        $document.bind("keydown", function(event) {
            $scope.$apply(function (){
                if(event.keyCode == 37){
                    $scope.prev();
                }
                if(event.keyCode == 39){
                    $scope.next();
                }
            })
        });



    }
})();
