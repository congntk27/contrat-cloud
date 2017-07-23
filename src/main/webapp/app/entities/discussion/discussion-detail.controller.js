(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('DiscussionDetailController', DiscussionDetailController);

    DiscussionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Discussion'];

    function DiscussionDetailController($scope, $rootScope, $stateParams, previousState, entity, Discussion) {
        var vm = this;

        vm.discussion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('contractcloudApp:discussionUpdate', function(event, result) {
            vm.discussion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
