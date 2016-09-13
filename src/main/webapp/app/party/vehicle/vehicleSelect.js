(function (angular) {
    'use strict';

    class VehicleSelectController {

        constructor(Vehicle) {
            this.vehicleService = Vehicle;
            this.selectedVehicle = null;
            this.setSelectedVehicle = vehicle => this.selectedVehicle = vehicle;

        }

        $onInit() {
            this.api = this.api || {};
            this.api.setSelectedVehicle = this.setSelectedVehicle;
        }

        searchVehicle(term) {
            return this.vehicleService.query({
                name: '*' + term + '*',
                code: term + '*'
            }).$promise;
        }

        onVehicleSelect(selectedVehicle) {
            this.onSelect({'vehicle': selectedVehicle});
        }

        vehicleTitle(vehicle) {
            if (angular.isObject(vehicle)) {
                return '[' + vehicle.name + '] ' + vehicle.owner.name;
            }
        }
    }

    angular.module('autopos')
        .component('vehicleSelect', {
            templateUrl: 'app/catalog/vehicle/vehicleSelect.html',
            controller: VehicleSelectController,
            bindings: {
                api: '=',
                onSelect: '&'
            }
        });

})(window.angular);