(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PhoneNumberDetailController', PhoneNumberDetailController);

    PhoneNumberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Phone'];

    function PhoneNumberDetailController($scope, $rootScope, $stateParams, entity, Phone) {
        var vm = this;
        vm.phone = entity;
        vm.load = function (id) {
            Phone.get({id: id}, function(result) {
                vm.phone = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:phoneNumberUpdate', function(event, result) {
            vm.phone = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
