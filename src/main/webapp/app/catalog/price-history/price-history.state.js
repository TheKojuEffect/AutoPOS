(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('price-history', {
            parent: 'entity',
            url: '/price-history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.priceHistory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-history/price-histories.html',
                    controller: 'PriceHistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('priceHistory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('price-history-detail', {
            parent: 'entity',
            url: '/price-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.priceHistory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-history/price-history-detail.html',
                    controller: 'PriceHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('priceHistory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PriceHistory', function($stateParams, PriceHistory) {
                    return PriceHistory.get({id : $stateParams.id});
                }]
            }
        })
        .state('price-history.new', {
            parent: 'price-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-history/price-history-dialog.html',
                    controller: 'PriceHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                markedPrice: null,
                                remarks: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('price-history', null, { reload: true });
                }, function() {
                    $state.go('price-history');
                });
            }]
        })
        .state('price-history.edit', {
            parent: 'price-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-history/price-history-dialog.html',
                    controller: 'PriceHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PriceHistory', function(PriceHistory) {
                            return PriceHistory.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-history', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('price-history.delete', {
            parent: 'price-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-history/price-history-delete-dialog.html',
                    controller: 'PriceHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PriceHistory', function(PriceHistory) {
                            return PriceHistory.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-history', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
