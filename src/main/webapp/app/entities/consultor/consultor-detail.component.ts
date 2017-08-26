import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Consultor } from './consultor.model';
import { ConsultorService } from './consultor.service';

@Component({
    selector: 'jhi-consultor-detail',
    templateUrl: './consultor-detail.component.html'
})
export class ConsultorDetailComponent implements OnInit, OnDestroy {

    consultor: Consultor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private consultorService: ConsultorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConsultors();
    }

    load(id) {
        this.consultorService.find(id).subscribe((consultor) => {
            this.consultor = consultor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConsultors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'consultorListModification',
            (response) => this.load(this.consultor.id)
        );
    }
}
