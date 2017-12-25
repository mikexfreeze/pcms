(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-observer', {
            parent: 'entity',
            url: '/pop-observer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopObservers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-observer/pop-observers.html',
                    controller: 'PopObserverController',
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
        .state('pop-observer-detail', {
            parent: 'pop-observer',
            url: '/pop-observer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopObserver'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-observer/pop-observer-detail.html',
                    controller: 'PopObserverDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopObserver', function($stateParams, PopObserver) {
                    return PopObserver.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-observer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-observer-detail.edit', {
            parent: 'pop-observer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-observer/pop-observer-dialog.html',
                    controller: 'PopObserverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopObserver', function(PopObserver) {
                            return PopObserver.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-observer.new', {
            parent: 'pop-observer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-observer/pop-observer-dialog.html',
                    controller: 'PopObserverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                userName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-observer', null, { reload: 'pop-observer' });
                }, function() {
                    $state.go('pop-observer');
                });
            }]
        })
        .state('pop-observer.edit', {
            parent: 'pop-observer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-observer/pop-observer-dialog.html',
                    controller: 'PopObserverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopObserver', function(PopObserver) {
                            return PopObserver.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-observer', null, { reload: 'pop-observer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-observer.delete', {
            parent: 'pop-observer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-observer/pop-observer-delete-dialog.html',
                    controller: 'PopObserverDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopObserver', function(PopObserver) {
                            return PopObserver.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-observer', null, { reload: 'pop-observer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
