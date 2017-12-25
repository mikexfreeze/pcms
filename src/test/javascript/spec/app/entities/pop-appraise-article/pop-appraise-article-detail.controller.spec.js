'use strict';

describe('Controller Tests', function() {

    describe('PopAppraiseArticle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPopAppraiseArticle, MockPopAppraise, MockPopContribute;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPopAppraiseArticle = jasmine.createSpy('MockPopAppraiseArticle');
            MockPopAppraise = jasmine.createSpy('MockPopAppraise');
            MockPopContribute = jasmine.createSpy('MockPopContribute');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PopAppraiseArticle': MockPopAppraiseArticle,
                'PopAppraise': MockPopAppraise,
                'PopContribute': MockPopContribute
            };
            createController = function() {
                $injector.get('$controller')("PopAppraiseArticleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pcmsApp:popAppraiseArticleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
