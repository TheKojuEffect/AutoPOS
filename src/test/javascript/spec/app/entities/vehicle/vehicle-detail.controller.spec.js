'use strict';

describe('Controller Tests', function () {

    describe('Vehicle Management Detail Controller', function () {
        var $scope, $rootScope;
        var MockEntity, MockVehicle, MockCustomer;
        var createController;

        beforeEach(inject(function ($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVehicle = jasmine.createSpy('MockVehicle');
            MockCustomer = jasmine.createSpy('MockCustomer');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'Vehicle': MockVehicle,
                'Customer': MockCustomer
            };
            createController = function () {
                $injector.get('$controller')("VehicleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function () {
            it('Unregisters root scope listener upon scope destruction', function () {
                var eventType = 'autoPosApp:vehicleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
