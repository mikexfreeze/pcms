(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopTheme', PopTheme);

    PopTheme.$inject = ['$http', '$localStorage', 'API_URL', '$resource', 'DateUtils', 'toaster', '$q', '$state'];

    function PopTheme ($http, $localStorage, API_URL, $resource, DateUtils, toaster, $q, $state) {

        var service = {
            createTheme:createTheme,
            updateTheme:updateTheme,
            getTheme:getTheme,
            getThemeById:getThemeById,
            deleteTheme:deleteTheme,
            deleteThemeAppraises: deleteThemeAppraises,
            resetSubject: resetSubject
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }

        //创建主题
        function createTheme(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/add-pop-subjects',
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
                    console.log("创建主题接口返回数据");
                    console.log(result.data);
                    toaster.pop('info',"创建成功");
                    $state.go($state.current, {}, {reload: true});
                });

        }

        //更新主题
        function updateTheme(data) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-subjects',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": data
                },
            };

            return $http(req)
                .then(function (result) {
                    console.log("编辑主题接口返回数据");
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

        //根据主题查询
        function getTheme(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "competitionId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-subjects-bycompetition_id',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取theme by competition Id接口返回数据");
                    console.log(result.data);
                    return result.data;
                });

        }

        function getThemeById(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-subject',
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
                    console.log("获取主题by theme id接口返回数据");
                    console.log(result.data);

                    return result.data
                });
        }

        //删除主题
        function deleteTheme(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/del-pop-subjects/',
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
                    console.log("删除主题by id接口返回数据");
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

        //删除主题下所有轮次信息
        function deleteThemeAppraises(id) {
            var req = {
                method: 'GET',
                url: API_URL + 'api/update-pcms/' + id,
            };

            return $http(req)
                .then(function (result) {
                    console.log("删除主题下所有轮次信息by id接口返回数据");
                    console.log(result.data);
                    if(result.data.code > 1000){
                        toaster.pop('error',"错误", result.data.msg);
                        return $q.reject()
                    }else{
                        toaster.pop('info',"重置成功");
                        $state.go($state.current, {}, {reload: true});
                    }

                });
        }

        //删除主题下所有轮次信息
        function resetSubject(id) {
            var req = {
                method: 'POST',
                url: API_URL + 'api/update-status-to-null',
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
                    console.log("重启评选by id接口返回数据");
                    console.log(result.data);
                    if(result.data.code > 1000){
                        toaster.pop('error',"错误", result.data.msg);
                        return $q.reject()
                    }else{
                        toaster.pop('info',"重启成功");
                        $state.go($state.current, {}, {reload: true});
                    }

                });
        }

        return service;


    }
})();
