(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopObserver', PopObserver);

    PopObserver.$inject = ['$resource', 'API_URL'];

    function PopObserver ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-observers/:id';

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
