(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('UploadService', UploadService);

    UploadService.$inject = ['$q', 'toaster', 'Upload', 'API_URL'];

    function UploadService ($q, toaster, Upload, API_URL) {
        var service = {
            upLoadImg:upLoadImg,
            uploadCompetition:uploadCompetition
        };

        function upLoadImg(file) {
            var fileName = file.name;
            if (!fileName.match(/.jpg|.png/i)) {
                toaster.pop("error","请上传.jpg或.png格式图片");
                return $q.reject();
            } else {

            }

            var promise = Upload.upload({
                url: API_URL + 'api/upload/1',
                method: 'POST',
                headers: {
                    'optional-header': 'header-value'
                },
                data: {file:file},
            }).then(function (response) {
                console.log("上传图片接口返回数据");
                console.log(response.data);
                return response.data;
            }, function (response) {
                // toaster.pop("error","服务器错误");
                console.log("error:" + result)
            });

            return promise;
        }

        function uploadCompetition(file) {

            var promise = Upload.upload({
                url: API_URL + 'api/uploadCompetition',
                method: 'POST',
                headers: {
                    'optional-header': 'header-value'
                },
                data: {file:file},
            }).then(function (response) {
                console.log("上传活动文件接口返回数据");
                console.log(response.data);
                toaster.pop("info","上传成功")
                return response.data;
            }, function (response) {
                // toaster.pop("error","服务器错误");
                console.log("error:" + result)
            });

            return promise;
        }

        return service;
    }
})();
