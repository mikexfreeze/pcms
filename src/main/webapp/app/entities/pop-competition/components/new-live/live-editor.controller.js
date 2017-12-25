/**
 * Created by Micheal Xiao on 2017/3/17.
 */
angular
    .module('pcmsApp')
    .controller('LiveEditorCtrl', LiveEditorCtrl);

LiveEditorCtrl.$inject = ['$scope', '$uibModal', '$uibModalInstance', '$timeout','PopLive','User'];

function LiveEditorCtrl($scope, $uibModal, $uibModalInstance, $timeout,PopLive,User) {
    var $ctrl = this;
    $scope.guestName = '';

    var vm = this;
    vm.search = ''
    $scope.vm = vm;
    vm.searchGuest = searchGuest;
    vm.fetchGuest = fetchGuest;

    console.log("liveid")
console.log($scope.liveId)
    $ctrl.ok = function () {
        $uibModalInstance.close($ctrl.selected.item);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    }

    $scope.openColorPicker = function () {
        $(".hidden-colorpicke").focus();
    }

    function fetchGuest () {
        User.getAdmins({
            userName: vm.search,
            roleNames: "'ROLE_GUEST'"
        }, onSuccess, onError);
    }

    vm.fetchGuest();

    function searchGuest() {
        fetchGuest();
    }

    function onSuccess(data, headers) {
        // vm.links = ParseLinks.parse(headers('link'));
        // vm.totalItems = headers('X-Total-Count') - hiddenUsersSize;
        console.log('111222')
        console.log(data);
        $scope.guests = data;
    }

    function onError(error) {
        toaster.pop('err',error.message);
    }


    $scope.guestSelected = function(data) {
        $scope.userName = data.login;
        $scope.userId = data.id;
        console.log("select")
        console.log(data.login)
    }
    
    $scope.save = function () {
        var queryData = {
            "color": $scope.color_picker,
            "userId":$scope.userId,
            "liveId":$scope.liveId
        }
        PopLive.addGuest(queryData)
            .then(function (result) {
                console.log("addguest")
                console.log(result)
            })
            .then(function () {
                $uibModalInstance.close($scope.liveId);
            })
    }
}
