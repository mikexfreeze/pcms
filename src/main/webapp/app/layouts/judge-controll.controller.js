/**
 * Created by Micheal Xiao on 2017/4/21.
 */
(function () {

    angular
        .module('pcmsApp')
        .controller('judgeConCtrl', judgeConCtrl)
        .directive('convertToNumber', function () {
            return {
                require: 'ngModel',
                link: function (scope, element, attrs, ngModel) {
                    ngModel.$parsers.push(function (val) {
                        return val != null ? parseInt(val, 10) : null;
                    });
                    ngModel.$formatters.push(function (val) {
                        return val != null ? '' + val : null;
                    });
                }
            };
        });

    judgeConCtrl.$inject = ['Principal', 'WaterFall', '$scope', '$rootScope', '$state', '$timeout', 'Auth', '$localStorage', '$q', '$stateParams', 'PopJudgeConfig', 'topBarJudgeService', 'toaster','PopJudge'];

    function judgeConCtrl(Principal, WaterFall, $scope, $rootScope, $state, $timeout, Auth, $localStorage, $q, $stateParams, PopJudgeConfig, topBarJudgeService, toaster,PopJudge) {
        var config = {
            conWidth: $("#all-works-contanier").width(),
            height: 236,
            horiSpace: 10,
            vertSpace: 10,
            startTop: 0
        };

        $scope.imgs = [];
        $scope.swiper = {};
        $scope.unFixLine = [];
        var page = {page: 0, size: 15};
        var voteNum = "", loadcomplete = true, totalPages = 0;
        $scope.competitionId = $stateParams.competitionId;
        $scope.appraiseId = $stateParams.appraiseId;
        $scope.subjectId = $stateParams.subjectId;
        // $scope.judgeId = $stateParams.judgeId;
        $scope.branchAppraise = $stateParams.branchAppraise;
        $scope.toggle = true;

        if(!$stateParams.submitNum){
            $scope.submitNum = 0
        }else{
            $scope.submitNum = $stateParams.submitNum
        }
        if($stateParams.branchAppraise){
            $scope.flowType = $stateParams.branchAppraise
        }else{
            $scope.flowType = "trunk";
        }

        //检查是否有评委提交
        function checkAllSub() {
            // PopJudgeConfig.countSubmitJudge( {
            //     "appraiseType": $scope.flowType.toUpperCase(),
            //     "id": $scope.appraiseId
            // })
            //     .then(function (result) {
            //         console.log(result)
            //     if(result.data > $scope.submitNum){
            //         $scope.submitNum = result.data
            //         // toaster.pop("info","有新评委提交")
            //         $state.go($state.current, {
            //             submitNum:$scope.submitNum,
            //             branchAppraise:$scope.flowType,
            //         }, {reload: true});
            //     }else{
            //         $timeout(function () {
            //             checkAllSub();
            //             console.log("112233")
            //         },5000)
            //     }
            // })
        }

        Principal.identity().then(function (account) {
            $scope.currentAccount = account;
            $scope.isAdmin = $.inArray('ROLE_ADMIN', account.authorities) >= 0;
            if($scope.isAdmin == true){
                // checkAllSub();
            }else {
                // checkAllSub();
                // checkSubVoted();
            }
            // fetchSubjectsByJudgeIdAndStatus()
        });

        $scope.appraiseSelected = function () {
            console.log($scope.appraise.id);
            if ($scope.appraise.id) {
                $state.go("judge-controll", {
                    subjectId: $scope.subjectId,
                    appraiseId: $scope.appraise.id,
                    competitionId: $scope.competitionId
                });
            }
        };

        $scope.changeBranch = function () {
            $state.go("judge-controll", {
                subjectId: $scope.subjectId,
                appraiseId: $scope.appraise.id,
                competitionId: $scope.competitionId,
                branchAppraise:$scope.flowType,
                submitNum:0
            });
        };

        $scope.appraise = {};




        function fetchAppraiseDetails() {
            topBarJudgeService.fetchAppraiseDetails($scope.appraiseId)
                .then(function (result) {
                    $scope.appraise = result.data;
                    $scope.appraise.id = parseInt($scope.appraise.id, 10)
                    $scope.parentAppraise = result.data.parentAppraise;
                });
        }

        function fetchAllAppraises() {
            PopJudgeConfig.getExistingConfigsBySubjectId($scope.subjectId)
                .then(function (result) {
                    $scope.appraises = result.data.content;
                    fetchAppraiseDetails();
                }, function (error) {

                });
        }

        function fetchAllBranchAppraises() {
            PopJudgeConfig.getExistingBranchBySubjectId($scope.subjectId)
                .then(function (result) {
                    $scope.branchAppraises = result.data;
                    fetchAppraiseDetails();
                }, function (error) {

                });
        }
        fetchAllBranchAppraises();
        fetchAllAppraises();
        fetchJudgeVoteStatus();

        function fetchJudgeVoteStatus() {
            topBarJudgeService.fetchJudgeVoteStatus($scope.appraiseId)
                .then(function (result) {
                    if (result.data) {
                        var results = result.data;
                        var submittedJudges = [];
                        var unSubmittedJudges = [];
                        for (var i = 0; i < results.length; i++) {
                            if (results[i].voteStatus == 'SUBMITTED') {
                                submittedJudges.push(results[i]);
                            } else {
                                unSubmittedJudges.push(results[i]);
                            }
                        }
                        $rootScope.submittedJudges = submittedJudges;
                        $rootScope.unSubmittedJudges = unSubmittedJudges;
                        if (unSubmittedJudges.length <= 0) {
                            $scope.allVoted = true;
                        } else {
                            $scope.allVoted = false;
                        }
                    }
                });
        }

        var picList = [];
        $scope.checkPic = function (img, bool) {
            if (bool) {
                picList.push(img)
            } else {

            }
            $rootScope.$broadcast('checkPic', picList)
        };

        $scope.getPicByVote = function (voteNum) {
            $scope.imgs = [];
            $scope.unFixLine = [];
            page = {page:0,size:15};
            loadcomplete = true;totalPages = 0;config.startTop = 0;

            getPicList(voteNum, page)
        };

        //getPicList 公用代码开始
        getPicList(voteNum, page);
        function getPicList(voteNum, page,order) {
            $scope.picProcess = true;
            return WaterFall.getPicCtrlPage({
                subjectId: $scope.subjectId,
                appraiseId: $scope.appraiseId,
                voteNum : voteNum || "",
                flowType:$scope.flowType,
                orderBy: order || ""
            }, page)
                .then(function (result) {
                    var imgs = [], promises = [];
                    $scope.totalElements = result.data.totalElements;
                    totalPages = result.data.totalPages;
                    // $scope.status = result.data.content[0].voteStatus
                    result.data.content.forEach(function (val) {
                        val.pictureList[0].src = val.pictureList[0].picPath;
                        val.pictureList[0].contributeId = val.id;
                        val.pictureList[0].contributeType = val.contributeType;
                        val.pictureList[0].title = val.title;
                        val.pictureList[0].author = val.author;
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

                    } catch (error) {}

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
                getPicList(voteNum, page)
            }
        });

        //全选按钮
        $scope.selectAll = function () {
            if(angular.element(":checkbox").scope().bool){
                angular.forEach(angular.element(":checkbox"), function(value, key) {
                    angular.element(value).scope().bool = false;
                });
            }else{
                angular.forEach(angular.element(":checkbox"), function(value, key) {
                    angular.element(value).scope().bool = true;
                });
            }
        };

        //按照ID排序
        $scope.getPciListOrderById = function(voteNum,page,order) {
            // var order = "userId"
            $scope.imgs = [];
            $scope.unFixLine = [];
            loadcomplete = true;totalPages = 0;config.startTop = 0;
            getPicList(voteNum,page,order)
        }
        $scope.getPciListInitial = function() {
            // var order = "userId"
            $scope.imgs = [];
            $scope.unFixLine = [];
            loadcomplete = true;totalPages = 0;config.startTop = 0;
            getPicList();
        }

        //中图、大图
        $scope.showSwiper = function (id) {
            $scope.swiper.show = true;
            $scope.picId = id
        };

        $scope.closeSwiper = function () {
            $scope.swiper.show = false;
        };



        //中图大图class切换
        $scope.switchMLBool = true;
        $scope.switchML = function () {
            $scope.switchMLBool = !$scope.switchMLBool
        }
        $scope.toggleId = function () {
            $scope.toggle = !$scope.toggle
        }

        //阻止冒泡事件
        // $scope.stopPro = function ($event) {
        //     console.log($event)
        //     $event.stopPropagation()
        // }


        //    心跳
        $scope.userId = $localStorage.user.userLogin.id;

        function checkSubVoted() {
            PopJudgeConfig.qryRecentPopAppraisesSubject($scope.subjectId, $scope.userId)
                .then(function (result) {
                    console.log("xintiaoxintiaoxintiaoxintiaoxintiao")
                    console.log(result)
                    $scope.voteStatus = result.data.subjectStatus;
                    console.log($scope.voteStatus);

                    if($scope.voteStatus == 'VOTE_FINISH'){
                        $state.go('pop-judge', null, {reload: true});
                    }else{
                        if($scope.appraiseId){
                            if(result.data.id > $scope.appraiseId){

                                $scope.appraiseId = result.data.id
                                // toaster.pop("info","有新评委提交")
                                $state.go("judge", {judgeId:$localStorage.user.userLogin.id,appraiseId:$scope.appraiseId,subjectId:$scope.subjectId,parentAppraiseId:$scope.parentAppraise},{reload: true});
                                // $state.go($state.current, {appraiseId:$scope.appraiseId}, {reload: true});
                            }
                            else{
                                $timeout(function () {
                                    checkSubVoted()
                                },5000)
                            }
                        }
                        else if($scope.parentAppraise){
                            if(result.data.parentAppraise > $scope.parentAppraise){
                                $scope.parentAppraiseId = result.data.parentAppraise
                                // toaster.pop("info","有新评委提交")
                                $state.go($state.current, {
                                    parentAppraiseId:$scope.parentAppraiseId,
                                }, {reload: true});}
                            else{
                                $timeout(function () {
                                    checkSubVoted()
                                },5000)
                            }
                        }
                    }
                })
        }


    }
})();
