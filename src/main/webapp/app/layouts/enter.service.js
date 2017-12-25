/**
 * Created by Yue Gu on 2017/4/20.
 */
(function () {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('Enter', Enter);

    Enter.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state'];

    function Enter($http, $localStorage, API_URL, toaster, $q, $state) {

        var service = {
            getEnter: getEnter,
            enterAppraise: enterAppraise,
            unEnterAppraise: unEnterAppraise,
            deleteAppraise: deleteAppraise,
            abandonAppraise: abandonAppraise,
            branchAppraise: branchAppraise
        };

        function checkResult(result) {
            if (result.data.code > 1000) {
                toaster.pop('error', "错误", result.data.msg);
                return $q.reject(result)
            } else {
                return $q.resolve(result)
            }
        }

        function getEnter(appraiseId, contributeId,flowType) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "contributeId": contributeId,
                    "flowType":flowType
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/pop-appraise-articles-state',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        function enterAppraise(appraiseId, contributeId, flowType) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "contributeId": contributeId,
                    "status": 'SELECTED',
                    "flowType": flowType
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/pop-appraise-articles-select',
                data: data
            };
            console.log(data)

            return $http(req)
                .then(function (result) {
                    console.log('根据评选ID入选');
                    console.log(result.data);
                    return result.data;
                });
        }

        function unEnterAppraise(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/pop-appraise-articles-unselect',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据ID取消入选');
                    console.log(result.data);
                    return result.data;
                });
        }

        function deleteAppraise(appraiseId, contributeId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "contributeId": contributeId,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/pop-appraise-articles-del',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据评选ID删除评选');
                    console.log(result.data);
                    return result.data;
                });
        }
        function abandonAppraise(appraiseId, contributeId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "contributeId": contributeId,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/pop-appraise-articles-off',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据评选ID弃选');
                    console.log(result.data);
                    return result.data;
                });
        }

        function branchAppraise(appraiseId, contributeId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "contributeId": contributeId,
                    "status": 'SELECTED',
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/pop-appraise-articles-in',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据评选ID入选');
                    console.log(result.data);
                    return result.data;
                });
        }

        return service;

    }
})();
