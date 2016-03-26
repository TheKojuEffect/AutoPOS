(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('stock-history', {
            parent: 'entity',
            url: '/stock-history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.stockHistory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stock-history/stock-histories.html',
                    controller: 'StockHistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('stockHistory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('stock-history-detail', {
            parent: 'entity',
            url: '/stock-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.stockHistory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stock-history/stock-history-detail.html',
                    controller: 'StockHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('stockHistory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StockHistory', function($stateParams, StockHistory) {
                    return StockHistory.get({id : $stateParams.id});
                }]
            }
        })
        .state('stock-history.new', {
            parent: 'stock-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stock-history/stock-history-dialog.html',
                    controller: 'StockHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                quantity: null,
                                remarks: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('stock-history', null, { reload: true });
                }, function() {
                    $state.go('stock-history');
                });
            }]
        })
        .state('stock-history.edit', {
            parent: 'stock-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stock-history/stock-history-dialog.html',
                    controller: 'StockHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StockHistory', function(StockHistory) {
                            return StockHistory.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('stock-history', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stock-history.delete', {
            parent: 'stock-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stock-history/stock-history-delete-dialog.html',
                    controller: 'StockHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StockHistory', function(StockHistory) {
                            return StockHistory.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('stock-history', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
