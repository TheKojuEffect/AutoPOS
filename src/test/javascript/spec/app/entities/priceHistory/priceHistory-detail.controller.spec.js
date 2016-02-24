'use strict';

describe('Controller Tests', function() {

    describe('PriceHistory Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPriceHistory, MockItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPriceHistory = jasmine.createSpy('MockPriceHistory');
            MockItem = jasmine.createSpy('MockItem');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PriceHistory': MockPriceHistory,
                'Item': MockItem
            };
            createController = function() {
                $injector.get('$controller')("PriceHistoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autopos:priceHistoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
