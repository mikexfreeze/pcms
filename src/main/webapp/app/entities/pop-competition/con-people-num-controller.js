/**
 * Created by Micheal Xiao on 2017/6/1.
 */

(function() {
    'use strict';
    angular
        .module('pcmsApp')
        .controller('ConPeopleNumCtrl', ConPeopleNumCtrl);

    ConPeopleNumCtrl.$inject = ['$scope', '$state', 'PopCompetition'];

    function ConPeopleNumCtrl ($scope, $state, PopCompetition){

        PopCompetition.getConPeopleNum($scope.x.id)
            .then(function (result) {
                $scope.numObj = result.data
            })

    }

})()
