/**
 * Created by Micheal Xiao on 2017/4/19.
 */
(function() {

    angular
        .module('pcmsApp')
        .controller('judgeCtrl', judgeCtrl);

    judgeCtrl.$inject = ['Principal', 'WaterFall', '$scope', '$rootScope', '$state', '$timeout', 'Auth', '$localStorage', '$q', '$stateParams', 'PopJudgeConfig'];

    function judgeCtrl (Principal, WaterFall, $scope, $rootScope, $state, $timeout, Auth, $localStorage, $q, $stateParams, PopJudgeConfig) {

        var config = {
            conWidth:$("#all-works-contanier").width(),
            height:236,
            horiSpace:10,
            vertSpace:10,
            startTop:0
        };
        $scope.config = config;
        $scope.swiper = {};
        $scope.imgs = [];
        $scope.unFixLine = [];
        var page = {page:0,size:5};
        var condition = "",loadcomplete = true,totalPages = 0;

        //测试数据用完注意注释或删除
        // $scope.picId = 156;
        $scope.swiper.show = false;

        $scope.$on('cdgGetPicCdion', function (event, cond) {
            $scope.imgs = [];
            $scope.unFixLine = [];
            page = {page:0,size:5};
            condition = "";loadcomplete = true;totalPages = 0;config.startTop = 0;

            condition = cond;
            getPicList(condition, page)
        });


        $scope.appraiseId = $stateParams.appraiseId;
        $scope.subjectId = $stateParams.subjectId;
        $scope.judgeId = $stateParams.judgeId;
        $scope.parentAppraiseId = $stateParams.parentAppraiseId;


        PopJudgeConfig.getJudgeById($scope.judgeId)
            .then(function (result) {
                $scope.judgeColor = result.data.colorFlag
            });

        initGetPic();
        function initGetPic() {
            getPicList(condition, page)
                .then(function () {
                    if(config.startTop + config.height + config.vertSpace < $(window).height() && page.page < totalPages - 1){
                        page.page += 1;
                        initGetPic()
                    }
                });
        }



        function getPicList(condition, page) {
            $scope.picProcess = true;
            return WaterFall.getPicList({
                subjectId: $scope.subjectId,
                appraiseId: $scope.appraiseId,
                judgeId:$scope.judgeId,
                flowType:$stateParams.parentAppraiseId?'branch':'trunk',
                voteStatus: condition
            }, page)
                .then(function (result) {
                    var imgs = [],promises = [];
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
                    if($scope.unFixLine.length){
                        var lang = $scope.unFixLine.length;
                        $scope.imgs.splice(-lang,lang)
                    }
                    $scope.imgs = $scope.imgs.concat(reslovData.newItems);
                    $scope.unFixLine = reslovData.unFixLine;
                    // console.log("unFixLine");
                    // console.log($scope.unFixLine);
                    console.log("处理完成之后");
                    console.log($scope.imgs);
                    try{
                        //设置父容器高度
                        $scope.conHeight = $scope.imgs[$scope.imgs.length - 1].top + config.height;
                        //后续更新行起始高度
                        if($scope.unFixLine.length > 0){
                            config.startTop = $scope.unFixLine[0].top;
                        }else{
                            config.startTop = $scope.imgs[$scope.imgs.length - 1].top + config.height + config.vertSpace
                        }

                    }catch (error){}

                })
                .finally(function () {
                    $scope.picProcess = false;
                })
        }

        $(window).scroll(function () {
            var loadPoint = WaterFall.checkscrollside();
            if(WaterFall.checkscrollside() && !$scope.picProcess && (page.page<totalPages-1)){
                // console.log("判断高度");
                // console.log(loadPoint);
                page.page += 1;
                getPicList(condition, page)
                    .then(function () {
                        if(page.page<totalPages-1){
                            page.page += 1;
                            getPicList(condition, page)
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

        Principal.identity().then(function(account) {
            $scope.currentAccount = account;
            $scope.isAdmin = $.inArray('ROLE_ADMIN', account.authorities) >= 0;
        });

        //中图大图class切换
        $scope.switchMLBool = true;
        $scope.switchML = function () {
            $scope.switchMLBool = !$scope.switchMLBool
        }
    }
})();
