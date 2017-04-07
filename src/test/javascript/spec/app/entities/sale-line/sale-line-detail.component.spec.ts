import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DataUtils, DateUtils, EventManager } from 'ng-jhipster';
import { AutoPosTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SaleLineDetailComponent } from '../../../../../../main/webapp/app/sales/sale-line/sale-line-detail.component';
import { SaleLineService } from '../../../../../../main/webapp/app/sales/sale-line/sale-line.service';
import { SaleLine } from '../../../../../../main/webapp/app/sales/sale-line/sale-line.model';

describe('Component Tests', () => {

    describe('SaleLine Management Detail Component', () => {
        let comp: SaleLineDetailComponent;
        let fixture: ComponentFixture<SaleLineDetailComponent>;
        let service: SaleLineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [SaleLineDetailComponent],
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
                    SaleLineService,
                    EventManager
                ]
            }).overrideComponent(SaleLineDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleLineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleLineService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SaleLine(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.saleLine).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
