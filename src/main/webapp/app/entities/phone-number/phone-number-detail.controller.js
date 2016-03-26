(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PhoneNumberDetailController', PhoneNumberDetailController);

    PhoneNumberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PhoneNumber'];

    function PhoneNumberDetailController($scope, $rootScope, $stateParams, entity, PhoneNumber) {
        var vm = this;
        vm.phoneNumber = entity;
        vm.load = function (id) {
            PhoneNumber.get({id: id}, function(result) {
                vm.phoneNumber = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:phoneNumberUpdate', function(event, result) {
            vm.phoneNumber = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
