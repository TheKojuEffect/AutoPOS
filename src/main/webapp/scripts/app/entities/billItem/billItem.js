'use strict';

angular.module('autoposApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('billItem', {
                parent: 'entity',
                url: '/billItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.billItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/billItem/billItems.html',
                        controller: 'BillItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('billItem');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('billItem.detail', {
                parent: 'entity',
                url: '/billItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.billItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/billItem/billItem-detail.html',
                        controller: 'BillItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('billItem');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BillItem', function($stateParams, BillItem) {
                        return BillItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('billItem.new', {
                parent: 'billItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/billItem/billItem-dialog.html',
                        controller: 'BillItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    quantity: null,
                                    rate: null,
                                    amount: null,
                                    date: null,
                                    remarks: null,
                                    issuedBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('billItem', null, { reload: true });
                    }, function() {
                        $state.go('billItem');
                    })
                }]
            })
            .state('billItem.edit', {
                parent: 'billItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/billItem/billItem-dialog.html',
                        controller: 'BillItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BillItem', function(BillItem) {
                                return BillItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('billItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('billItem.delete', {
                parent: 'billItem',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/billItem/billItem-delete-dialog.html',
                        controller: 'BillItemDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BillItem', function(BillItem) {
                                return BillItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('billItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
