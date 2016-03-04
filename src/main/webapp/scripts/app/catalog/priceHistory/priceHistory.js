'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('priceHistory', {
                parent: 'catalog',
                url: '/priceHistorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.priceHistory.home.title'
                },
                views: {
                    'content-tab': {
                        templateUrl: 'scripts/app/catalog/priceHistory/priceHistorys.html',
                        controller: 'PriceHistoryController'
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
            .state('priceHistory.detail', {
                parent: 'catalog',
                url: '/priceHistory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.priceHistory.detail.title'
                },
                views: {
                    'content-tab': {
                        templateUrl: 'scripts/app/catalog/priceHistory/priceHistory-detail.html',
                        controller: 'PriceHistoryDetailController'
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
            .state('priceHistory.new', {
                parent: 'priceHistory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/catalog/priceHistory/priceHistory-dialog.html',
                        controller: 'PriceHistoryDialogController',
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
                    }).result.then(function(result) {
                        $state.go('priceHistory', null, { reload: true });
                    }, function() {
                        $state.go('priceHistory');
                    })
                }]
            })
            .state('priceHistory.edit', {
                parent: 'priceHistory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/catalog/priceHistory/priceHistory-dialog.html',
                        controller: 'PriceHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PriceHistory', function(PriceHistory) {
                                return PriceHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('priceHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('priceHistory.delete', {
                parent: 'priceHistory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/catalog/priceHistory/priceHistory-delete-dialog.html',
                        controller: 'PriceHistoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PriceHistory', function(PriceHistory) {
                                return PriceHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('priceHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
