/**
 * Created by zhaimaojin on 17/5/19.
 */
(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('pop-live-list', {
                parent: 'entity',
                url: '/pop-live-list',
                data: {
                    authorities: ['ROLE_HOST','ROLE_GUEST'],
                    pageTitle: '评选列表'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/pop-live-list/pop-live-list.html',
                        controller: 'PopLiveListCtrl'
                    }
                }
            })
    }

})();
