import { URLSearchParams, BaseRequestOptions } from '@angular/http';

export const createRequestOption = (req?: any): BaseRequestOptions => {
    const options: BaseRequestOptions = new BaseRequestOptions();
    if (req) {
        const params: URLSearchParams = new URLSearchParams();
        params.set('page', req.page);
        params.set('size', req.size);
        if (req.nome) {
            params.set('nome', req.nome);
        }
        if (req.tarefa) {
            params.set('tarefa', req.tarefa);
        }
        if (req.dataInicio) {
            params.set('dataInicio', req.dataInicio);
        }
        if (req.dataFim) {
            params.set('dataFim', req.dataFim);
        }
        if (req.sort) {
            params.paramsMap.set('sort', req.sort);
        }
        params.set('query', req.query);

        options.params = params;
    }
    return options;
};
