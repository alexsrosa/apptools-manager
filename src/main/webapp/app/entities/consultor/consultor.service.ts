import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Consultor } from './consultor.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConsultorService {

    private resourceUrl = 'api/consultors';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(consultor: Consultor): Observable<Consultor> {
        const copy = this.convert(consultor);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(consultor: Consultor): Observable<Consultor> {
        const copy = this.convert(consultor);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Consultor> {
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

    private convert(consultor: Consultor): Consultor {
        const copy: Consultor = Object.assign({}, consultor);
        copy.dataprimeiroregistro = this.dateUtils
            .convertLocalDateToServer(consultor.dataprimeiroregistro);
        copy.dataultimoregistro = this.dateUtils
            .convertLocalDateToServer(consultor.dataultimoregistro);
        return copy;
    }
}
