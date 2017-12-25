(function () {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('Message', Message);

    Message.$inject = ['$resource', 'POP_URL'];

    function Message ($resource, POP_URL) {
        var service = $resource(POP_URL + 'api/member-messages/:id', {}, {
            'save': { method:'POST' }
        });

        return service;
    }
})();
