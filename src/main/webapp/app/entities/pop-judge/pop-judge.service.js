(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopJudge', PopJudge);

    PopJudge.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state'];

    function PopJudge ($http, $localStorage, API_URL, toaster, $q, $state) {
        var service = {
            getJudgeDetails:getJudgeDetails,
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result);
            }else{
                return $q.resolve(result);
            }
        }

        function getJudgeDetails(id, status) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "userId": id,
                    "appraiseStatus": status
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/find-pop-judges',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取评选列表接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        return service;
    }
})();
