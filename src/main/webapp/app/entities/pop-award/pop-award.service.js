(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopAward', PopAward);

    PopAward.$inject = ['$resource', 'API_URL'];

    function PopAward ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-awards/:id';

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
