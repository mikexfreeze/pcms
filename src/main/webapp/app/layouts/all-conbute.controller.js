/**
 * Created by Micheal Xiao on 2017/5/8.
 */
(function () {

    angular
        .module('pcmsApp')
        .controller('allConbuteCtrl', allConbuteCtrl)
        .controller('delConCtrl', delConCtrl)

    allConbuteCtrl.$inject = ['Principal', 'WaterFall', '$scope', '$rootScope', '$state', '$timeout', 'Auth', '$localStorage', '$q', '$stateParams', 'PopJudgeConfig'];

    function allConbuteCtrl(Principal, WaterFall, $scope, $rootScope, $state, $timeout, Auth, $localStorage, $q, $stateParams, PopJudgeConfig) {

        var config = {
            conWidth: $("#all-works-contanier").width(),
            height: 236,
            horiSpace: 10,
            vertSpace: 10,
            startTop: 0
        };
        $scope.config = config;
        $scope.swiper = {};
        $scope.imgs = [];
        $scope.unFixLine = [];

        var page = {page: 0, size: 5};
        var loadcomplete = true, totalPages = 0;
        console.log("state")
        console.log($stateParams)

        // if($localStorage.user.userLogin.authorities.indexOf("ROLE_ADMIN") != -1){
        //     $scope.showFlag = true;
        // }else{
        //     $scope.showFlag = false
        // }

        $scope.appraiseId = $stateParams.appraiseId;
        $scope.subjectId = $stateParams.subjectId;
        $scope.judgeId = $stateParams.judgeId;





        initGetPic();
        function initGetPic() {
            getPicList(page)
                .then(function () {
                    if (config.startTop + config.height + config.vertSpace < $(window).height() && page.page < totalPages - 1) {
                        page.page += 1;
                        initGetPic()
                    }
                });
        }

        function getPicList(page) {
            $scope.picProcess = true;
            return WaterFall.getAllConbuteList($stateParams.competitionId, page)
                .then(function (result) {
                    var imgs = [], promises = [];
                    totalPages = result.data.totalPages;
                    result.data.content.forEach(function (val) {
                        val.pictureList[0].src = val.pictureList[0].picPath;
                        val.pictureList[0].contributeId = val.id;
                        val.pictureList[0].contributeType = val.contributeType;
                        val.pictureList[0].title = val.title;
                        val.pictureList[0].author = val.author;
                        // val.pictureList[0].swiper = $scope.swper;
                        imgs.push(val.pictureList[0])
                    });
                    imgs.forEach(function (img) {
                        promises.push(
                            WaterFall.getNaturalSize(img.src).then(function (naturalSize) {
                                img.naturalWidth = naturalSize.width;
                                img.naturalHeight = naturalSize.height;
                            })
                        )
                    });

                    return $q.all(promises).then(function () {
                        // console.log('获取原始size之后');
                        // console.log(imgs);
                        return imgs
                    });

                })
                .then(function (imgs) {

                    imgs = $scope.unFixLine.concat(imgs);
                    return WaterFall.reSizeImg(imgs, config)
                })
                .then(function (imgs) {
                    // console.log("接口返回数据初次处理之后");
                    // console.log(imgs);

                    var reslovData = WaterFall.calculate(imgs, config);
                    if ($scope.unFixLine.length) {
                        var lang = $scope.unFixLine.length;
                        $scope.imgs.splice(-lang, lang)
                    }
                    $scope.imgs = $scope.imgs.concat(reslovData.newItems);
                    $scope.unFixLine = reslovData.unFixLine;
                    // console.log("unFixLine");
                    // console.log($scope.unFixLine);
                    console.log("处理完成之后");
                    console.log($scope.imgs);
                    try {
                        //设置父容器高度
                        $scope.conHeight = $scope.imgs[$scope.imgs.length - 1].top + config.height;
                        //后续更新行起始高度
                        if ($scope.unFixLine.length > 0) {
                            config.startTop = $scope.unFixLine[0].top;
                        } else {
                            config.startTop = $scope.imgs[$scope.imgs.length - 1].top + config.height + config.vertSpace
                        }

                    } catch (error) {
                    }

                })
                .finally(function () {
                    $scope.picProcess = false;
                })
        }

        $(window).scroll(function () {
            var loadPoint = WaterFall.checkscrollside();
            if (WaterFall.checkscrollside() && !$scope.picProcess && (page.page < totalPages - 1)) {
                // console.log("判断高度");
                // console.log(loadPoint);
                page.page += 1;
                getPicList(page)
                    .then(function () {
                        if (page.page < totalPages - 1) {
                            page.page += 1;
                            getPicList(page)
                        }
                    })
            }
        });

        $scope.showSwiper = function (id) {
            $scope.swiper.show = true;
            $scope.picId = id
        };

        $scope.closeSwiper = function () {
            $scope.swiper.show = false;
        };

        Principal.identity().then(function (account) {
            $scope.currentAccount = account;
            console.log("account")
            console.log(account)
            // $scope.isAdmin = $.inArray('ROLE_ADMIN', account.authorities) >= 0;
        });

        //中图大图class切换
        $scope.switchMLBool = true;
        $scope.switchML = function () {
            $scope.switchMLBool = !$scope.switchMLBool
        }

    }


    delConCtrl.$inject = ['$scope', '$rootScope', 'WaterFall','$localStorage','Principal'];

    function delConCtrl($scope, $rootScope, WaterFall,$localStorage,Principal) {
        // $scope.roleArr = $localStorage.user.userLogin.authorities[1];
        // console.log($scope.roleArr)
        // if($scope.roleArr == 'ROLE_ADMIN'){
        //     $scope.showFlag = true;
        // }else {
        //     $scope.showFlag = false;
        // }
        // console.log($scope.showFlag)
        // if ($localStorage.user){
        //     $scope.roleArr = $localStorage.user.userLogin.authorities[1];
        //     console.log("role")
        //     console.log($scope.roleArr)
        //     if($scope.roleArr == 'ROLE_ADMIN'){
        //
        //     }else{
        //         $scope.showFlag = false;
        //         $rootScope.initClass = "allContribute";
        //         $scope.indexLink = 'http://118.187.50.42/content/popphoto/home.html'
        //     }
        // }else {
        //     $scope.roleArr = false
        // }


        Principal.identity().then(function(account) {
            $scope.currentAccount = account;
            $scope.isAdmin = $.inArray('ROLE_ADMIN', account.authorities) >= 0;
            if($scope.isAdmin){
                $scope.showFlag = true;
                $scope.indexLink = '#'
            }else {
                $scope.showFlag = false;
                $rootScope.initClass = "allContribute";
                $scope.indexLink = 'http://118.187.50.42/content/popphoto/home.html'
            }
        });

        console.log("del")
        console.log($scope.showFlag)

        $scope.$on('delConbute', function () {
            if($scope.bool){
                WaterFall.deleteConbute($scope.img.contributeId)
                    .then(function () {
                        $scope.img.mask = {
                            show:true,
                            text:'已删除'
                        }
                    })
            }
        })

    }

})();
