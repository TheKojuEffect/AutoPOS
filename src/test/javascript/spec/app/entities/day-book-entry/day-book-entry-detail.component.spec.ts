import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DataUtils, DateUtils, EventManager } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DayBookEntryDetailComponent } from '../../../../../../main/webapp/app/accounting/day-book-entry/day-book-entry-detail.component';
import { DayBookEntryService } from '../../../../../../main/webapp/app/accounting/day-book-entry/day-book-entry.service';
import { DayBookEntry } from '../../../../../../main/webapp/app/accounting/day-book-entry/day-book-entry.model';
import { AutoPosTestModule } from '../../../test.module';

describe('Component Tests', () => {

    describe('DayBookEntry Management Detail Component', () => {
        let comp: DayBookEntryDetailComponent;
        let fixture: ComponentFixture<DayBookEntryDetailComponent>;
        let service: DayBookEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
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

                    DayBookEntryService,
                    EventManager
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
