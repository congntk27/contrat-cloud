(function() {
    'use strict';
    angular
        .module('contractcloudApp')
        .factory('Signature', Signature);

    Signature.$inject = ['$resource', 'DateUtils'];

    function Signature ($resource, DateUtils) {
        var resourceUrl =  'api/signatures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.signdate = DateUtils.convertDateTimeFromServer(data.signdate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
