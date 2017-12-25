/**
 * Created by zhaimaojin on 17/3/22.
 */
(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .factory('PopJudgeConfig', PopJudgeConfig);

    PopJudgeConfig.$inject = ['$http', '$localStorage', 'API_URL', 'UAA_URL', '$resource', 'DateUtils', '$q', '$state'];

    function PopJudgeConfig ($http, $localStorage, API_URL, UAA_URL, $resource, DateUtils, $q, $state) {

        var service = {
            getLatestConfigBySubjectId: getLatestConfigBySubjectId,
            getExistingConfigsBySubjectId: getExistingConfigsBySubjectId,
            getExistingBranchBySubjectId:getExistingBranchBySubjectId,
            getAwardConfigsByCompetitionId: getAwardConfigsByCompetitionId,
            saveConfig: saveConfig,
            getJudgesByConfigId: getJudgesByConfigId,
            searchJudgesByUsername: searchJudgesByUsername,
            getJudgeById: getJudgeById,
            saveJudge: saveJudge,
            autoFillJudgesForNewRound: autoFillJudgesForNewRound,
            updateJudge: updateJudge,
            removeJudge: removeJudge,
            getObserversByConfigId: getObserversByConfigId,
            saveObserver: saveObserver,
            removeObserver: removeObserver,
            findContributeNum:findContributeNum,
            countSubmitJudge:countSubmitJudge,
            qryRecentPopAppraisesSubject:qryRecentPopAppraisesSubject,
            fetchVotedJudges: fetchVotedJudges,
            deleteJudgeVoted: deleteJudgeVoted,
            checkRefresh:checkRefresh
        };

        function getLatestConfigBySubjectId(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "subjectId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-max-pop-appraises',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取主题Id返回最新轮次数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getExistingConfigsBySubjectId(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "subjectId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-appraises',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取活动by Id接口返回已有轮次数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getExistingBranchBySubjectId(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "subjectId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-branch-appraises',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取已有分支轮次数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getAwardConfigsByCompetitionId(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "competitionId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/list-pop-award-configs',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取活动by Id接口返回奖项配置数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function saveConfig(config) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": config
            };

            var url = 'api/add-pop-appraises';
            if (config.id > 0) {
                url = 'api/update-pop-appraises';
            } else {
                if (config.parentAppraise) {
                    url = 'api/add-pop-appraises-branch';
                }
            }

            var req = {
                method: 'POST',
                url: API_URL + url,
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        function getJudgesByConfigId(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/list-pop-judges',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取活动by Id接口返回评委数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function searchJudgesByUsername(username) {

            var req = {
                method: 'GET',
                url: UAA_URL + 'api/search/admin/' + username,
            };

            return $http(req)
                .then(function (result) {
                    console.log("根据username查询评委");
                    console.log(result.data);
                    return result.data;
                });
        }

        function getJudgeById(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/find-one-judges',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取评委by Id接口返回评委数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function saveJudge(judge) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": judge
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/add-judge',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        function autoFillJudgesForNewRound(round, subjectId, id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    round: round,
                    subjectId: subjectId,
                    id: id
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/add-pop-appraises-judge',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        function updateJudge(judge) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": judge
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/update-pop-judges',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        function removeJudge(judge) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": judge
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/del-pop-judges',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        function getObserversByConfigId(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-observers-byappraiseid',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取活动by Id接口返回观察员数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function saveObserver(observer) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": observer
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/add-observer',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        function removeObserver(observer) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": observer
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/del-pop-observers',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    return result.data;
                });
        }

        //查询作品数量
        function findContributeNum(appraiseId,subjectId) {

            var req = {
                method: 'POST',
                url: API_URL + 'api/findContributeNum',
                data: {
                    "appId": "string",
                    "channelId": "string",
                    "data": {
                        "appraiseId":appraiseId,
                        "subjectId":subjectId
                    }
                }
            };

            return $http(req)
                .then(function (result) {
                    console.log("获取作品数量接口返回观察员数据");
                    console.log(result.data);
                    return result.data;
                });
        }

        function countSubmitJudge(data) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/count-pop-appraises-judge',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("检查是否有评委提交")
                    console.log(result)
                    return result.data;
                });
        }

        function checkRefresh(data) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": data
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/getLastJudgeInfo',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("检查是否需要跳转")
                    console.log(result)
                    return result.data;
                });
        }

        function qryRecentPopAppraisesSubject(subjectId,userId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "subjectId": subjectId,
                    "userId": userId,
                }
            }

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-recent-pop-appraises-subject',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('查询心跳 接口返回数据');
                    console.log(result.data);
                    return result.data;
                });
        }

        function fetchVotedJudges(appraiseId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": appraiseId,
                }
            }

            var req = {
                method: 'POST',
                url: API_URL + 'api/find-judges-by-appraise',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据轮次ID获取已投评委列表');
                    console.log(result.data);
                    return result.data;
                });
        }

        function deleteJudgeVoted(judgeId, appraiseId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "judgeId": judgeId,
                    "appraiseId": appraiseId,
                }
            }

            var req = {
                method: 'POST',
                url: API_URL + 'api/delVoteByJudgeIdAndAppraiseId',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据judgedId和appraiseId删除大众摄影投票');
                    console.log(result.data);
                    return result.data;
                });
        }


        return service;


    }
})();
