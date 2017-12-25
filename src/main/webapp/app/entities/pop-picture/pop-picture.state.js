(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-picture', {
            parent: 'entity',
            url: '/pop-picture?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopPictures'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-picture/pop-pictures.html',
                    controller: 'PopPictureController',
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
        .state('pop-picture-detail', {
            parent: 'pop-picture',
            url: '/pop-picture/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopPicture'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-picture/pop-picture-detail.html',
                    controller: 'PopPictureDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopPicture', function($stateParams, PopPicture) {
                    return PopPicture.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-picture',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-picture-detail.edit', {
            parent: 'pop-picture-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-picture/pop-picture-dialog.html',
                    controller: 'PopPictureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopPicture', function(PopPicture) {
                            return PopPicture.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-picture.new', {
            parent: 'pop-picture',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-picture/pop-picture-dialog.html',
                    controller: 'PopPictureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                picPath: null,
                                remark: null,
                                shootAddress: null,
                                shootDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-picture', null, { reload: 'pop-picture' });
                }, function() {
                    $state.go('pop-picture');
                });
            }]
        })
        .state('pop-picture.edit', {
            parent: 'pop-picture',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-picture/pop-picture-dialog.html',
                    controller: 'PopPictureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopPicture', function(PopPicture) {
                            return PopPicture.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-picture', null, { reload: 'pop-picture' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-picture.delete', {
            parent: 'pop-picture',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-picture/pop-picture-delete-dialog.html',
                    controller: 'PopPictureDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopPicture', function(PopPicture) {
                            return PopPicture.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-picture', null, { reload: 'pop-picture' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
