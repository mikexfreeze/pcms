(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('GatewayRoutes', GatewayRoutes);

    GatewayRoutes.$inject = ['$resource', 'API_URL'];

    function GatewayRoutes ($resource, API_URL) {
        var service = $resource(API_URL + 'api/gateway/routes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });

        return service;
    }

})();
