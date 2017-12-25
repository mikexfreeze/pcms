/**
 * Created by zhaimaojin on 17/3/23.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('CommonService', CommonService);

    CommonService.$inject = ['$http', '$localStorage', 'API_URL', '$resource', 'DateUtils', 'toaster', '$q','$uibModal'];

    function CommonService ($http, $localStorage, API_URL, $resource, DateUtils, toaster, $q, $uibModal) {

        var service = {
            confirmModal:confirmModal,
        };

        function confirmModal() {
            var modalInstance = $uibModal.open({
                templateUrl: 'app/entities/pop-competition/components/delete/del-modal.html',
                controller: 'DelModalController',
                size: 'sm',
            });
            return modalInstance.result.then(function (flag) {
                if (flag.$value) {
                    return $q.resolve()
                } else {
                    return $q.reject()
                }
            });
        }

        return service;

    }
})();
