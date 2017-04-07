import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DataUtils, DateUtils, EventManager } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VehicleDetailComponent } from '../../../../../../main/webapp/app/party/vehicle/vehicle-detail.component';
import { VehicleService } from '../../../../../../main/webapp/app/party/vehicle/vehicle.service';
import { Vehicle } from '../../../../../../main/webapp/app/party/vehicle/vehicle.model';
import { AutoPosTestModule } from '../../../test.module';

describe('Component Tests', () => {

    describe('Vehicle Management Detail Component', () => {
        let comp: VehicleDetailComponent;
        let fixture: ComponentFixture<VehicleDetailComponent>;
        let service: VehicleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [VehicleDetailComponent],
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
                    VehicleService,
                    EventManager
                ]
            }).overrideComponent(VehicleDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VehicleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Vehicle(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.vehicle).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
