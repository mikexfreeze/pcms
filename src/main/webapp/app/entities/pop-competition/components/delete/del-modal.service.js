/**
 * Created by zhaimaojin on 17/3/22.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('DelModal', DelModal);

    DelModal.$inject = ['$http', '$localStorage', 'API_URL', '$resource', 'DateUtils', 'toaster', '$q', '$state'];

    function DelModal ($http, $localStorage, API_URL, $resource, DateUtils, toaster, $q, $state) {
        var service = {
            deleteCompetition:deleteCompetition
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
                        toaster.pop('info',"编辑成功");
                    }
                })
        }


        return service;
    }
})();
