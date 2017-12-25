/**
 * Created by Micheal Xiao on 2017/4/25.
 */
(function() {

    angular
        .module('pcmsApp')
        .controller('multiStarsCtrl', multiStarsCtrl);

    multiStarsCtrl.$inject = ['$scope', 'Vote'];

    function multiStarsCtrl ($scope, Vote) {
        $scope.stars = [];

        // console.log("img")
        // console.log($scope.img)

        var queryData = {
            "appraiseId":$scope.appraiseId,
            "judgeId":$scope.judgeId,
            "contributeId":$scope.img.contributeId,
            "voteStatus": "SUBMITTED",
            "flowType":$scope.flowType
        };

        Vote.getVote(queryData)
            .then(function (result) {
                if(result.data.content.length == 0){
                    $scope.stars == []
                }else{
                    // console.log("投票接口：");
                    // console.log(result.data);
                    result.data.content.forEach(function (val) {
                        var star = {
                            color:val.judgeList[0].colorFlag,
                            voteId:val.judgeList[0].id
                        };
                        $scope.stars.push(star)
                    });
                    // console.log($scope.stars)
                    // $scope.starColor = result.data.content[0].judgeList[0].colorFlag;
                    // $scope.voteId = result.data.content[0].judgeList[0].id
                }
            });

        $scope._vote = function (id, voteId) {
            var data = {
                "appraiseId": Number($scope.appraiseId),
                "contributeId": Number(id),
                // "id": 0,
                "judgeId": Number($scope.judgeId),
                "status": "SELECTED",
                "voteType": $scope.parentAppraiseId ? "BRANCH" : "TRUNK"
            };

            if($scope.starColor == "#ddd"){
                $scope.starColor = $scope.judgeColor;
                Vote.vote(data)
            }else{
                $scope.starColor = "#ddd";
                Vote.unVote(voteId)
            }
        }

    }
})();
