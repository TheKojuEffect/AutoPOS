import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { AutoPosTestModule } from '../../../test.module';
import { DataUtils, DateUtils, EventManager } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PriceHistoryDetailComponent } from '../../../../../../main/webapp/app/catalog/price-history/price-history-detail.component';
import { PriceHistoryService } from '../../../../../../main/webapp/app/catalog/price-history/price-history.service';
import { PriceHistory } from '../../../../../../main/webapp/app/catalog/price-history/price-history.model';

describe('Component Tests', () => {

    describe('PriceHistory Management Detail Component', () => {
        let comp: PriceHistoryDetailComponent;
        let fixture: ComponentFixture<PriceHistoryDetailComponent>;
        let service: PriceHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [PriceHistoryDetailComponent],
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

                    PriceHistoryService,
                    EventManager
                ]
            }).overrideComponent(PriceHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PriceHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PriceHistory(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.priceHistory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
