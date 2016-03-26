'use strict';

describe('Controller Tests', function() {

    describe('Receipt Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockReceipt, MockCustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockReceipt = jasmine.createSpy('MockReceipt');
            MockCustomer = jasmine.createSpy('MockCustomer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Receipt': MockReceipt,
                'Customer': MockCustomer
            };
            createController = function() {
                $injector.get('$controller')("ReceiptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autoPosApp:receiptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
