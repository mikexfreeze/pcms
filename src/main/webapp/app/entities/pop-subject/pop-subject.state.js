(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-subject', {
            parent: 'entity',
            url: '/pop-subject?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopSubjects'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-subject/pop-subjects.html',
                    controller: 'PopSubjectController',
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
        .state('pop-subject-detail', {
            parent: 'pop-subject',
            url: '/pop-subject/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopSubject'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-subject/pop-subject-detail.html',
                    controller: 'PopSubjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopSubject', function($stateParams, PopSubject) {
                    return PopSubject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-subject',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-subject-detail.edit', {
            parent: 'pop-subject-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-subject/pop-subject-dialog.html',
                    controller: 'PopSubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopSubject', function(PopSubject) {
                            return PopSubject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-subject.new', {
            parent: 'pop-subject',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-subject/pop-subject-dialog.html',
                    controller: 'PopSubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                maxLimit: null,
                                groupMaxLimit: null,
                                assetDir: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-subject', null, { reload: 'pop-subject' });
                }, function() {
                    $state.go('pop-subject');
                });
            }]
        })
        .state('pop-subject.edit', {
            parent: 'pop-subject',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-subject/pop-subject-dialog.html',
                    controller: 'PopSubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopSubject', function(PopSubject) {
                            return PopSubject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-subject', null, { reload: 'pop-subject' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-subject.delete', {
            parent: 'pop-subject',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-subject/pop-subject-delete-dialog.html',
                    controller: 'PopSubjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopSubject', function(PopSubject) {
                            return PopSubject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-subject', null, { reload: 'pop-subject' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
