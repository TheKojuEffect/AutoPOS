(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ledger', {
            parent: 'trade',
            url: '/ledger?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.ledger.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/trade/ledger/ledgers.html',
                    controller: 'LedgerController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ledger');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ledger-detail', {
            parent: 'trade',
            url: '/ledger/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.ledger.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/trade/ledger/ledger-detail.html',
                    controller: 'LedgerDetailController',
                    controllerAs: 'vm'
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
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/ledger/ledger-dialog.html',
                    controller: 'LedgerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
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
                }).result.then(function() {
                    $state.go('ledger', null, { reload: true });
                }, function() {
                    $state.go('ledger');
                });
            }]
        })
        .state('ledger.edit', {
            parent: 'ledger',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/ledger/ledger-dialog.html',
                    controller: 'LedgerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ledger', function(Ledger) {
                            return Ledger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('ledger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ledger.delete', {
            parent: 'ledger',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/ledger/ledger-delete-dialog.html',
                    controller: 'LedgerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ledger', function(Ledger) {
                            return Ledger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('ledger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
