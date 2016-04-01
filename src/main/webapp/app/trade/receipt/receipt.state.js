(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('receipt', {
            parent: 'entity',
            url: '/receipt?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.receipt.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/trade/receipt/receipts.html',
                    controller: 'ReceiptController',
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
                    $translatePartialLoader.addPart('receipt');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('receipt-detail', {
            parent: 'entity',
            url: '/receipt/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.receipt.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/trade/receipt/receipt-detail.html',
                    controller: 'ReceiptDetailController',
                    controllerAs: 'vm'
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
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/receipt/receipt-dialog.html',
                    controller: 'ReceiptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
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
                }).result.then(function() {
                    $state.go('receipt', null, { reload: true });
                }, function() {
                    $state.go('receipt');
                });
            }]
        })
        .state('receipt.edit', {
            parent: 'receipt',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/receipt/receipt-dialog.html',
                    controller: 'ReceiptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Receipt', function(Receipt) {
                            return Receipt.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('receipt', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('receipt.delete', {
            parent: 'receipt',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/receipt/receipt-delete-dialog.html',
                    controller: 'ReceiptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Receipt', function(Receipt) {
                            return Receipt.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('receipt', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
