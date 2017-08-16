/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ManagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TimeSheetDetailComponent } from '../../../../../../main/webapp/app/entities/time-sheet/time-sheet-detail.component';
import { TimeSheetService } from '../../../../../../main/webapp/app/entities/time-sheet/time-sheet.service';
import { TimeSheet } from '../../../../../../main/webapp/app/entities/time-sheet/time-sheet.model';

describe('Component Tests', () => {

    describe('TimeSheet Management Detail Component', () => {
        let comp: TimeSheetDetailComponent;
        let fixture: ComponentFixture<TimeSheetDetailComponent>;
        let service: TimeSheetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManagerTestModule],
                declarations: [TimeSheetDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TimeSheetService,
                    JhiEventManager
                ]
            }).overrideTemplate(TimeSheetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimeSheetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeSheetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TimeSheet(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.timeSheet).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
