'use strict';

describe('Controller Tests', function() {

    describe('StockHistory Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStockHistory, MockItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStockHistory = jasmine.createSpy('MockStockHistory');
            MockItem = jasmine.createSpy('MockItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'StockHistory': MockStockHistory,
                'Item': MockItem
            };
            createController = function() {
                $injector.get('$controller')("StockHistoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autoposApp:stockHistoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
