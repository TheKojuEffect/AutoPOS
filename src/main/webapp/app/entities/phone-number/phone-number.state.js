(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('phone-number', {
            parent: 'entity',
            url: '/phone-number?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.phoneNumber.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/phone-number/phone-numbers.html',
                    controller: 'PhoneNumberController',
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
                    $translatePartialLoader.addPart('phoneNumber');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('phone-number-detail', {
            parent: 'entity',
            url: '/phone-number/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.phoneNumber.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/phone-number/phone-number-detail.html',
                    controller: 'PhoneNumberDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('phoneNumber');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PhoneNumber', function($stateParams, PhoneNumber) {
                    return PhoneNumber.get({id : $stateParams.id});
                }]
            }
        })
        .state('phone-number.new', {
            parent: 'phone-number',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/phone-number/phone-number-dialog.html',
                    controller: 'PhoneNumberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('phone-number', null, { reload: true });
                }, function() {
                    $state.go('phone-number');
                });
            }]
        })
        .state('phone-number.edit', {
            parent: 'phone-number',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/phone-number/phone-number-dialog.html',
                    controller: 'PhoneNumberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PhoneNumber', function(PhoneNumber) {
                            return PhoneNumber.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('phone-number', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('phone-number.delete', {
            parent: 'phone-number',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/phone-number/phone-number-delete-dialog.html',
                    controller: 'PhoneNumberDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PhoneNumber', function(PhoneNumber) {
                            return PhoneNumber.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('phone-number', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
