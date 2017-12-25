/**
 * Created by Micheal Xiao on 2017/5/5.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopPublish', PopPublish);

    PopPublish.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state' ,'POP_URL'];

    function PopPublish ($http, $localStorage, API_URL, toaster, $q, $state, POP_URL) {

        var service = {
            getPublish:getPublish,
            getUserName:getUserName,
            updateUserName:updateUserName,
            updateContubeName:updateContubeName,
            updatePrizeName:updatePrizeName,
            addNewWorks:addNewWorks,
            deleteWorksByContributeId:deleteWorksByContributeId,
            getContributeList:getContributeList,
            searchByRealname:searchByRealname,
            addConToAward:addConToAward,
            exportWorks:exportWorks
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }

        //根据主题查询
        function getPublish(data) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry_pop-awards-byuserid',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    // console.log("获取publish by competition Id接口返回数据");
                    // console.log(result.data);
                    return result.data;
                });

        }

        function getUserName(userId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    userId:userId
                }
            };

            var req = {
                method: 'GET',
                url: POP_URL + 'api/qry-members-by-userId/' + userId,

            };

            return $http(req)
                .then(function (result) {
                    // console.log("根据userId 获取用户名称 接口返回数据");
                    // console.log(result.data);
                    return result.data;
                });
        }

        function updateUserName(data) {

            var req = {
                method: 'POST',
                url: POP_URL + 'api/update-relanmae-by-userId',
                data:data
            };

            return $http(req)
                .then(function (result) {
                    console.log("修改用户名称 接口返回数据");
                    console.log(result.data);
                    toaster.pop("info","用户姓名编辑成功");
                    // $state.go($state.current, {}, {reload: true});
                    return result.data;
                });
        }

        function updateContubeName(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-contributes-title',
                data:{
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("修改投稿名称 接口返回数据");
                    console.log(result.data);
                    toaster.pop("info","修改成功");
                    $state.go($state.current, {}, {reload: true});
                    return result.data;
                });
        }

        function updatePrizeName(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-award-name',
                data:{
                    "name": "string",
                    "channelId": "string",
                    "data": data
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("修改奖项名称 接口返回数据");
                    console.log(result.data);
                    $state.go($state.current, {}, {reload: true});
                    return result.data;
                });
        }

        function addNewWorks() {
            var req = {
                method: 'POST',
                url: API_URL + 'api/save_pop-awards',
                data:{
                    "name": "string",
                    "channelId": "string",
                    "data": data
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("修改奖项名称 接口返回数据");
                    console.log(result.data);
                    toaster.pop("info","修改成功");
                    $state.go($state.current, {}, {reload: true});
                    return result.data;
                });
        }

        function deleteWorksByContributeId(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/delete-pop-appraise-articles',
                data:{
                    "name": "string",
                    "channelId": "string",
                    "data": data
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("删除获奖 接口返回数据");
                    console.log(result.data);
                    $state.go($state.current, {}, {reload: true});
                    return result.data;
                });
        }

        function getContributeList(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/getMyContributeList',
                data:{
                    "name": "string",
                    "channelId": "string",
                    "data": data
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取投稿作品 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function searchByRealname(realname) {
            var req = {
                method: 'POST',
                url: POP_URL + 'api/qry-member-by-relaname',
                data:{
                    "realname":realname
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("search by realname 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function addConToAward(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/save_pop-awards',
                data:{
                    "name": "string",
                    "channelId": "string",
                    "data": data
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("添加作品到获奖名单 接口返回数据");
                    console.log(result.data);
                    toaster.pop("info","添加成功");
                    $state.go($state.current, {}, {reload: true});
                    return result.data;
                });
        }

        function exportWorks(competitionId) {
            var req = {
                method: 'GET',
                url: API_URL + 'api/expzip/' + competitionId,
            };

            return $http(req)
                .then(function (result) {
                    console.log("作品导出 接口返回数据");
                    console.log(result.data);
                    // $state.go($state.current, {}, {reload: true});
                    return result.data;
                });
        }

        return service;


    }
})();
