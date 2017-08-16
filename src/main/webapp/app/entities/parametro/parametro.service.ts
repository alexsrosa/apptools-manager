import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Parametro } from './parametro.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ParametroService {

    private resourceUrl = 'api/parametros';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(parametro: Parametro): Observable<Parametro> {
        const copy = this.convert(parametro);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(parametro: Parametro): Observable<Parametro> {
        const copy = this.convert(parametro);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Parametro> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.datainclusao = this.dateUtils
            .convertLocalDateFromServer(entity.datainclusao);
        entity.dataultimaatualizacao = this.dateUtils
            .convertLocalDateFromServer(entity.dataultimaatualizacao);
    }

    private convert(parametro: Parametro): Parametro {
        const copy: Parametro = Object.assign({}, parametro);
        copy.datainclusao = this.dateUtils
            .convertLocalDateToServer(parametro.datainclusao);
        copy.dataultimaatualizacao = this.dateUtils
            .convertLocalDateToServer(parametro.dataultimaatualizacao);
        return copy;
    }
}
