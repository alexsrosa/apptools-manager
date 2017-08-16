import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Parametro } from './parametro.model';
import { ParametroPopupService } from './parametro-popup.service';
import { ParametroService } from './parametro.service';

@Component({
    selector: 'jhi-parametro-dialog',
    templateUrl: './parametro-dialog.component.html'
})
export class ParametroDialogComponent implements OnInit {

    parametro: Parametro;
    isSaving: boolean;
    datainclusaoDp: any;
    dataultimaatualizacaoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private parametroService: ParametroService,
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
        if (this.parametro.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parametroService.update(this.parametro));
        } else {
            this.subscribeToSaveResponse(
                this.parametroService.create(this.parametro));
        }
    }

    private subscribeToSaveResponse(result: Observable<Parametro>) {
        result.subscribe((res: Parametro) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Parametro) {
        this.eventManager.broadcast({ name: 'parametroListModification', content: 'OK'});
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
    selector: 'jhi-parametro-popup',
    template: ''
})
export class ParametroPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametroPopupService: ParametroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parametroPopupService
                    .open(ParametroDialogComponent as Component, params['id']);
            } else {
                this.parametroPopupService
                    .open(ParametroDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
