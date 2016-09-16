(function () {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('day-book-entry', {
                parent: 'accounting',
                url: '/daybook?page&sort&search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.dayBookEntry.home.title'
                },
                views: {
                    'content-tab': {
                        templateUrl: 'app/accounting/day-book-entry/day-book-entries.html',
                        controller: 'DayBookEntryController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'date, desc',
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
                        $translatePartialLoader.addPart('dayBookEntry');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('day-book-entry.new', {
                parent: 'day-book-entry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/accounting/day-book-entry/day-book-entry-dialog.html',
                        controller: 'DayBookEntryDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: new Date(),
                                    incomingAmount: null,
                                    outgoingAmount: null,
                                    miscExpenses: null,
                                    id: null
                                };
                            },
                            pageHeader: function () {
                                return 'Add a Day Book Entry';
                            }
                        }
                    }).result.then(function () {
                        $state.go('day-book-entry', null, {reload: true});
                    }, function () {
                        $state.go('day-book-entry');
                    });
                }]
            })
            .state('day-book-entry-detail', {
                parent: 'day-book-entry',
                url: '/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.dayBookEntry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/accounting/day-book-entry/day-book-entry-detail.html',
                        controller: 'DayBookEntryDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dayBookEntry');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DayBookEntry', function ($stateParams, DayBookEntry) {
                        return DayBookEntry.get({id: $stateParams.id});
                    }]
                }
            })

            .state('day-book-entry.edit', {
                parent: 'day-book-entry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/accounting/day-book-entry/day-book-entry-dialog.html',
                        controller: 'DayBookEntryDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DayBookEntry', function (DayBookEntry) {
                                return DayBookEntry.get({id: $stateParams.id});
                            }],
                            pageHeader: function () {
                                return 'Edit a Day Book Entry';
                            }
                        }
                    }).result.then(function () {
                        $state.go('day-book-entry', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('day-book-entry.delete', {
                parent: 'day-book-entry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/accounting/day-book-entry/day-book-entry-delete-dialog.html',
                        controller: 'DayBookEntryDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['DayBookEntry', function (DayBookEntry) {
                                return DayBookEntry.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function () {
                        $state.go('day-book-entry', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
