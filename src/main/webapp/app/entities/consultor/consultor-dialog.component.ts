import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Consultor } from './consultor.model';
import { ConsultorPopupService } from './consultor-popup.service';
import { ConsultorService } from './consultor.service';

@Component({
    selector: 'jhi-consultor-dialog',
    templateUrl: './consultor-dialog.component.html'
})
export class ConsultorDialogComponent implements OnInit {

    consultor: Consultor;
    isSaving: boolean;
    dataprimeiroregistroDp: any;
    dataultimoregistroDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private consultorService: ConsultorService,
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
        if (this.consultor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultorService.update(this.consultor));
        } else {
            this.subscribeToSaveResponse(
                this.consultorService.create(this.consultor));
        }
    }

    private subscribeToSaveResponse(result: Observable<Consultor>) {
        result.subscribe((res: Consultor) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Consultor) {
        this.eventManager.broadcast({ name: 'consultorListModification', content: 'OK'});
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
    selector: 'jhi-consultor-popup',
    template: ''
})
export class ConsultorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultorPopupService: ConsultorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultorPopupService
                    .open(ConsultorDialogComponent as Component, params['id']);
            } else {
                this.consultorPopupService
                    .open(ConsultorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
