import { BaseEntity } from './../../shared';

export class Parametro implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public valor?: string,
        public datainclusao?: any,
        public dataultimaatualizacao?: any,
    ) {
    }
}
