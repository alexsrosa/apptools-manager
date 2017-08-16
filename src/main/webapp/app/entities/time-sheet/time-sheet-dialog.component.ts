import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TimeSheet } from './time-sheet.model';
import { TimeSheetPopupService } from './time-sheet-popup.service';
import { TimeSheetService } from './time-sheet.service';

@Component({
    selector: 'jhi-time-sheet-dialog',
    templateUrl: './time-sheet-dialog.component.html'
})
export class TimeSheetDialogComponent implements OnInit {

    timeSheet: TimeSheet;
    isSaving: boolean;
    dataDp: any;
    datainclusaoDp: any;
    dataultimaatualizacaoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private timeSheetService: TimeSheetService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.timeSheet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.timeSheetService.update(this.timeSheet));
        } else {
            this.subscribeToSaveResponse(
                this.timeSheetService.create(this.timeSheet));
        }
    }

    private subscribeToSaveResponse(result: Observable<TimeSheet>) {
        result.subscribe((res: TimeSheet) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TimeSheet) {
        this.eventManager.broadcast({ name: 'timeSheetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-time-sheet-popup',
    template: ''
})
export class TimeSheetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timeSheetPopupService: TimeSheetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.timeSheetPopupService
                    .open(TimeSheetDialogComponent as Component, params['id']);
            } else {
                this.timeSheetPopupService
                    .open(TimeSheetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
