(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopAppraise', PopAppraise);

    PopAppraise.$inject = ['$resource', 'API_URL'];

    function PopAppraise ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-appraises/:id';

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
            'update': { method:'PUT' },
            'getSubjectAppraises': {
                method: 'POST',
                isArray: false,
                url: API_URL + 'api/qry-pop-appraises-subject',
                // params: { },
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    }
})();
