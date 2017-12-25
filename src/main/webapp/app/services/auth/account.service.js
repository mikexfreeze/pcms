(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('Account', Account);

    Account.$inject = ['$resource', 'API_URL', 'UAA_URL'];

    function Account ($resource, API_URL, UAA_URL) {
        var service = $resource(UAA_URL + 'api/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });

        return service;
    }
})();
