'use strict';

angular.module('autoposApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('priceHistory', {
                parent: 'entity',
                url: '/priceHistorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.priceHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/priceHistory/priceHistorys.html',
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
                parent: 'entity',
                url: '/priceHistory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.priceHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/priceHistory/priceHistory-detail.html',
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
                        templateUrl: 'scripts/app/entities/priceHistory/priceHistory-dialog.html',
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
                        templateUrl: 'scripts/app/entities/priceHistory/priceHistory-dialog.html',
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
                        templateUrl: 'scripts/app/entities/priceHistory/priceHistory-delete-dialog.html',
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
