import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Consultores } from './consultores.model';
import { ConsultoresService } from './consultores.service';

@Component({
    selector: 'jhi-consultores-detail',
    templateUrl: './consultores-detail.component.html'
})
export class ConsultoresDetailComponent implements OnInit, OnDestroy {

    consultores: Consultores;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private consultoresService: ConsultoresService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConsultores();
    }

    load(id) {
        this.consultoresService.find(id).subscribe((consultores) => {
            this.consultores = consultores;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConsultores() {
        this.eventSubscriber = this.eventManager.subscribe(
            'consultoresListModification',
            (response) => this.load(this.consultores.id)
        );
    }
}
