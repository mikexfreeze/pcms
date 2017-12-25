(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopVote', PopVote);

    PopVote.$inject = ['$resource', 'API_URL'];

    function PopVote ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-votes/:id';

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
