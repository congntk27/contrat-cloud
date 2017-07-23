(function() {
    'use strict';
    angular
        .module('contractcloudApp')
        .factory('Discussion', Discussion);

    Discussion.$inject = ['$resource'];

    function Discussion ($resource) {
        var resourceUrl =  'api/discussions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
