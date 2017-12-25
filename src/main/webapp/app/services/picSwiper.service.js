/**
 * Created by Micheal Xiao on 2017/4/26.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('Swpier', Swpier);

    Swpier.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state'];

    function Swpier ($http, $localStorage, API_URL, toaster, $q, $state) {

        var service = {
            getPicPosition:getPicPosition,
            getPicPositJudg:getPicPositJudg,
            getPicPositAll:getPicPositAll,
            getPicByPosition:getPicByPosition,
            getPicByPositJudg:getPicByPositJudg,
            getPicByPositAll:getPicByPositAll,
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }

        function getPicPosition(data) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getWheelPictureLocation',
                data: data
            };
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取当前图片作品轮播列表中位置接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getPicPositJudg(data) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getAppraiseCollectWheelPictureLocation',
                data: data
            };
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取当前图片作品轮播列表中位置 judge-controll 页面 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getPicPositAll(data) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getWheelPictureAllLocation',
                data: data
            };
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取当前图片作品轮播列表中位置 all-conbute 页面 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getPicByPosition(data, page) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getPictureWheel',
                data: data
            };

            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取作品轮播列图片接口返回数据");
                    console.log(result.data);
                    return result.data;
                });


        }

        function getPicByPositJudg(data, page) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getAppraiseCollectPictureWheel',
                data: data
            };

            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取作品轮播列图片 judge-controll 页面 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });


        }

        function getPicByPositAll(data, page) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getPictureAllWheel',
                data: data
            };

            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取作品轮播列图片 judge-controll 页面 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });


        }
        return service;

    }
})();
