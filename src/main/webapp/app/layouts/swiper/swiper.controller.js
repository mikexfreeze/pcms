/**
 * Created by Micheal Xiao on 2017/4/26.
 */
(function() {

    angular
        .module('pcmsApp')
        .controller('swiperCtrl', swiperCtrl);

    swiperCtrl.$inject = ['Swpier', '$scope', '$rootScope', '$state', 'Auth', '$localStorage', '$q', '$stateParams','$document'];

    function swiperCtrl (Swpier, $scope, $rootScope, $state, Auth, $localStorage, $q, $stateParams,$document) {

        $scope.appraiseId = $stateParams.appraiseId;
        $scope.subjectId = $stateParams.subjectId;
        $scope.judgeId = $stateParams.judgeId;

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
            return Swpier.getPicPosition({
                subjectId:$scope.subjectId,
                appraiseId:$scope.appraiseId,
                voteStatus: condition,
                picId:$scope.picId,
                flowType:flowType
            }).then(function (result) {
                setSlide(result);
                console.log(result)
                console.log(result)
                $scope.totalPages = result.data.content[0].pictureList[0].totalPage;
                $scope.currentId = result.data.content[0].pictureList[0].picLocation;
                // $scope.contributetype = result.data.content[0].contributeType
                // $scope.img.contributeId = result.data.content[0].pictureList[0].id;
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

        function getPicByPosition(positionId) {
            return Swpier.getPicByPosition({
                subjectId:$scope.subjectId,
                appraiseId:$scope.appraiseId,
                voteStatus: condition,
                picLocation:positionId,
                flowType:flowType
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
                id:result.data.content[0].id,
                contributeId:result.data.content[0].pictureList[0].id,
                contributeType : result.data.content[0].contributeType,
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
                if(event.keyCode == 37){
                    $scope.prev();
                }
                if(event.keyCode == 39){
                    $scope.next();
                }
        });



    }
})();
