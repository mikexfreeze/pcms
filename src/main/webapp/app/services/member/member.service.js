(function () {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('Member', Member);

    Member.$inject = ['$resource', 'API_URL'];

    function Member ($resource, API_URL) {
        var service = $resource(API_URL + 'api/pop-user-competitions/:id', {}, {
            'competitionUsers': {
                method: 'POST',
                // isArray: true,
                url: API_URL + 'api/qy-pop-user-bycompetionid',
                // params: { },
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });

        return service;
    }
})();
