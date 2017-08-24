import { BaseEntity } from './../../shared';

export class Consultores implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public dataprimeiroregistro?: any,
        public dataultimoregistro?: any,
    ) {
    }
}
