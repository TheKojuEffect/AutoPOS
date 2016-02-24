'use strict';

describe('Controller Tests', function() {

    describe('Stock Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStock, MockItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStock = jasmine.createSpy('MockStock');
            MockItem = jasmine.createSpy('MockItem');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Stock': MockStock,
                'Item': MockItem
            };
            createController = function() {
                $injector.get('$controller')("StockDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autopos:stockUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
