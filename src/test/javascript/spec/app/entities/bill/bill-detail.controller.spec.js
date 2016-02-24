'use strict';

describe('Controller Tests', function() {

    describe('Bill Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBill, MockBillItem, MockVehicle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBill = jasmine.createSpy('MockBill');
            MockBillItem = jasmine.createSpy('MockBillItem');
            MockVehicle = jasmine.createSpy('MockVehicle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Bill': MockBill,
                'BillItem': MockBillItem,
                'Vehicle': MockVehicle
            };
            createController = function() {
                $injector.get('$controller')("BillDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autoposApp:billUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
