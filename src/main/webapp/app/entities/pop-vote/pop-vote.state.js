(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-vote', {
            parent: 'entity',
            url: '/pop-vote?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopVotes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-vote/pop-votes.html',
                    controller: 'PopVoteController',
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
        .state('pop-vote-detail', {
            parent: 'pop-vote',
            url: '/pop-vote/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopVote'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-vote/pop-vote-detail.html',
                    controller: 'PopVoteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopVote', function($stateParams, PopVote) {
                    return PopVote.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-vote',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-vote-detail.edit', {
            parent: 'pop-vote-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-vote/pop-vote-dialog.html',
                    controller: 'PopVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopVote', function(PopVote) {
                            return PopVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-vote.new', {
            parent: 'pop-vote',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-vote/pop-vote-dialog.html',
                    controller: 'PopVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                voteType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-vote', null, { reload: 'pop-vote' });
                }, function() {
                    $state.go('pop-vote');
                });
            }]
        })
        .state('pop-vote.edit', {
            parent: 'pop-vote',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-vote/pop-vote-dialog.html',
                    controller: 'PopVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopVote', function(PopVote) {
                            return PopVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-vote', null, { reload: 'pop-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-vote.delete', {
            parent: 'pop-vote',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-vote/pop-vote-delete-dialog.html',
                    controller: 'PopVoteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopVote', function(PopVote) {
                            return PopVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-vote', null, { reload: 'pop-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
