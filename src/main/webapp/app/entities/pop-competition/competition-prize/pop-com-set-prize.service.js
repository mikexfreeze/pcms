/**
 * Created by Micheal Xiao on 2017/3/22.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopPrize', PopPrize);

    PopPrize.$inject = ['$http', '$localStorage', 'API_URL', '$resource', 'DateUtils', 'toaster', '$q', '$state'];

    function PopPrize ($http, $localStorage, API_URL, $resource, DateUtils, toaster, $q, $state) {

        var service = {
            createPrize:createPrize,
            updatePrize:updatePrize,
            getPrize:getPrize,
            getPrizeById:getPrizeById,
            deletePrize:deletePrize,
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }

        //创建奖项
        function createPrize(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/add-pop-award-configs',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                },
            };

            return $http(req)
                .then(function (result) {
                    return checkResult(result);
                })
                .then(function (result) {
                    console.log("创建奖项接口返回数据");
                    console.log(result.data);
                    toaster.pop('info',"创建成功");
                    $state.go($state.current, {}, {reload: true});
                });

        }

        //更新奖项
        function updatePrize(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-award-configs',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                },
            };

            return $http(req)
                .then(function (result) {
                    console.log("编辑奖项接口返回数据");
                    console.log(result.data);
                    if(result.data.code > 1000){
                        toaster.pop('error',"错误", result.data.msg);
                        return $q.reject()
                    }else{
                        toaster.pop('info',"编辑成功");
                        $state.go($state.current, {}, {reload: true});
                    }

                });
        }

        //根据competition Id查询
        function getPrize(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "competitionId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/list-pop-award-configs/',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取奖项 by competition Id接口返回数据");
                    console.log(result.data);
                    return result.data;
                });

        }

        function getPrizeById(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-award-configs',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": {
                        "id": id,
                    }
                },
            };

            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取奖项by award id接口返回数据");
                    console.log(result.data);

                    return result.data
                });
        }

        //删除奖项
        function deletePrize(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/del-pop-award-configs',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": {
                        "id": id,
                    }
                },
            };

            return $http(req)
                .then(function (result) {
                    console.log("删除奖项by id接口返回数据");
                    console.log(result.data);
                    if(result.data.code > 1000){
                        toaster.pop('error',"错误", result.data.msg);
                        return $q.reject()
                    }else{
                        toaster.pop('info',"删除成功");
                        $state.go($state.current, {}, {reload: true});
                    }

                });
        }

        return service;


    }
})();
