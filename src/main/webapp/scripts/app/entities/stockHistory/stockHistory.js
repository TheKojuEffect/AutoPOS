'use strict';

angular.module('autoposApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stockHistory', {
                parent: 'entity',
                url: '/stockHistorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.stockHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockHistory/stockHistorys.html',
                        controller: 'StockHistoryController'
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
            .state('stockHistory.detail', {
                parent: 'entity',
                url: '/stockHistory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.stockHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stockHistory/stockHistory-detail.html',
                        controller: 'StockHistoryDetailController'
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
            .state('stockHistory.new', {
                parent: 'stockHistory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/stockHistory/stockHistory-dialog.html',
                        controller: 'StockHistoryDialogController',
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
                    }).result.then(function(result) {
                        $state.go('stockHistory', null, { reload: true });
                    }, function() {
                        $state.go('stockHistory');
                    })
                }]
            })
            .state('stockHistory.edit', {
                parent: 'stockHistory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/stockHistory/stockHistory-dialog.html',
                        controller: 'StockHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StockHistory', function(StockHistory) {
                                return StockHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stockHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('stockHistory.delete', {
                parent: 'stockHistory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/stockHistory/stockHistory-delete-dialog.html',
                        controller: 'StockHistoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['StockHistory', function(StockHistory) {
                                return StockHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stockHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
