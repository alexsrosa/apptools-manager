import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { TimeSheet } from './time-sheet.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TimeSheetService {

    private resourceUrl = 'api/time-sheets';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(timeSheet: TimeSheet): Observable<TimeSheet> {
        const copy = this.convert(timeSheet);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(timeSheet: TimeSheet): Observable<TimeSheet> {
        const copy = this.convert(timeSheet);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<TimeSheet> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        console.log('query');
        console.log(options);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        console.log('search');
        return this.http.get(`${this.resourceUrl}/search/`, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.data = this.dateUtils
            .convertLocalDateFromServer(entity.data);
        entity.datainclusao = this.dateUtils
            .convertLocalDateFromServer(entity.datainclusao);
        entity.dataultimaatualizacao = this.dateUtils
            .convertLocalDateFromServer(entity.dataultimaatualizacao);
    }

    private convert(timeSheet: TimeSheet): TimeSheet {
        const copy: TimeSheet = Object.assign({}, timeSheet);
        copy.data = this.dateUtils
            .convertLocalDateToServer(timeSheet.data);
        copy.datainclusao = this.dateUtils
            .convertLocalDateToServer(timeSheet.datainclusao);
        copy.dataultimaatualizacao = this.dateUtils
            .convertLocalDateToServer(timeSheet.dataultimaatualizacao);
        return copy;
    }
}
