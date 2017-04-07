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
import { BrandDetailComponent } from '../../../../../../main/webapp/app/catalog/brand/brand-detail.component';
import { BrandService } from '../../../../../../main/webapp/app/catalog/brand/brand.service';
import { Brand } from '../../../../../../main/webapp/app/catalog/brand/brand.model';

describe('Component Tests', () => {

    describe('Brand Management Detail Component', () => {
        let comp: BrandDetailComponent;
        let fixture: ComponentFixture<BrandDetailComponent>;
        let service: BrandService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoPosTestModule],
                declarations: [BrandDetailComponent],
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
                    BrandService,
                    EventManager
                ]
            }).overrideComponent(BrandDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Brand(10)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.brand).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
