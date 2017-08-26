import { BaseEntity } from './../../shared';

const enum ConsultorStatus {
    'ATIVADO',
    'DESATIVADO'
}

export class Consultor implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public dataprimeiroregistro?: any,
        public dataultimoregistro?: any,
        public flativo?: ConsultorStatus,
    ) {
    }
}
