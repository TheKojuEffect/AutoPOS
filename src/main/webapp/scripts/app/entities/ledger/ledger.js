'use strict';

angular.module('autoposApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ledger', {
                parent: 'entity',
                url: '/ledgers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.ledger.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ledger/ledgers.html',
                        controller: 'LedgerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ledger');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ledger.detail', {
                parent: 'entity',
                url: '/ledger/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.ledger.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ledger/ledger-detail.html',
                        controller: 'LedgerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ledger');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Ledger', function($stateParams, Ledger) {
                        return Ledger.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ledger.new', {
                parent: 'ledger',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ledger/ledger-dialog.html',
                        controller: 'LedgerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    balance: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ledger', null, { reload: true });
                    }, function() {
                        $state.go('ledger');
                    })
                }]
            })
            .state('ledger.edit', {
                parent: 'ledger',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ledger/ledger-dialog.html',
                        controller: 'LedgerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ledger', function(Ledger) {
                                return Ledger.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ledger', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ledger.delete', {
                parent: 'ledger',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ledger/ledger-delete-dialog.html',
                        controller: 'LedgerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Ledger', function(Ledger) {
                                return Ledger.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ledger', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
