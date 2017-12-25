/**
 * Created by Yue Gu on 2017/4/19.
 */

(function() {
    'use strict';

    angular
        .module('pcmsApp')
        .factory('topBarJudgeService', topBarJudgeService);

    topBarJudgeService.$inject = ['$q', '$http', 'toaster', 'API_URL'];

    function topBarJudgeService ($q, $http, toaster, API_URL) {
        var service = {
            fetchSubjectDetails: fetchSubjectDetails,
            fetchAppraiseDetails: fetchAppraiseDetails,
            fetchBranchAppraise: fetchBranchAppraise,
            fetchUserCompetitionCount: fetchUserCompetitionCount,
            fetchEnteredCount: fetchEnteredCount,
            fetchPreviousAppraises: fetchPreviousAppraises,
            fetchNextAppraises: fetchNextAppraises,
            submit: submit,
            fetchJudgeVoteStatus: fetchJudgeVoteStatus,
            finishAppraiseById: finishAppraiseById,
            finishSubjectById: finishSubjectById,
            countJudgesPolNum:countJudgesPolNum,
            exportAppraiseData: exportAppraiseData,
        };

        function checkResult(result) {
            if(result.data.code > 1000){
                toaster.pop('error',"错误", result.data.msg);
                return $q.reject(result);
            }else{
                return $q.resolve(result);
            }
        }

        function fetchSubjectDetails(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-subject',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据主题ID获取主题详情');
                    console.log(result.data);
                    return result.data;
                });
        }

        function fetchAppraiseDetails(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-appraises-byappraid',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据轮次ID获取轮次详情');
                    console.log(result.data);
                    return result.data;
                });
        }



        function fetchBranchAppraise(id) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "parentAppraiseId": id,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/getMaxBranchAppraiseId',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据轮次ID获取最新分支轮次');
                    console.log(result.data);
                    return result.data;
                });
        }

        function fetchUserCompetitionCount(judgeId, appraiseId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "judgeId": judgeId,
                    "appraiseId": appraiseId,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/get_vote_num',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据用户Id和轮次Id获取已选数量');
                    console.log(result.data);
                    return result.data;
                });
        }

        function fetchEnteredCount(appraiseId, flowType) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "flowType":flowType
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/count-pop-appraise-articles',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据轮次Id获取已入选数量');
                    console.log(result.data);
                    return result.data;
                });
        }

        function fetchPreviousAppraises(appraiseId, round) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": appraiseId,
                    "round": round,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-appraises-grant-bysubjectid',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据轮次Id获取之前轮次列表');
                    console.log(result.data);
                    return result.data;
                });
        }

        function fetchNextAppraises(appraiseId, round) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": appraiseId,
                    "round": round,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-pop-appraises-bysubjectid',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据轮次Id获取之后轮次列表');
                    console.log(result.data);
                    return result.data;
                });
        }

        function submit(judgeId, appraiseId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    id: judgeId, appraiseId: appraiseId, voteStatus: 'SUBMITTED'
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/update-judges-states',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log("评选提交")
                    return result.data;
                });
        }

        function fetchJudgeVoteStatus(appraiseId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/qry-judges-states',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据主题Id获取已选数量');
                    console.log(result.data);
                    return result.data;
                });
        }

        function finishSubjectById(subjectId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": subjectId,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/finish_subject',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据主题Id结束评选');
                    console.log(result.data);
                    return result.data;
                });
        }

        function finishAppraiseById(appraiseId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "id": appraiseId,
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/end-pop-appraises',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('根据轮次Id结束此轮次评选');
                    console.log(result.data);
                    return result.data;
                });
        }
        
        function countJudgesPolNum(appraiseId,judgeId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "id": judgeId
                }
            };

            var req = {
                method: 'POST',
                url: API_URL + 'api/count-judges-pollnum',
                data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('查询count和polnumber接口返回数据');
                    console.log(result.data);
                    return result.data;
                });
        }

        function exportAppraiseData(appraiseId, round, subjectId) {
            var data = {
                "appId": "string",
                "channelId": "string",
                "data": {
                    "appraiseId": appraiseId,
                    "round": round,
                    "subjectId": subjectId
                }
            };

            var req = {
                method: 'GET',
                url: API_URL + 'api/export-appraise-data/'+ round + '/' + subjectId,
                // data: data,
            };

            return $http(req)
                .then(function (result) {
                    console.log('导出轮次数据');
                    console.log(result.data);
                    return result.data;
                });
        }

        return service;
    }
})();
