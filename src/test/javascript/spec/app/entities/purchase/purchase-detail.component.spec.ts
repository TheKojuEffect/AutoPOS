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
import { PurchaseDetailComponent } from '../../../../../../main/webapp/app/entities/purchase/purchase-detail.component';
import { PurchaseService } from '../../../../../../main/webapp/app/entities/purchase/purchase.service';
import { Purchase } from '../../../../../../main/webapp/app/entities/purchase/purchase.model';

describe('Component Tests', () => {

    describe('Purchase Management Detail Component', () => {
        let comp: PurchaseDetailComponent;
        let fixture: ComponentFixture<PurchaseDetailComponent>;
        let service: PurchaseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [PurchaseDetailComponent],
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

                    PurchaseService,
                    EventManager
                ]
            }).overrideComponent(PurchaseDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PurchaseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Purchase(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.purchase).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
