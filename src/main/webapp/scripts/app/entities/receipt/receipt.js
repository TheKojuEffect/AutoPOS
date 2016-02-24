'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('receipt', {
                parent: 'entity',
                url: '/receipts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.receipt.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/receipt/receipts.html',
                        controller: 'ReceiptController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('receipt');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('receipt.detail', {
                parent: 'entity',
                url: '/receipt/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.receipt.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/receipt/receipt-detail.html',
                        controller: 'ReceiptDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('receipt');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Receipt', function($stateParams, Receipt) {
                        return Receipt.get({id : $stateParams.id});
                    }]
                }
            })
            .state('receipt.new', {
                parent: 'receipt',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/receipt/receipt-dialog.html',
                        controller: 'ReceiptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    amount: null,
                                    receivedBy: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('receipt', null, { reload: true });
                    }, function() {
                        $state.go('receipt');
                    })
                }]
            })
            .state('receipt.edit', {
                parent: 'receipt',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/receipt/receipt-dialog.html',
                        controller: 'ReceiptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Receipt', function(Receipt) {
                                return Receipt.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('receipt', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('receipt.delete', {
                parent: 'receipt',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/receipt/receipt-delete-dialog.html',
                        controller: 'ReceiptDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Receipt', function(Receipt) {
                                return Receipt.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('receipt', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
