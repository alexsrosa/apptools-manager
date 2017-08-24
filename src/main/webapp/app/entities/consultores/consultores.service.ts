import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Consultores } from './consultores.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConsultoresService {

    private resourceUrl = 'api/consultores';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(consultores: Consultores): Observable<Consultores> {
        const copy = this.convert(consultores);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(consultores: Consultores): Observable<Consultores> {
        const copy = this.convert(consultores);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Consultores> {
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
        entity.dataprimeiroregistro = this.dateUtils
            .convertLocalDateFromServer(entity.dataprimeiroregistro);
        entity.dataultimoregistro = this.dateUtils
            .convertLocalDateFromServer(entity.dataultimoregistro);
    }

    private convert(consultores: Consultores): Consultores {
        const copy: Consultores = Object.assign({}, consultores);
        copy.dataprimeiroregistro = this.dateUtils
            .convertLocalDateToServer(consultores.dataprimeiroregistro);
        copy.dataultimoregistro = this.dateUtils
            .convertLocalDateToServer(consultores.dataultimoregistro);
        return copy;
    }
}
