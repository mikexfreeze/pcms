/**
 * Created by Micheal Xiao on 2017/4/20.
 */
(function () {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('Vote', Vote);

    Vote.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state', '$rootScope'];

    function Vote($http, $localStorage, API_URL, toaster, $q, $state, $rootScope) {

        var service = {
            vote: vote,
            getVote: getVote,
            unVote:unVote,
            checkCountResult:checkCountResult
        };

        function checkResult(result) {
            if (result.data.code > 1000) {
                toaster.pop('error', "错误", result.data.msg);
                return $q.reject(result)
            } else {
                return $q.resolve(result)
            }
        }

        function checkCountResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", "超过最大投票数");
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }

        function vote(queryData) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": 0,
                    "contributeId": 0,
                    // "id": 0,
                    "judgeId": 18,
                    "status": "SELECTED",
                    "voteType": "TRUNK"
                }
            };

            data.data = angular.extend(data.data, queryData);
            var req = {
                method: 'POST',
                url: API_URL + 'api/add-vote',
                data: data
            };
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("投票接口返回数据");
                    console.log(result.data);
                    $rootScope.$broadcast('vote');
                    return result.data;
                });

        }

        function unVote(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": id
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/del-pop-votes',
                data: data
            };

            return $http(req)
                .then(function (result) {
                    return checkCountResult(result)
                })
                .then(function (result) {
                    console.log("删除投票接口返回数据");
                    console.log(result.data);
                    $rootScope.$broadcast('vote');
                    return result.data;
                });
        }

        function getVote(queryData) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": queryData
            };
            // data.data = angular.extend(data.data, queryData);

            var req = {
                method: 'POST',
                url: API_URL + 'api/list-pop-contribute_vote',
                data: data
            };
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    // console.log("获取投票情况接口返回数据");
                    // console.log(result.data);
                    return result.data;
                });
        }

        return service;

    }
})();
