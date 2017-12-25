(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopContribute', PopContribute);

    PopContribute.$inject = ['$resource', 'API_URL'];

    function PopContribute ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-contributes/:id';

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
