'use strict';

describe('Controller Tests', function() {

    describe('LedgerEntry Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLedgerEntry, MockLedger;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLedgerEntry = jasmine.createSpy('MockLedgerEntry');
            MockLedger = jasmine.createSpy('MockLedger');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LedgerEntry': MockLedgerEntry,
                'Ledger': MockLedger
            };
            createController = function() {
                $injector.get('$controller')("LedgerEntryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autoposApp:ledgerEntryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
