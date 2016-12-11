(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('VehicleDetailController', VehicleDetailController);

    VehicleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Vehicle', 'Customer'];

    function VehicleDetailController($scope, $rootScope, $stateParams, entity, Vehicle, Customer) {
        var vm = this;
        vm.vehicle = entity;
        vm.load = function (id) {
            Vehicle.get({id: id}, function(result) {
                vm.vehicle = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:vehicleUpdate', function(event, result) {
            vm.vehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
