import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Consultores } from './consultores.model';
import { ConsultoresPopupService } from './consultores-popup.service';
import { ConsultoresService } from './consultores.service';

@Component({
    selector: 'jhi-consultores-delete-dialog',
    templateUrl: './consultores-delete-dialog.component.html'
})
export class ConsultoresDeleteDialogComponent {

    consultores: Consultores;

    constructor(
        private consultoresService: ConsultoresService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultoresService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'consultoresListModification',
                content: 'Deleted an consultores'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consultores-delete-popup',
    template: ''
})
export class ConsultoresDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultoresPopupService: ConsultoresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.consultoresPopupService
                .open(ConsultoresDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
