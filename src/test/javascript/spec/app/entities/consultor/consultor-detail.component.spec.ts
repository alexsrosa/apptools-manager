/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ManagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConsultorDetailComponent } from '../../../../../../main/webapp/app/entities/consultor/consultor-detail.component';
import { ConsultorService } from '../../../../../../main/webapp/app/entities/consultor/consultor.service';
import { Consultor } from '../../../../../../main/webapp/app/entities/consultor/consultor.model';

describe('Component Tests', () => {

    describe('Consultor Management Detail Component', () => {
        let comp: ConsultorDetailComponent;
        let fixture: ComponentFixture<ConsultorDetailComponent>;
        let service: ConsultorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManagerTestModule],
                declarations: [ConsultorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConsultorService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConsultorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Consultor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.consultor).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
