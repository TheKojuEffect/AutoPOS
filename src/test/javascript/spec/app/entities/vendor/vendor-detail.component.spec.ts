import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DataUtils, DateUtils, EventManager } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VendorDetailComponent } from '../../../../../../main/webapp/app/party/vendor/vendor-detail.component';
import { VendorService } from '../../../../../../main/webapp/app/party/vendor/vendor.service';
import { Vendor } from '../../../../../../main/webapp/app/party/vendor/vendor.model';
import { AutoPosTestModule } from '../../../test.module';

describe('Component Tests', () => {

    describe('Vendor Management Detail Component', () => {
        let comp: VendorDetailComponent;
        let fixture: ComponentFixture<VendorDetailComponent>;
        let service: VendorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [VendorDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VendorService,
                    EventManager
                ]
            }).overrideComponent(VendorDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VendorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Vendor(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.vendor).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
