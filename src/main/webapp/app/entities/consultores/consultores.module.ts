import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagerSharedModule } from '../../shared';
import {
    ConsultoresService,
    ConsultoresPopupService,
    ConsultoresComponent,
    ConsultoresDetailComponent,
    ConsultoresDialogComponent,
    ConsultoresPopupComponent,
    ConsultoresDeletePopupComponent,
    ConsultoresDeleteDialogComponent,
    consultoresRoute,
    consultoresPopupRoute,
    ConsultoresResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...consultoresRoute,
    ...consultoresPopupRoute,
];

@NgModule({
    imports: [
        ManagerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConsultoresComponent,
        ConsultoresDetailComponent,
        ConsultoresDialogComponent,
        ConsultoresDeleteDialogComponent,
        ConsultoresPopupComponent,
        ConsultoresDeletePopupComponent,
    ],
    entryComponents: [
        ConsultoresComponent,
        ConsultoresDialogComponent,
        ConsultoresPopupComponent,
        ConsultoresDeleteDialogComponent,
        ConsultoresDeletePopupComponent,
    ],
    providers: [
        ConsultoresService,
        ConsultoresPopupService,
        ConsultoresResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManagerConsultoresModule {}
