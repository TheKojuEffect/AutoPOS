'use strict';

describe('Controller Tests', function() {

    describe('Ledger Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLedger, MockCustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLedger = jasmine.createSpy('MockLedger');
            MockCustomer = jasmine.createSpy('MockCustomer');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Ledger': MockLedger,
                'Customer': MockCustomer
            };
            createController = function() {
                $injector.get('$controller')("LedgerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autopos:ledgerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
