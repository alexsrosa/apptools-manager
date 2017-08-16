import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Parametro } from './parametro.model';
import { ParametroService } from './parametro.service';

@Component({
    selector: 'jhi-parametro-detail',
    templateUrl: './parametro-detail.component.html'
})
export class ParametroDetailComponent implements OnInit, OnDestroy {

    parametro: Parametro;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private parametroService: ParametroService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParametros();
    }

    load(id) {
        this.parametroService.find(id).subscribe((parametro) => {
            this.parametro = parametro;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParametros() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parametroListModification',
            (response) => this.load(this.parametro.id)
        );
    }
}
