(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('payment', {
            parent: 'entity',
            url: '/payment?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.payment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/trade/payment/payments.html',
                    controller: 'PaymentController',
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
                    $translatePartialLoader.addPart('payment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('payment-detail', {
            parent: 'entity',
            url: '/payment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.payment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/trade/payment/payment-detail.html',
                    controller: 'PaymentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('payment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Payment', function($stateParams, Payment) {
                    return Payment.get({id : $stateParams.id});
                }]
            }
        })
        .state('payment.new', {
            parent: 'payment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/payment/payment-dialog.html',
                    controller: 'PaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                amount: null,
                                receiptNumber: null,
                                paidBy: null,
                                remarks: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('payment', null, { reload: true });
                }, function() {
                    $state.go('payment');
                });
            }]
        })
        .state('payment.edit', {
            parent: 'payment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/payment/payment-dialog.html',
                    controller: 'PaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Payment', function(Payment) {
                            return Payment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment.delete', {
            parent: 'payment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/trade/payment/payment-delete-dialog.html',
                    controller: 'PaymentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Payment', function(Payment) {
                            return Payment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
