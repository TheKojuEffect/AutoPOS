'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dayBookEntry', {
                parent: 'entity',
                url: '/dayBookEntrys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.dayBookEntry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dayBookEntry/dayBookEntrys.html',
                        controller: 'DayBookEntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dayBookEntry');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dayBookEntry.detail', {
                parent: 'entity',
                url: '/dayBookEntry/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.dayBookEntry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dayBookEntry/dayBookEntry-detail.html',
                        controller: 'DayBookEntryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dayBookEntry');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DayBookEntry', function($stateParams, DayBookEntry) {
                        return DayBookEntry.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dayBookEntry.new', {
                parent: 'dayBookEntry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dayBookEntry/dayBookEntry-dialog.html',
                        controller: 'DayBookEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    incomingAmount: null,
                                    outgoingAmount: null,
                                    miscExpenses: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dayBookEntry', null, { reload: true });
                    }, function() {
                        $state.go('dayBookEntry');
                    })
                }]
            })
            .state('dayBookEntry.edit', {
                parent: 'dayBookEntry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dayBookEntry/dayBookEntry-dialog.html',
                        controller: 'DayBookEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DayBookEntry', function(DayBookEntry) {
                                return DayBookEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dayBookEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dayBookEntry.delete', {
                parent: 'dayBookEntry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dayBookEntry/dayBookEntry-delete-dialog.html',
                        controller: 'DayBookEntryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DayBookEntry', function(DayBookEntry) {
                                return DayBookEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dayBookEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
