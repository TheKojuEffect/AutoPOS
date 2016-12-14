import { downgradeComponent } from '@angular/upgrade/static';
import { ItemSearchComponent } from './catalog/item/item-search.component';
import * as angular from 'angular';

angular
    .module('autopos', [
        'ngStorage',
        'tmh.dynamicLocale',
        'pascalprecht.translate',
        'ngResource',
        'ngCookies',
        'ngAria',
        'ngCacheBuster',
        'ngFileUpload',
        'ui.bootstrap',
        'ui.bootstrap.datetimepicker',
        'ui.router',
        'infinite-scroll',
        'angularMoment'
    ])
    .directive('aposItemSearch', downgradeComponent({component: ItemSearchComponent}) as angular.IDirectiveFactory)
    .run(run);

run.$inject = ['stateHandler', 'translationHandler', 'moment'];

function run(stateHandler, translationHandler, moment) {
    Date.prototype.toJSON = function () {
        return moment(this).format('YYYY-MM-DDTHH:mm:ss');
    };

    stateHandler.initialize();
    translationHandler.initialize();
}
