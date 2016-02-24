'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bill', {
                parent: 'entity',
                url: '/bills',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.bill.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bill/bills.html',
                        controller: 'BillController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bill');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bill.detail', {
                parent: 'entity',
                url: '/bill/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.bill.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bill/bill-detail.html',
                        controller: 'BillDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bill');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Bill', function($stateParams, Bill) {
                        return Bill.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bill.new', {
                parent: 'bill',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    subTotal: null,
                                    discount: null,
                                    taxableAmount: null,
                                    tax: null,
                                    grandTotal: null,
                                    client: null,
                                    remarks: null,
                                    issuedBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('bill');
                    })
                }]
            })
            .state('bill.edit', {
                parent: 'bill',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bill', function(Bill) {
                                return Bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bill.delete', {
                parent: 'bill',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-delete-dialog.html',
                        controller: 'BillDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Bill', function(Bill) {
                                return Bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
