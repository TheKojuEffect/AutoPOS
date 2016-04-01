(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bill', {
            parent: 'trade',
            url: '/bill?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.bill.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/trade/bill/bills.html',
                    controller: 'BillController',
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
                    $translatePartialLoader.addPart('bill');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bill-detail', {
            parent: 'trade',
            url: '/bill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.bill.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/trade/bill/bill-detail.html',
                    controller: 'BillDetailController',
                    controllerAs: 'vm'
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
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/bill/bill-dialog.html',
                    controller: 'BillDialogController',
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
                    $state.go('bill', null, { reload: true });
                }, function() {
                    $state.go('bill');
                });
            }]
        })
        .state('bill.edit', {
            parent: 'bill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/bill/bill-dialog.html',
                    controller: 'BillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bill', function(Bill) {
                            return Bill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('bill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bill.delete', {
            parent: 'bill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/bill/bill-delete-dialog.html',
                    controller: 'BillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Bill', function(Bill) {
                            return Bill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('bill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
