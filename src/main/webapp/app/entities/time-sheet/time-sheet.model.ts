import { BaseEntity } from './../../shared';

export class TimeSheet implements BaseEntity {
    constructor(
        public id?: number,
        public matricula?: number,
        public nome?: string,
        public tarefa?: number,
        public descricao?: string,
        public codigofase?: number,
        public descricaofase?: string,
        public codigoatividade?: number,
        public descricaoatividade?: string,
        public data?: any,
        public observacao?: string,
        public horas?: string,
        public datainclusao?: any,
        public dataultimaatualizacao?: any,
    ) {
    }
}
