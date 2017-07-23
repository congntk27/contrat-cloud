(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('DiscussionDialogController', DiscussionDialogController);

    DiscussionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Discussion'];

    function DiscussionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Discussion) {
        var vm = this;

        vm.discussion = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.discussion.id !== null) {
                Discussion.update(vm.discussion, onSaveSuccess, onSaveError);
            } else {
                Discussion.save(vm.discussion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('contractcloudApp:discussionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
