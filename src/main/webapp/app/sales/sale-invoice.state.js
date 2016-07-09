(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sale-invoice', {
            parent: 'sale',
            url: '/sale-invoice?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.sale-invoice.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/sale/sale-invoice/sale-invoices.html',
                    controller: 'SaleInvoiceController',
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
                    $translatePartialLoader.addPart('sale-invoice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sale-invoice-detail', {
            parent: 'sale',
            url: '/sale-invoice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.sale-invoice.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/sale/sale-invoice/sale-invoice-detail.html',
                    controller: 'SaleInvoiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sale-invoice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SaleInvoice', function($stateParams, SaleInvoice) {
                    return SaleInvoice.get({id : $stateParams.id});
                }]
            }
        })
        .state('sale-invoice.new', {
            parent: 'sale-invoice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sale/sale-invoice/sale-invoice-dialog.html',
                    controller: 'SaleInvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
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
                }).result.then(function() {
                    $state.go('sale-invoice', null, { reload: true });
                }, function() {
                    $state.go('sale-invoice');
                });
            }]
        })
        .state('sale-invoice.edit', {
            parent: 'sale-invoice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sale/sale-invoice/sale-invoice-dialog.html',
                    controller: 'SaleInvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SaleInvoice', function(SaleInvoice) {
                            return SaleInvoice.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sale-invoice', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sale-invoice.delete', {
            parent: 'sale-invoice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sale/sale-invoice/sale-invoice-delete-dialog.html',
                    controller: 'SaleInvoiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SaleInvoice', function(SaleInvoice) {
                            return SaleInvoice.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sale-invoice', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
