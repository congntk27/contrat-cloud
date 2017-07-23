(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('CommentController', CommentController);

    CommentController.$inject = ['Comment'];

    function CommentController(Comment) {

        var vm = this;

        vm.comments = [];

        loadAll();

        function loadAll() {
            Comment.query(function(result) {
                vm.comments = result;
                vm.searchQuery = null;
            });
        }
    }
})();
