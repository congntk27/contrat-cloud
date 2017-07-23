(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('DiscussionDeleteController',DiscussionDeleteController);

    DiscussionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Discussion'];

    function DiscussionDeleteController($uibModalInstance, entity, Discussion) {
        var vm = this;

        vm.discussion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Discussion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
