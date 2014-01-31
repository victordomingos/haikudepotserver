/*
 * Copyright 2013-2014, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

angular.module('haikudepotserver').controller(
    'ViewUserController',
    [
        '$scope','$log','$location','$routeParams',
        'jsonRpc','constants','errorHandling',
        function(
            $scope,$log,$location,$routeParams,
            jsonRpc,constants,errorHandling) {

            $scope.breadcrumbItems = undefined;
            $scope.user = undefined;

            $scope.shouldSpin = function() {
                return undefined == $scope.user;
            }

            refreshUser();

            function refreshBreadcrumbItems() {
                $scope.breadcrumbItems = [{
                    title : $scope.user.nickname,
                    path : $location.path()
                }];
            }

            function refreshUser() {
                jsonRpc.call(
                        constants.ENDPOINT_API_V1_USER,
                        "getUser",
                        [{
                            nickname : $routeParams.nickname
                        }]
                    ).then(
                    function(result) {
                        $scope.user = result;
                        refreshBreadcrumbItems();
                        $log.info('fetched user; '+result.nickname);
                    },
                    function(err) {
                        errorHandling.handleJsonRpcError(err);
                    }
                );
            };

            $scope.goChangePassword = function() {
                $location.path('/changepassword/' + $scope.user.nickname);
            }

        }
    ]
);