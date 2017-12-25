(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('ProfileService', ProfileService);

    ProfileService.$inject = ['$http', 'API_URL'];

    function ProfileService($http, API_URL) {

        var dataPromise;

        var service = {
            getProfileInfo : getProfileInfo
        };

        return service;

        function getProfileInfo() {
            if (angular.isUndefined(dataPromise)) {
                dataPromise = $http.get(API_URL + 'api/profile-info').then(function(result) {
                    if (result.data.activeProfiles) {
                        var response = {};
                        response.activeProfiles = result.data.activeProfiles;
                        response.ribbonEnv = result.data.ribbonEnv;
                        response.inProduction = result.data.activeProfiles.indexOf("prod") !== -1;
                        response.swaggerEnabled = result.data.activeProfiles.indexOf("swagger") !== -1;
                        return response;
                    }
                });
            }
            return dataPromise;
        }
    }
})();
