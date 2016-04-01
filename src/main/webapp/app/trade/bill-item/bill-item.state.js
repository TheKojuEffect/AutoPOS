(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bill-item', {
            parent: 'trade',
            url: '/bill-item?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.billItem.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/trade/bill-item/bill-items.html',
                    controller: 'BillItemController',
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
                    $translatePartialLoader.addPart('billItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bill-item-detail', {
            parent: 'trade',
            url: '/bill-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.billItem.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/trade/bill-item/bill-item-detail.html',
                    controller: 'BillItemDetailController',
                    controllerAs: 'vm'
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
        .state('bill-item.new', {
            parent: 'bill-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/bill-item/bill-item-dialog.html',
                    controller: 'BillItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
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
                }).result.then(function() {
                    $state.go('bill-item', null, { reload: true });
                }, function() {
                    $state.go('bill-item');
                });
            }]
        })
        .state('bill-item.edit', {
            parent: 'bill-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/bill-item/bill-item-dialog.html',
                    controller: 'BillItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BillItem', function(BillItem) {
                            return BillItem.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('bill-item', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bill-item.delete', {
            parent: 'bill-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/bill-item/bill-item-delete-dialog.html',
                    controller: 'BillItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BillItem', function(BillItem) {
                            return BillItem.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('bill-item', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
