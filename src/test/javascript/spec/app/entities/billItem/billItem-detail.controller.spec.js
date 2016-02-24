'use strict';

describe('Controller Tests', function() {

    describe('BillItem Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBillItem, MockItem, MockBill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBillItem = jasmine.createSpy('MockBillItem');
            MockItem = jasmine.createSpy('MockItem');
            MockBill = jasmine.createSpy('MockBill');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BillItem': MockBillItem,
                'Item': MockItem,
                'Bill': MockBill
            };
            createController = function() {
                $injector.get('$controller')("BillItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autopos:billItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
