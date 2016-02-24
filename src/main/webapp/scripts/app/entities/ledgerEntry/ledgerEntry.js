'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ledgerEntry', {
                parent: 'entity',
                url: '/ledgerEntrys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.ledgerEntry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ledgerEntry/ledgerEntrys.html',
                        controller: 'LedgerEntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ledgerEntry');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ledgerEntry.detail', {
                parent: 'entity',
                url: '/ledgerEntry/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.ledgerEntry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ledgerEntry/ledgerEntry-detail.html',
                        controller: 'LedgerEntryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ledgerEntry');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LedgerEntry', function($stateParams, LedgerEntry) {
                        return LedgerEntry.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ledgerEntry.new', {
                parent: 'ledgerEntry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ledgerEntry/ledgerEntry-dialog.html',
                        controller: 'LedgerEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    particular: null,
                                    folio: null,
                                    debit: null,
                                    credit: null,
                                    balance: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ledgerEntry', null, { reload: true });
                    }, function() {
                        $state.go('ledgerEntry');
                    })
                }]
            })
            .state('ledgerEntry.edit', {
                parent: 'ledgerEntry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ledgerEntry/ledgerEntry-dialog.html',
                        controller: 'LedgerEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LedgerEntry', function(LedgerEntry) {
                                return LedgerEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ledgerEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ledgerEntry.delete', {
                parent: 'ledgerEntry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ledgerEntry/ledgerEntry-delete-dialog.html',
                        controller: 'LedgerEntryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LedgerEntry', function(LedgerEntry) {
                                return LedgerEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ledgerEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
