import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Consultores } from './consultores.model';
import { ConsultoresPopupService } from './consultores-popup.service';
import { ConsultoresService } from './consultores.service';

@Component({
    selector: 'jhi-consultores-dialog',
    templateUrl: './consultores-dialog.component.html'
})
export class ConsultoresDialogComponent implements OnInit {

    consultores: Consultores;
    isSaving: boolean;
    dataprimeiroregistroDp: any;
    dataultimoregistroDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private consultoresService: ConsultoresService,
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
        if (this.consultores.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultoresService.update(this.consultores));
        } else {
            this.subscribeToSaveResponse(
                this.consultoresService.create(this.consultores));
        }
    }

    private subscribeToSaveResponse(result: Observable<Consultores>) {
        result.subscribe((res: Consultores) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Consultores) {
        this.eventManager.broadcast({ name: 'consultoresListModification', content: 'OK'});
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
    selector: 'jhi-consultores-popup',
    template: ''
})
export class ConsultoresPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultoresPopupService: ConsultoresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultoresPopupService
                    .open(ConsultoresDialogComponent as Component, params['id']);
            } else {
                this.consultoresPopupService
                    .open(ConsultoresDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
