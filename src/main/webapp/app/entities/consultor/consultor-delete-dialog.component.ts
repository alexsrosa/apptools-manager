import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Consultor } from './consultor.model';
import { ConsultorPopupService } from './consultor-popup.service';
import { ConsultorService } from './consultor.service';

@Component({
    selector: 'jhi-consultor-delete-dialog',
    templateUrl: './consultor-delete-dialog.component.html'
})
export class ConsultorDeleteDialogComponent {

    consultor: Consultor;

    constructor(
        private consultorService: ConsultorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'consultorListModification',
                content: 'Deleted an consultor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consultor-delete-popup',
    template: ''
})
export class ConsultorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultorPopupService: ConsultorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.consultorPopupService
                .open(ConsultorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
