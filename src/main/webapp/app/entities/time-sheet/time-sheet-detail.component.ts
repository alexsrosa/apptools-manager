import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TimeSheet } from './time-sheet.model';
import { TimeSheetService } from './time-sheet.service';

@Component({
    selector: 'jhi-time-sheet-detail',
    templateUrl: './time-sheet-detail.component.html'
})
export class TimeSheetDetailComponent implements OnInit, OnDestroy {

    timeSheet: TimeSheet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private timeSheetService: TimeSheetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTimeSheets();
    }

    load(id) {
        this.timeSheetService.find(id).subscribe((timeSheet) => {
            this.timeSheet = timeSheet;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTimeSheets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'timeSheetListModification',
            (response) => this.load(this.timeSheet.id)
        );
    }
}
