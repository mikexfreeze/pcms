(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pop-appraise-article', {
            parent: 'entity',
            url: '/pop-appraise-article?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAppraiseArticles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-appraise-article/pop-appraise-articles.html',
                    controller: 'PopAppraiseArticleController',
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
        .state('pop-appraise-article-detail', {
            parent: 'pop-appraise-article',
            url: '/pop-appraise-article/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PopAppraiseArticle'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pop-appraise-article/pop-appraise-article-detail.html',
                    controller: 'PopAppraiseArticleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PopAppraiseArticle', function($stateParams, PopAppraiseArticle) {
                    return PopAppraiseArticle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pop-appraise-article',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pop-appraise-article-detail.edit', {
            parent: 'pop-appraise-article-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise-article/pop-appraise-article-dialog.html',
                    controller: 'PopAppraiseArticleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAppraiseArticle', function(PopAppraiseArticle) {
                            return PopAppraiseArticle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-appraise-article.new', {
            parent: 'pop-appraise-article',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise-article/pop-appraise-article-dialog.html',
                    controller: 'PopAppraiseArticleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                multipleFlag: null,
                                multipleScore: null,
                                revote: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pop-appraise-article', null, { reload: 'pop-appraise-article' });
                }, function() {
                    $state.go('pop-appraise-article');
                });
            }]
        })
        .state('pop-appraise-article.edit', {
            parent: 'pop-appraise-article',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise-article/pop-appraise-article-dialog.html',
                    controller: 'PopAppraiseArticleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PopAppraiseArticle', function(PopAppraiseArticle) {
                            return PopAppraiseArticle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-appraise-article', null, { reload: 'pop-appraise-article' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pop-appraise-article.delete', {
            parent: 'pop-appraise-article',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pop-appraise-article/pop-appraise-article-delete-dialog.html',
                    controller: 'PopAppraiseArticleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PopAppraiseArticle', function(PopAppraiseArticle) {
                            return PopAppraiseArticle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pop-appraise-article', null, { reload: 'pop-appraise-article' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
