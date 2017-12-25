(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopCompetition', PopCompetition);

    PopCompetition.$inject = ['$http', '$localStorage', 'API_URL', '$resource', 'DateUtils', 'toaster', '$q', '$state'];

    function PopCompetition ($http, $localStorage, API_URL, $resource, DateUtils, toaster, $q, $state) {

        var service = {
            getCompetition:getCompetition,
            getCompetitionById:getCompetitionById,
            createCompetition:createCompetition,
            updateCompetition:updateCompetition,
            deleteCompetition:deleteCompetition,
            updateCompetitionStatus:updateCompetitionStatus,
            getConPeopleNum:getConPeopleNum,
            exportCompetition:exportCompetition,
        };


        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }



        function getCompetition(queryData) {

            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "title": ""
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/list-pop-competitions',
                data: data,
            };

            if(queryData.page == 0 || queryData.page){
                req.params = {"page": queryData.page, "size": 10}
            }
            if(queryData.keyword){
                data.data.title = queryData.keyword
            }
            if(queryData.status){
                data.data.status = queryData.status
            }


            return $http(req)
                .then(function (result) {
                    console.log("获取活动列表接口返回数据");
                    console.log(result.data);
                    return result.data;
                });

        }

        function getCompetitionById(id) {

            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-competition',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取活动by Id接口返回数据");
                    console.log(result.data);
                    return result.data;
                });

        }

        function createCompetition(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/add-pop-competitions',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                },
            };

            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("创建活动列表接口返回数据");
                    console.log(result.data);
                    $state.go('com-tab-page-editor', {competitionId:result.data.data.id}, {reload: true});
                    toaster.pop('info',"创建成功");
                });

        }

        function updateCompetition(data) {

            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-competitions',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                },
            };

            return $http(req)
                .then(function (result) {
                    console.log("编辑活动列表接口返回数据");
                    console.log(result.data);
                    if(result.data.code > 1000){
                        toaster.pop('error',"错误", result.data.msg);
                        return $q.reject()
                    }else{
                        toaster.pop('info',"编辑成功");
                    }

                });

            console.log(result.data);
        }

        function deleteCompetition(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/del-pop-competitions',
                data: {
                    "data": {
                        "id": id
                    }
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("删除接口");
                    if(result.data.code > 1000){
                        toaster.pop('error',"错误", result.data.msg);
                        return $q.reject()
                    }else{
                        toaster.pop('info',"删除成功");
                    }
                })
        }

        function updateCompetitionStatus(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-competitions-status',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                },
            };

            return $http(req)
                .then(function (result) {
                    console.log("延期截稿接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getConPeopleNum(competitionId) {

            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    competitionId: competitionId
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/count-auth-contribute',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取投稿人数、作品数接口返回数据");
                    console.log(result.data);
                    return result.data;
                });

        }

        function exportCompetition(competitionId) {

            var req = {
                method: 'GET',
                url: API_URL + 'api/expCompete/' + competitionId,
            };

            return $http(req)
                .then(function (result) {
                    console.log("导出活动所有作品");
                    console.log(result.data);
                    if(result.data.code > 1000){
                        toaster.pop('error',"错误", result.data.msg);
                        return $q.reject()
                    }else{
                        toaster.pop('info',"导出成功");
                        return result.data;
                    }

                });

            console.log(result.data);
        }

        return service;

    }
})();
