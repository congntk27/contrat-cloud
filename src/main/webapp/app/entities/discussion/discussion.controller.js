(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('DiscussionController', DiscussionController);

    DiscussionController.$inject = ['Discussion'];

    function DiscussionController(Discussion) {

        var vm = this;

        vm.discussions = [];

        loadAll();

        function loadAll() {
            Discussion.query(function(result) {
                vm.discussions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
