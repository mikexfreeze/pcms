/**
 * Created by zhaimaojin on 17/5/11.
 */

(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopLive', PopPublish);

    PopPublish.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state' ,'POP_URL'];

    function PopPublish ($http, $localStorage, API_URL, toaster, $q, $state, POP_URL) {

        var service = {
            addLive:addLive,
            getHostByLiveId:getHostByLiveId,
            addGuest:addGuest,
            queryLive:queryLive,
            updateLive:updateLive,
            quryGuest:quryGuest,
            deleteGuest:deleteGuest,
            deleteHost:deleteHost,
            addHost:addHost,
            updateHost:updateHost,
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }
        //添加直播
        function addLive(data){
            var req = {
                method: 'POST',
                url: API_URL + 'api/add-pop-competition-live',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                }
            }
            return $http(req)
                .then(function (result) {
                    console.log("开启LIVE接口返回数据");
                    console.log(result)
                    return result.data
                });
        }
        //更新直播
        function updateLive(data){
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-competitions-live',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                }
            }
            return $http(req)
                .then(function (result) {
                    console.log("更新LIVE接口返回数据");
                    console.log(result)
                    return result.data
                });
        }

        //添加嘉宾

        function addGuest(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/add-live-guest',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                }
            };
            return $http(req)
                .then(function (result) {
                    console.log("添加Guest接口返回数据");
                    console.log(result)
                    return result.data
                })
        }

        //添加主持人
        function addHost(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/add-live-host',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                }
            };
            return $http(req)
                .then(function (result) {
                    console.log("添加主持人接口返回数据");
                    console.log(result)
                    return result.data
                })
        }

        //更新主持人
        function updateHost(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-live-host',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                }
            };
            return $http(req)
                .then(function (result) {
                    console.log("更新主持人接口返回数据");
                    console.log(result)
                    return result.data
                })
        }
        
        //查询嘉宾
        function quryGuest(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/live-guest',
                data:{
                    "appId": "string",
                    "channelId": "string",
                    "data": {
                        "liveId":id,
                    }
                }
            }
            return $http(req)
                .then(function (result) {
                    console.log("查询嘉宾接口返回数据");
                    console.log(result)
                    return result.data;
                })
        }

        
        //根据competitionID查询直播
        function queryLive(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-competitions-live',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": {
                        "competitionId":id
                    }
                }
            }
            return $http(req)
                .then(function (result) {
                    console.log("查询Live接口返回数据");
                    console.log(result)
                    return result.data;
                })
        }



        //通过直播id获取主持人信息
        function getHostByLiveId(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/live-host',
                data:{
                    "appId": "string",
                    "channelId": "string",
                    "data":{
                        "liveId":id
                    }
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("根据liveid 获取host信息 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        //删除嘉宾
        function deleteGuest(id) {
            var req = {
                method:'DELETE',
                url:API_URL + 'api/live-guests/' + id
            }
            return $http(req)
                .then(function (result) {
                    console.log("删除嘉宾接口返回数据");
                    console.log(result)
                    return result.data
                });
        }

        //删除主持人
        function deleteHost(id) {
            var req = {
                method:'DELETE',
                url:API_URL + 'api/live-hosts/' + id
            }
            return $http(req)
                .then(function (result) {
                    console.log("删除主持人接口返回数据");
                    console.log(result)
                    return result.data
                });
        }

        return service;


    }
})();
