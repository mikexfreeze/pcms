(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-contribute', {
            parent: 'entity',
            url: '/pop-contribute?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopContributes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-contribute/pop-contributes.html',
                    controller: 'PopContributeController',
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
        .state('pop-contribute-detail', {
            parent: 'pop-contribute',
            url: '/pop-contribute/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopContribute'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-contribute/pop-contribute-detail.html',
                    controller: 'PopContributeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopContribute', function($stateParams, PopContribute) {
                    return PopContribute.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-contribute',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-contribute-detail.edit', {
            parent: 'pop-contribute-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-contribute/pop-contribute-dialog.html',
                    controller: 'PopContributeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopContribute', function(PopContribute) {
                            return PopContribute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-contribute.new', {
            parent: 'pop-contribute',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-contribute/pop-contribute-dialog.html',
                    controller: 'PopContributeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contributeType: null,
                                title: null,
                                assetDir: null,
                                status: null,
                                author: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-contribute', null, { reload: 'pop-contribute' });
                }, function() {
                    $state.go('pop-contribute');
                });
            }]
        })
        .state('pop-contribute.edit', {
            parent: 'pop-contribute',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-contribute/pop-contribute-dialog.html',
                    controller: 'PopContributeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopContribute', function(PopContribute) {
                            return PopContribute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-contribute', null, { reload: 'pop-contribute' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-contribute.delete', {
            parent: 'pop-contribute',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-contribute/pop-contribute-delete-dialog.html',
                    controller: 'PopContributeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopContribute', function(PopContribute) {
                            return PopContribute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-contribute', null, { reload: 'pop-contribute' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
