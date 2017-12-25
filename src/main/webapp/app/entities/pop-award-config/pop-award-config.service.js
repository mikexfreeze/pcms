(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopAwardConfig', PopAwardConfig);

    PopAwardConfig.$inject = ['$resource', 'API_URL'];

    function PopAwardConfig ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-award-configs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
