(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('pop-competition', {
                parent: 'entity',
                url: '/pop-competition?page&sort&search',
                cache:false,

                data: {
                    authorities: ["ROLE_ADMIN"],
                    pageTitle: '活动管理'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/pop-competition/pop-competitions.html',
                        controller: 'PopCompetitionController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }]
                }
            })
            .state('pop-competition-detail', {
                parent: 'pop-competition',
                url: '/pop-competition/{id}',
                cache:false,
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PopCompetition'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/pop-competition/pop-competition-detail.html',
                        controller: 'PopCompetitionDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PopCompetition', function($stateParams, PopCompetition) {
                        return PopCompetition.get({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'pop-competition',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }],

                }
            })
            .state('pop-competition-detail.edit', {
                parent: 'pop-competition-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/pop-competition/pop-competition-dialog.html',
                        controller: 'PopCompetitionDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['PopCompetition', function(PopCompetition) {
                                return PopCompetition.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('pop-competition.tabPage', {
                parent: 'pop-competition',
                url: '/tabPage/{competitionId}',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/pop-competition/competition-tab-page/pop-com-tab-page.html',
                        controller: 'CompetitionsCtrl'
                    },
                    'compet-tab-content@pop-competition.tabPage' :{
                        templateUrl: 'app/entities/pop-competition/competition-editor/pop-com-editor.html',
                        controller: 'CompetitionEditorCtrl'
                    }
                }
            })
                //tab 页设置

            .state('com-tab-page-editor', {
                parent:'pop-competition.tabPage',
                url:'/competEditor',
                data: {
                    authorities: ['ROLE_USER']
                },
                views:{
                    'compet-tab-content@pop-competition.tabPage' :{
                        templateUrl: 'app/entities/pop-competition/competition-editor/pop-com-editor.html',
                        controller: 'CompetitionEditorCtrl'
                    }
                }
            })
            .state('com-tab-page-theme', {
                parent:'pop-competition.tabPage',
                url:'/competTheme',
                data: {
                    authorities: ['ROLE_USER']
                },
                views:{
                    'compet-tab-content@pop-competition.tabPage' :{
                        templateUrl: 'app/entities/pop-competition/competition-theme/pop-com-theme.html',
                        controller: 'CompetitionThemeCtrl'
                    }
                }
            })
            .state('com-tab-page-prize', {
                    parent:'pop-competition.tabPage',
                    url:'/prizeConfig',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views:{
                        'compet-tab-content@pop-competition.tabPage' :{
                            templateUrl: 'app/entities/pop-competition/competition-prize/pop-com-setprize.html',
                            controller: 'CompetitionSetPrizeCtrl'
                        }
                    }
                })
            .state('com-tab-page-publish', {
                parent:'pop-competition.tabPage',
                url:'/competPublish',
                data: {
                    authorities: ['ROLE_USER']
                },
                views:{
                    'compet-tab-content@pop-competition.tabPage' :{
                        templateUrl: 'app/entities/pop-competition/competition-publish/pop-com-publish.html',
                        controller: 'CompetitionsPublishCtrl'
                    }
                }
            })
            .state('com-tab-page-live', {
                parent:'pop-competition.tabPage',
                url:'/competLive',
                data: {
                    authorities: ['ROLE_USER']
                },
                views:{
                    'compet-tab-content@pop-competition.tabPage' :{
                        templateUrl: 'app/entities/pop-competition/competition-live/pop-com-live.html',
                        controller: 'CompetitionLiveCtrl'
                    }
                }
            })

            .state('pop-competition.edit', {
                parent: 'pop-competition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/pop-competition/pop-competition-dialog.html',
                        controller: 'PopCompetitionDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['PopCompetition', function(PopCompetition) {
                                return PopCompetition.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('pop-competition', null, { reload: 'pop-competition' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            //     .state('pop-competition.edit', {
            //     parent: 'pop-competition',
            //     url: '/{id}/del',
            //     data: {
            // //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/pop-competition/components/delets/del-modal.html',
            //             controller: 'PopCompetitionDialogController',
            //             controllerAs: 'vm',
            //             backdrop: 'static',
            //             size: 'lg',
            //             resolve: {
            //                 entity: ['PopCompetition', function(PopCompetition) {
            //                     return PopCompetition.get({id : $stateParams.id}).$promise;
            //                 }]
            //             }
            //         }).result.then(function() {
            //             $state.go('pop-competition', null, { reload: 'pop-competition' });
            //         }, function() {
            //             $state.go('^');
            //         });
            //     }]
            // })
            .state('pop-competition.delete', {
                parent: 'pop-competition',
                url: '/{id}/delete',
                cache:false,
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/pop-competition/pop-competition-delete-dialog.html',
                        controller: 'PopCompetitionDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['PopCompetition', function(PopCompetition) {
                                return PopCompetition.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('pop-competition', null, { reload: 'pop-competition' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            // 评选设置
            .state('pop-competition.pop-com-judge-config', {
                parent: 'com-tab-page-theme',
                url: '/{subjectId}/judge-config?pAppraiseId&pAppraiseRound&parentAppraiseId&parentAppraiseRound&competitonId',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/pop-competition/competition-judge-config/pop-com-judge-config.html',
                        controller: 'PopCompetitionJudgeConfigController'
                    }
                },
                resolve: {

                }
            })
            // 主题下的轮次
            .state('pop-competition-theme-pop-appraise', {
                parent: 'com-tab-page-theme',
                url: '/{subjectId}/appraises',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/pop-appraise/pop-appraises.html',
                        controller: 'PopAppraiseController'
                    }
                },
                resolve: {

                }
            });
    }

})();
