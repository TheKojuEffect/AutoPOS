import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SaleLineDetailComponent } from '../../../../../../main/webapp/app/entities/sale-line/sale-line-detail.component';
import { SaleLineService } from '../../../../../../main/webapp/app/entities/sale-line/sale-line.service';
import { SaleLine } from '../../../../../../main/webapp/app/entities/sale-line/sale-line.model';

describe('Component Tests', () => {

    describe('SaleLine Management Detail Component', () => {
        let comp: SaleLineDetailComponent;
        let fixture: ComponentFixture<SaleLineDetailComponent>;
        let service: SaleLineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
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
                    SaleLineService
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
            expect(comp.saleLine).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
