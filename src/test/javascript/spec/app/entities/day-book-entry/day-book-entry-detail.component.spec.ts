import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { BaseRequestOptions, Http } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DataUtils, DateUtils, JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DayBookEntryDetailComponent } from '../../../../../../main/webapp/app/accounting/day-book-entry/day-book-entry-detail.component';
import { DayBookEntryService } from '../../../../../../main/webapp/app/accounting/day-book-entry/day-book-entry.service';
import { DayBookEntry } from '../../../../../../main/webapp/app/accounting/day-book-entry/day-book-entry.model';

describe('Component Tests', () => {

    describe('DayBookEntry Management Detail Component', () => {
        let comp: DayBookEntryDetailComponent;
        let fixture: ComponentFixture<DayBookEntryDetailComponent>;
        let service: DayBookEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [DayBookEntryDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    DayBookEntryService
                ]
            }).overrideComponent(DayBookEntryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DayBookEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DayBookEntryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DayBookEntry(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dayBookEntry).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
