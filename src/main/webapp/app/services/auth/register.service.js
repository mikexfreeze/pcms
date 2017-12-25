(function () {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('Register', Register);

    Register.$inject = ['$resource', 'API_URL'];

    function Register ($resource, API_URL) {
        return $resource(API_URL + 'uaa/api/register', {}, {});
    }
})();
