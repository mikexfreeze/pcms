(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-award', {
            parent: 'entity',
            url: '/pop-award?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAwards'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-award/pop-awards.html',
                    controller: 'PopAwardController',
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
        .state('pop-award-detail', {
            parent: 'pop-award',
            url: '/pop-award/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAward'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-award/pop-award-detail.html',
                    controller: 'PopAwardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopAward', function($stateParams, PopAward) {
                    return PopAward.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-award',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-award-detail.edit', {
            parent: 'pop-award-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award/pop-award-dialog.html',
                    controller: 'PopAwardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAward', function(PopAward) {
                            return PopAward.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-award.new', {
            parent: 'pop-award',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award/pop-award-dialog.html',
                    controller: 'PopAwardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                content: null,
                                background: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-award', null, { reload: 'pop-award' });
                }, function() {
                    $state.go('pop-award');
                });
            }]
        })
        .state('pop-award.edit', {
            parent: 'pop-award',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award/pop-award-dialog.html',
                    controller: 'PopAwardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAward', function(PopAward) {
                            return PopAward.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-award', null, { reload: 'pop-award' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-award.delete', {
            parent: 'pop-award',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award/pop-award-delete-dialog.html',
                    controller: 'PopAwardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopAward', function(PopAward) {
                            return PopAward.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-award', null, { reload: 'pop-award' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
