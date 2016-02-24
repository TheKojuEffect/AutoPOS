'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('phoneNumber', {
                parent: 'entity',
                url: '/phoneNumbers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.phoneNumber.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/phoneNumber/phoneNumbers.html',
                        controller: 'PhoneNumberController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('phoneNumber');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('phoneNumber.detail', {
                parent: 'entity',
                url: '/phoneNumber/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.phoneNumber.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/phoneNumber/phoneNumber-detail.html',
                        controller: 'PhoneNumberDetailController'
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
            .state('phoneNumber.new', {
                parent: 'phoneNumber',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/phoneNumber/phoneNumber-dialog.html',
                        controller: 'PhoneNumberDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    number: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('phoneNumber', null, { reload: true });
                    }, function() {
                        $state.go('phoneNumber');
                    })
                }]
            })
            .state('phoneNumber.edit', {
                parent: 'phoneNumber',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/phoneNumber/phoneNumber-dialog.html',
                        controller: 'PhoneNumberDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PhoneNumber', function(PhoneNumber) {
                                return PhoneNumber.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('phoneNumber', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('phoneNumber.delete', {
                parent: 'phoneNumber',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/phoneNumber/phoneNumber-delete-dialog.html',
                        controller: 'PhoneNumberDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PhoneNumber', function(PhoneNumber) {
                                return PhoneNumber.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('phoneNumber', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
