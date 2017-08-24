/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ManagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConsultoresDetailComponent } from '../../../../../../main/webapp/app/entities/consultores/consultores-detail.component';
import { ConsultoresService } from '../../../../../../main/webapp/app/entities/consultores/consultores.service';
import { Consultores } from '../../../../../../main/webapp/app/entities/consultores/consultores.model';

describe('Component Tests', () => {

    describe('Consultores Management Detail Component', () => {
        let comp: ConsultoresDetailComponent;
        let fixture: ComponentFixture<ConsultoresDetailComponent>;
        let service: ConsultoresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManagerTestModule],
                declarations: [ConsultoresDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConsultoresService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConsultoresDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultoresDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultoresService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Consultores(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.consultores).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
