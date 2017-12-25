'use strict';

describe('Controller Tests', function() {

    describe('PopVote Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPopVote, MockPopJudge, MockPopContribute, MockPopAppraise;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPopVote = jasmine.createSpy('MockPopVote');
            MockPopJudge = jasmine.createSpy('MockPopJudge');
            MockPopContribute = jasmine.createSpy('MockPopContribute');
            MockPopAppraise = jasmine.createSpy('MockPopAppraise');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PopVote': MockPopVote,
                'PopJudge': MockPopJudge,
                'PopContribute': MockPopContribute,
                'PopAppraise': MockPopAppraise
            };
            createController = function() {
                $injector.get('$controller')("PopVoteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pcmsApp:popVoteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
