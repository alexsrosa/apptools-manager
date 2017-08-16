import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parametro } from './parametro.model';
import { ParametroPopupService } from './parametro-popup.service';
import { ParametroService } from './parametro.service';

@Component({
    selector: 'jhi-parametro-delete-dialog',
    templateUrl: './parametro-delete-dialog.component.html'
})
export class ParametroDeleteDialogComponent {

    parametro: Parametro;

    constructor(
        private parametroService: ParametroService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parametroService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parametroListModification',
                content: 'Deleted an parametro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parametro-delete-popup',
    template: ''
})
export class ParametroDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametroPopupService: ParametroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parametroPopupService
                .open(ParametroDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
