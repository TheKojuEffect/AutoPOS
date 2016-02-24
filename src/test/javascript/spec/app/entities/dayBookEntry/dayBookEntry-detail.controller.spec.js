'use strict';

describe('Controller Tests', function() {

    describe('DayBookEntry Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDayBookEntry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDayBookEntry = jasmine.createSpy('MockDayBookEntry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DayBookEntry': MockDayBookEntry
            };
            createController = function() {
                $injector.get('$controller')("DayBookEntryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autoposApp:dayBookEntryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
