(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-appraise', {
            parent: 'entity',
            url: '/pop-appraise?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAppraises'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-appraise/pop-appraises.html',
                    controller: 'PopAppraiseController',
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
        .state('pop-appraise-detail', {
            parent: 'pop-appraise',
            url: '/pop-appraise/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAppraise'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-appraise/pop-appraise-detail.html',
                    controller: 'PopAppraiseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopAppraise', function($stateParams, PopAppraise) {
                    return PopAppraise.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-appraise',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-appraise-detail.edit', {
            parent: 'pop-appraise-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise/pop-appraise-dialog.html',
                    controller: 'PopAppraiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAppraise', function(PopAppraise) {
                            return PopAppraise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-appraise.new', {
            parent: 'pop-appraise',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise/pop-appraise-dialog.html',
                    controller: 'PopAppraiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                round: null,
                                appraiseType: null,
                                pollNum: null,
                                remark: null,
                                awardName: null,
                                status: null,
                                awardConfigId: null,
                                parentAppraise: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-appraise', null, { reload: 'pop-appraise' });
                }, function() {
                    $state.go('pop-appraise');
                });
            }]
        })
        .state('pop-appraise.edit', {
            parent: 'pop-appraise',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise/pop-appraise-dialog.html',
                    controller: 'PopAppraiseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAppraise', function(PopAppraise) {
                            return PopAppraise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-appraise', null, { reload: 'pop-appraise' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-appraise.delete', {
            parent: 'pop-appraise',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise/pop-appraise-delete-dialog.html',
                    controller: 'PopAppraiseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopAppraise', function(PopAppraise) {
                            return PopAppraise.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-appraise', null, { reload: 'pop-appraise' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
