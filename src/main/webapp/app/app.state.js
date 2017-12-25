(function () {

    angular
        .module('pcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('app', {
                abstract: true,
                views: {
                    'navbar@': {
                        templateUrl: 'app/layouts/navbar/navbar.html',
                        controller: 'NavbarController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    authorize: ['Auth',
                        function (Auth) {
                            return Auth.authorize();
                        }
                    ],
                    initClass: ['$rootScope',
                        function ($rootScope) {
                            $rootScope.initClass = 'origin'
                        }
                    ]
                }
            })
            .state('judge', {
                cache: false,
                url: '/judge?judgeId&appraiseId&subjectId&parentAppraiseId',
                data: {
                    authorities: ['ROLE_JUDGE'],
                    pageTitle: '评选'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'app/layouts/topbar-judge/navbar.html',
                        controller: 'topBarJudgeCtrl'
                    },
                    'content@': {
                        templateUrl: 'app/layouts/judge.html',
                        controller: 'judgeCtrl'
                    }
                },
                resolve: {
                    authorize: ['Auth',
                        function (Auth) {
                            return Auth.authorize();
                        }
                    ],
                    initClass: ['$rootScope',
                        function ($rootScope) {
                            $rootScope.initClass = 'judge'
                        }
                    ]
                }
            })
            // 评选控制
            // 评选汇总
            // 观察员观察界面
            .state('judge-controll', {
                url: '/judge-controll?judgeId&appraiseId&subjectId&competitionId&branchAppraise&submitNum',
                data: {
                    authorities: ['ROLE_JUDGE', 'ROLE_ADMIN', 'ROLE_OBSERVER'],
                    pageTitle: '评选'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'app/layouts/topbar-judge-controll/judge-controll-navbar.html',
                        controller: 'judgeControllTopBarJudgeCtrl'
                    },
                    'content@': {
                        templateUrl: 'app/layouts/judge-controll.html',
                        controller: 'judgeConCtrl'
                    }
                },
                resolve: {
                    authorize: ['Auth',
                        function (Auth) {
                            return Auth.authorize();
                        }
                    ],
                    initClass: ['$rootScope',
                        function ($rootScope) {
                            $rootScope.initClass = 'judgeCtrl'
                        }
                    ]
                }

            })
            //全部作品

            .state('allContube', {
                url: '/allcontube?competitionId&role',
                data: {
                    authorities: [],
                    pageTitle: '全部作品'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'app/layouts/all-conbute-topbar/all-conbute-topbar.html',
                        controller: 'allConTopCtrl'
                    },
                    'content@': {
                        templateUrl: 'app/layouts/all-conbute.html',
                        controller: 'allConbuteCtrl'
                    }
                },
                resolve: {
                    initClass: ['$rootScope',
                        function ($rootScope) {
                            $rootScope.initClass = 'judge'
                        }
                    ]
                }
            })
    }
})();
