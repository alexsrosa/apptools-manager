/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ManagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ParametroDetailComponent } from '../../../../../../main/webapp/app/entities/parametro/parametro-detail.component';
import { ParametroService } from '../../../../../../main/webapp/app/entities/parametro/parametro.service';
import { Parametro } from '../../../../../../main/webapp/app/entities/parametro/parametro.model';

describe('Component Tests', () => {

    describe('Parametro Management Detail Component', () => {
        let comp: ParametroDetailComponent;
        let fixture: ComponentFixture<ParametroDetailComponent>;
        let service: ParametroService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManagerTestModule],
                declarations: [ParametroDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ParametroService,
                    JhiEventManager
                ]
            }).overrideTemplate(ParametroDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParametroDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametroService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Parametro(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.parametro).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
