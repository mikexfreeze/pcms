/**
 * Created by Micheal Xiao on 2017/4/19.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('WaterFall', WaterFall);

    WaterFall.$inject = ['$http', '$localStorage', 'API_URL', 'toaster', '$q', '$state'];

    function WaterFall ($http, $localStorage, API_URL, toaster, $q, $state) {

        var service = {
            getPicList:getPicList,
            getAllConbuteList:getAllConbuteList,
            getPicCtrlPage:getPicCtrlPage,
            getJudgeCollection:getJudgeCollection,
            reSizeImg:reSizeImg,
            getNaturalSize:getNaturalSize,
            calculate:calculate,
            checkscrollside:checkscrollside,
            deleteConbute:deleteConbute
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result)
            }else{
                return $q.resolve(result)
            }
        }

        function getPicList(data, page) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getPictureByAuth',
                data: data
            };
            if(page){
                req.params = {"page": page.page, "size": page.size || "20"};
            }
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取图片列表接口返回数据");
                    console.log(result.data);
                    return result.data;
                });


        }

        function getAllConbuteList(id,page) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "competitionId":id
                }
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getAllContributeList',
                data: data
            };
            if(page){
                req.params = {"page": page.page, "size": page.size || "20"};
            }
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取所有投稿图片列表 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getPicCtrlPage(data, page) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/findAppraiseCollect',
                data: data
            };
            if(page){
                req.params = {"page": page.page, "size": page.size || "20"};
            }
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("评选控制及投票汇总页面获取图片列表接口返回数据");
                    console.log(result.data);
                    return result.data;
                });


        }

        function getJudgeCollection(data, page) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/getJudgeCollection',
                data: data
            };
            if(page){
                req.params = {"page": page.page, "size": page.size || "20"};
            }
            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("获取图片列表接口返回数据");
                    console.log(result.data);
                    return result.data;
                });


        }

        function reSizeImg(imgs, config) {
            imgs.forEach(function (img) {
                var rate = img.naturalWidth/img.naturalHeight;
                img.height = config.height;
                img.width = Math.floor(config.height * rate)
            });

            // console.log("reSize 之后数据");
            // console.log(imgs);

            return imgs
        }

        function getNaturalSize(src) {
            if(!src){
                return $q.resolve({
                    width:0,
                    height:236
                })
            }
            var image = new Image();
            image.src = src;

            return $q(function(resolve, reject) {
                image.onload = function() {
                    var height = image.naturalHeight;
                    var width = image.naturalWidth;
                    // console.log ('The image size is '+width+'*'+height);
                    var naturalSize = {
                        width:width,
                        height:height
                    };
                    resolve(naturalSize)
                };
            });
        }

        function calculate(items, config) {

            //函数内全局变量
            var contLeftSet = 0,newItems = [],unFixLine = [];
            var conWidth = config.conWidth;
            var heigth = config.height;
            var startTop = config.startTop;

            var lineNum = 0,lines = [];
            //cutItems需要lineNum lines 两个外部变量
            cutItems(items, config);
            // console.log("剪切处理后数据");
            // console.log(lines);

            if(!lines[lines.length - 1].fix){
                unFixLine = lines[lines.length - 1].array;
            }

            //处理每行图片样式
            lines.forEach(function (val, index, lines) {
                if(val.fix){
                    var contLeftSet = 0;
                    val.array.forEach(function (v, n, items) {
                        var curWidth = Math.floor(v.width * val.rate);
                        v.left = contLeftSet;
                        v.width = curWidth;
                        v.top = (val.lineNum - 1) * (heigth + config.vertSpace) + startTop;
                        contLeftSet += curWidth + config.horiSpace;
                        newItems.push(v)
                    })
                }else{
                    //处理不满行的情况
                    var contLeftSet = 0;
                    val.array.forEach(function (v, n, items) {
                        v.left = contLeftSet;
                        v.top = (val.lineNum - 1) * (heigth + config.vertSpace) + startTop;
                        contLeftSet += v.width + config.horiSpace;
                        newItems.push(v)
                    })

                }

            });

            // console.log("每行处理之后数据");
            // console.log(newItems);

            function cutItems(items, config) {

                var oneLineData = {},restItems = [];
                var contLineWidth = 0,rate = 0;

                //剪切每行数据
                var fixBool = items.some(function (val,index,items) {
                    contLineWidth += (Number(val.width) + config.horiSpace);
                    if((contLineWidth - config.horiSpace) > (conWidth - 100)){
                        restItems = items.splice(index + 1, items.length - 1);
                        lineNum ++;

                        oneLineData = {
                            array : items,
                            rate : (conWidth - ((items.length-1)*config.horiSpace))/(contLineWidth - (items.length*config.horiSpace)),
                            lineNum : lineNum,
                            fix : true
                        };

                        lines.push(oneLineData);
                        cutItems(restItems, config);

                        return true
                    }
                });
                if(!fixBool){
                    lineNum ++;
                    oneLineData = {
                        array : items,
                        lineNum : lineNum,
                        fix : false
                    };
                    lines.push(oneLineData);
                }

            }


            return {newItems:newItems,unFixLine:unFixLine}
        }

        function checkscrollside(){
            var $aPin = $( ".img-container" );
            var lastPinH = $aPin.last().get(0).offsetTop + Math.floor($aPin.last().height()/2);//创建【触发添加块框函数waterfall()】的高度：最后一个块框的距离网页顶部+自身高的一半(实现未滚到底就开始加载)
            var scrollTop = $( window ).scrollTop();//注意解决兼容性
            var documentH = $( window ).height();//页面高度
            return (lastPinH < scrollTop + documentH ) ? true : false;//到达指定高度后 返回true，触发waterfall()函数
        }

        function deleteConbute(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "contributeId":id
                }
            };
            var req = {
                method: 'POST',
                url: API_URL + 'api/failure-contribute',
                data: data
            };

            return $http(req)
                .then(function (result) {
                    return checkResult(result)
                })
                .then(function (result) {
                    console.log("删除作品 接口返回数据");
                    console.log(result.data);
                    return result.data;
                });


        }

        return service;

    }
})();
