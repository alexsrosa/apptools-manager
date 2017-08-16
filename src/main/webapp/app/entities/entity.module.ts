import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ManagerParametroModule } from './parametro/parametro.module';
import { ManagerTimeSheetModule } from './time-sheet/time-sheet.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ManagerParametroModule,
        ManagerTimeSheetModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManagerEntityModule {}
