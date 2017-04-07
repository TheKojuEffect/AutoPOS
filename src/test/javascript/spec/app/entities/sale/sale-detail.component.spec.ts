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
import { SaleDetailComponent } from '../../../../../../main/webapp/app/sales/sale/sale-detail.component';
import { SaleService } from '../../../../../../main/webapp/app/sales/sale/sale.service';
import { Sale } from '../../../../../../main/webapp/app/sales/sale/sale.model';

describe('Component Tests', () => {

    describe('Sale Management Detail Component', () => {
        let comp: SaleDetailComponent;
        let fixture: ComponentFixture<SaleDetailComponent>;
        let service: SaleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [SaleDetailComponent],
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
                    SaleService,
                    EventManager
                ]
            }).overrideComponent(SaleDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Sale(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sale).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
