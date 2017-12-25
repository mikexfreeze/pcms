(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('Password', Password);

    Password.$inject = ['$resource', 'UAA_URL'];

    function Password($resource, UAA_URL) {
        var service = $resource(UAA_URL + 'api/admin/change_password', {}, {});

        return service;
    }
})();
