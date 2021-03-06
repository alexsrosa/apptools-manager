import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { TimeSheet } from './time-sheet.model';
import { TimeSheetService } from './time-sheet.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-time-sheet',
    templateUrl: './time-sheet.component.html'
})
export class TimeSheetComponent implements OnInit, OnDestroy {

currentAccount: any;
    timeSheets: TimeSheet[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    nomeSearch: string;
    tarefaSearch: string;
    dataInicioSearch: string;
    dataFimSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private timeSheetService: TimeSheetService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        if (this.nomeSearch || this.tarefaSearch || this.dataInicioSearch || this.dataFimSearch) {
            this.timeSheetService.search({
                nome: this.nomeSearch,
                tarefa: this.tarefaSearch,
                dataInicio: this.dataInicioSearch,
                dataFim: this.dataFimSearch,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()}).subscribe(
                (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)
            );
            return;
        }
        this.timeSheetService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/time-sheet'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.nomeSearch = '';
        this.tarefaSearch = '';
        this.dataInicioSearch = '';
        this.dataFimSearch = '';
        this.router.navigate(['/time-sheet', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
    }
    search(nome, tarefa, dataInicio, dataFim) {
        this.page = 0;
        this.nomeSearch = nome;
        this.tarefaSearch = tarefa;
        this.dataInicioSearch = dataInicio;
        this.dataFimSearch = dataFim;
        this.router.navigate(['/time-sheet', {
            nome: this.nomeSearch,
            tarefa: this.tarefaSearch,
            dataInicio: this.dataInicioSearch,
            dataFim: this.dataFimSearch,
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTimeSheets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TimeSheet) {
        return item.id;
    }
    registerChangeInTimeSheets() {
        this.eventSubscriber = this.eventManager.subscribe('timeSheetListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.timeSheets = data;
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
