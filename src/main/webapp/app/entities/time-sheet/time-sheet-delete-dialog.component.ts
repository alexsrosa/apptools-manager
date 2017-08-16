import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TimeSheet } from './time-sheet.model';
import { TimeSheetPopupService } from './time-sheet-popup.service';
import { TimeSheetService } from './time-sheet.service';

@Component({
    selector: 'jhi-time-sheet-delete-dialog',
    templateUrl: './time-sheet-delete-dialog.component.html'
})
export class TimeSheetDeleteDialogComponent {

    timeSheet: TimeSheet;

    constructor(
        private timeSheetService: TimeSheetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timeSheetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'timeSheetListModification',
                content: 'Deleted an timeSheet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-sheet-delete-popup',
    template: ''
})
export class TimeSheetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timeSheetPopupService: TimeSheetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.timeSheetPopupService
                .open(TimeSheetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
