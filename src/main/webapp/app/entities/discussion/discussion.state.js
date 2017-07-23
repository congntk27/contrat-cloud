(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('discussion', {
            parent: 'entity',
            url: '/discussion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'contractcloudApp.discussion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discussion/discussions.html',
                    controller: 'DiscussionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discussion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('discussion-detail', {
            parent: 'discussion',
            url: '/discussion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'contractcloudApp.discussion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discussion/discussion-detail.html',
                    controller: 'DiscussionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discussion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Discussion', function($stateParams, Discussion) {
                    return Discussion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'discussion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('discussion-detail.edit', {
            parent: 'discussion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discussion/discussion-dialog.html',
                    controller: 'DiscussionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Discussion', function(Discussion) {
                            return Discussion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discussion.new', {
            parent: 'discussion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discussion/discussion-dialog.html',
                    controller: 'DiscussionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contractid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('discussion', null, { reload: 'discussion' });
                }, function() {
                    $state.go('discussion');
                });
            }]
        })
        .state('discussion.edit', {
            parent: 'discussion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discussion/discussion-dialog.html',
                    controller: 'DiscussionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Discussion', function(Discussion) {
                            return Discussion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discussion', null, { reload: 'discussion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discussion.delete', {
            parent: 'discussion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discussion/discussion-delete-dialog.html',
                    controller: 'DiscussionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Discussion', function(Discussion) {
                            return Discussion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discussion', null, { reload: 'discussion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
