(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-award-config', {
            parent: 'entity',
            url: '/pop-award-config?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAwardConfigs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-award-config/pop-award-configs.html',
                    controller: 'PopAwardConfigController',
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
        .state('pop-award-config-detail', {
            parent: 'pop-award-config',
            url: '/pop-award-config/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAwardConfig'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-award-config/pop-award-config-detail.html',
                    controller: 'PopAwardConfigDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopAwardConfig', function($stateParams, PopAwardConfig) {
                    return PopAwardConfig.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-award-config',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-award-config-detail.edit', {
            parent: 'pop-award-config-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award-config/pop-award-config-dialog.html',
                    controller: 'PopAwardConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAwardConfig', function(PopAwardConfig) {
                            return PopAwardConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-award-config.new', {
            parent: 'pop-award-config',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award-config/pop-award-config-dialog.html',
                    controller: 'PopAwardConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                amount: null,
                                background: null,
                                template: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-award-config', null, { reload: 'pop-award-config' });
                }, function() {
                    $state.go('pop-award-config');
                });
            }]
        })
        .state('pop-award-config.edit', {
            parent: 'pop-award-config',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award-config/pop-award-config-dialog.html',
                    controller: 'PopAwardConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAwardConfig', function(PopAwardConfig) {
                            return PopAwardConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-award-config', null, { reload: 'pop-award-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-award-config.delete', {
            parent: 'pop-award-config',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-award-config/pop-award-config-delete-dialog.html',
                    controller: 'PopAwardConfigDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopAwardConfig', function(PopAwardConfig) {
                            return PopAwardConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-award-config', null, { reload: 'pop-award-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
