(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('AuthServerProvider', AuthServerProvider);

    AuthServerProvider.$inject = ['$http', '$localStorage', '$sessionStorage', '$q', 'API_URL', 'UAA_URL'];

    function AuthServerProvider ($http, $localStorage, $sessionStorage, $q, API_URL, UAA_URL) {
        var service = {
            getToken: getToken,
            login: login,
            loginWithToken: loginWithToken,
            storeAuthenticationToken: storeAuthenticationToken,
            logout: logout
        };

        return service;

        function getToken () {
            return $localStorage.authenticationToken || $sessionStorage.authenticationToken;
        }

        function login (credentials) {
            var data = {
                username: credentials.username,
                password: credentials.password,
                grant_type: "password"
            };
            var headers = {
                'Content-Type': 'application/x-www-form-urlencoded',
                "Authorization" : "Basic d2ViX2FwcDo="
            };

            return $http({
                url: UAA_URL + 'oauth/token',
                method: 'post',
                data: data,
                headers: headers,
                transformRequest: function(obj) {
                    var str = [];
                    for (var p in obj) {
                        str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                    }
                    return str.join('&');
                }
            }).then(function (data) {
                console.log("token接口返回信息");
                console.log(data.data);
                var accessToken = data.data["access_token"];
                // $http.defaults.headers.common.Authorization = accessToken;
                if (angular.isDefined(accessToken)) {
                    service.storeAuthenticationToken(accessToken, credentials.rememberMe);
                }
            });
        }

        function loginWithToken(jwt, rememberMe) {
            var deferred = $q.defer();

            if (angular.isDefined(jwt)) {
                this.storeAuthenticationToken(jwt, rememberMe);
                deferred.resolve(jwt);
            } else {
                deferred.reject();
            }

            return deferred.promise;
        }

        function storeAuthenticationToken(jwt, rememberMe) {
            if(rememberMe){
                $localStorage.authenticationToken = jwt;
            } else {
                $sessionStorage.authenticationToken = jwt;
            }
        }

        function logout () {
            delete $localStorage.authenticationToken;
            delete $sessionStorage.authenticationToken;
        }
    }
})();
