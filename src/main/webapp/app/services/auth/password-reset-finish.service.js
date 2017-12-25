(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource', 'API_URL'];

    function PasswordResetFinish($resource, API_URL) {
        var service = $resource(API_URL + 'uaa/api/account/reset_password/finish', {}, {});

        return service;
    }
})();
