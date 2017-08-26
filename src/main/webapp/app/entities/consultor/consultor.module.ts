import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagerSharedModule } from '../../shared';
import {
    ConsultorService,
    ConsultorPopupService,
    ConsultorComponent,
    ConsultorDetailComponent,
    ConsultorDialogComponent,
    ConsultorPopupComponent,
    ConsultorDeletePopupComponent,
    ConsultorDeleteDialogComponent,
    consultorRoute,
    consultorPopupRoute,
    ConsultorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...consultorRoute,
    ...consultorPopupRoute,
];

@NgModule({
    imports: [
        ManagerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConsultorComponent,
        ConsultorDetailComponent,
        ConsultorDialogComponent,
        ConsultorDeleteDialogComponent,
        ConsultorPopupComponent,
        ConsultorDeletePopupComponent,
    ],
    entryComponents: [
        ConsultorComponent,
        ConsultorDialogComponent,
        ConsultorPopupComponent,
        ConsultorDeleteDialogComponent,
        ConsultorDeletePopupComponent,
    ],
    providers: [
        ConsultorService,
        ConsultorPopupService,
        ConsultorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManagerConsultorModule {}
