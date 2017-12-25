(function () {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('User', User);

    User.$inject = ['$resource', 'UAA_URL'];

    function User ($resource, UAA_URL) {
        var service = $resource(UAA_URL + 'api/admin/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'},
            'getAdmins': {
                method: 'POST',
                isArray: true,
                url: UAA_URL + 'api/getUserByAuth',
                // params: { },
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });

        return service;
    }
})();
