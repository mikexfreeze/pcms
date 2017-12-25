(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopSubject', PopSubject);

    PopSubject.$inject = ['$resource', 'API_URL'];

    function PopSubject ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-subjects/:id';

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
