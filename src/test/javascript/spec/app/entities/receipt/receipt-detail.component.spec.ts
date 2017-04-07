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
import { ReceiptDetailComponent } from '../../../../../../main/webapp/app/transaction/receipt/receipt-detail.component';
import { ReceiptService } from '../../../../../../main/webapp/app/transaction/receipt/receipt.service';
import { Receipt } from '../../../../../../main/webapp/app/transaction/receipt/receipt.model';

describe('Component Tests', () => {

    describe('Receipt Management Detail Component', () => {
        let comp: ReceiptDetailComponent;
        let fixture: ComponentFixture<ReceiptDetailComponent>;
        let service: ReceiptService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [ReceiptDetailComponent],
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
                    ReceiptService,
                    EventManager
                ]
            }).overrideComponent(ReceiptDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReceiptDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceiptService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Receipt(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.receipt).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
