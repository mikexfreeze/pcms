(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-judge', {
            parent: 'entity',
            url: '/pop-judge?page&sort&search',
            data: {
                authorities: ['ROLE_JUDGE', 'ROLE_OBSERVER'],
                pageTitle: '评选列表'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-judge/pop-judges.html',
                    controller: 'PopJudgeController'
                }
            }
        })
    }

})();
