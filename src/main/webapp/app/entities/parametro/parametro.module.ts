import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagerSharedModule } from '../../shared';
import {
    ParametroService,
    ParametroPopupService,
    ParametroComponent,
    ParametroDetailComponent,
    ParametroDialogComponent,
    ParametroPopupComponent,
    ParametroDeletePopupComponent,
    ParametroDeleteDialogComponent,
    parametroRoute,
    parametroPopupRoute,
    ParametroResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...parametroRoute,
    ...parametroPopupRoute,
];

@NgModule({
    imports: [
        ManagerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ParametroComponent,
        ParametroDetailComponent,
        ParametroDialogComponent,
        ParametroDeleteDialogComponent,
        ParametroPopupComponent,
        ParametroDeletePopupComponent,
    ],
    entryComponents: [
        ParametroComponent,
        ParametroDialogComponent,
        ParametroPopupComponent,
        ParametroDeleteDialogComponent,
        ParametroDeletePopupComponent,
    ],
    providers: [
        ParametroService,
        ParametroPopupService,
        ParametroResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManagerParametroModule {}
