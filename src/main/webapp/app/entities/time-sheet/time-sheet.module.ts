import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagerSharedModule } from '../../shared';
import {
    TimeSheetService,
    TimeSheetPopupService,
    TimeSheetComponent,
    TimeSheetDetailComponent,
    TimeSheetDialogComponent,
    TimeSheetPopupComponent,
    timeSheetRoute,
    timeSheetPopupRoute,
    TimeSheetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...timeSheetRoute,
    ...timeSheetPopupRoute,
];

@NgModule({
    imports: [
        ManagerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TimeSheetComponent,
        TimeSheetDetailComponent,
        TimeSheetDialogComponent,
        TimeSheetPopupComponent,
    ],
    entryComponents: [
        TimeSheetComponent,
        TimeSheetDialogComponent,
        TimeSheetPopupComponent,
    ],
    providers: [
        TimeSheetService,
        TimeSheetPopupService,
        TimeSheetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManagerTimeSheetModule {}
