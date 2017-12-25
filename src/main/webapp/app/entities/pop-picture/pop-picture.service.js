(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopPicture', PopPicture);

    PopPicture.$inject = ['$resource', 'DateUtils'];

    function PopPicture ($resource, DateUtils) {
        var resourceUrl =  API_URL + 'api/pop-pictures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.shootDate = DateUtils.convertLocalDateFromServer(data.shootDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.shootDate = DateUtils.convertLocalDateToServer(copy.shootDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.shootDate = DateUtils.convertLocalDateToServer(copy.shootDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
