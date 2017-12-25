/**
 * Created by zhaimaojin on 17/5/22.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopLiveList', PopLiveList);

    PopLiveList.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state'];

    function PopLiveList ($http, $localStorage, API_URL, toaster, $q, $state) {
        var service = {
            getPopLiveList:getPopLiveList
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result);
            }else{
                return $q.resolve(result);
            }
        }

        function getPopLiveList(role, id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "liveId":0,
                    "role": role+',',
                    "userId": id
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-live-byuserId',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取直播列表接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }



        return service;
    }
})();
