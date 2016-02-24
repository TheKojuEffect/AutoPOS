'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stock', {
                parent: 'site',
                url: '/stocks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.stock.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/stock/stock/stocks.html',
                        controller: 'StockController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stock');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stock.detail', {
                parent: 'entity',
                url: '/stock/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.stock.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/stock/stock/stock-detail.html',
                        controller: 'StockDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stock');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Stock', function($stateParams, Stock) {
                        return Stock.get({id : $stateParams.id});
                    }]
                }
            })
            .state('stock.new', {
                parent: 'stock',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/stock/stock/stock-dialog.html',
                        controller: 'StockDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    quantity: null,
                                    location: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('stock', null, { reload: true });
                    }, function() {
                        $state.go('stock');
                    })
                }]
            })
            .state('stock.edit', {
                parent: 'stock',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/stock/stock/stock-dialog.html',
                        controller: 'StockDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Stock', function(Stock) {
                                return Stock.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stock', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('stock.delete', {
                parent: 'stock',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/stock/stock/stock-delete-dialog.html',
                        controller: 'StockDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Stock', function(Stock) {
                                return Stock.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stock', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
