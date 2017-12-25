(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopAppraiseArticle', PopAppraiseArticle);

    PopAppraiseArticle.$inject = ['$resource', 'API_URL'];

    function PopAppraiseArticle ($resource, API_URL) {
        var resourceUrl =  API_URL + 'api/pop-appraise-articles/:id';

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
