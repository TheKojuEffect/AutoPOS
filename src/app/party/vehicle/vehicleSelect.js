(function (angular) {
    'use strict';

    class VehicleSelectController {

        constructor(Vehicle) {
            this.vehicleService = Vehicle;
        }

        searchVehicle(term) {
            return this.vehicleService.query({
                q: term
            }).$promise;
        }

        vehicleTitle(vehicle) {
            if (angular.isObject(vehicle)) {
                const ownerName = vehicle.owner && vehicle.owner.name || '';
                return `[ ${vehicle.number} ] ${ownerName}`;
            }
        }
    }

    angular.module('autopos')
        .component('vehicleSelect', {
            templateUrl: 'app/party/vehicle/vehicleSelect.html',
            controller: VehicleSelectController,
            bindings: {
                selectedVehicle: '='
            }
        });

})(window.angular);
