import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TimeSheetComponent } from './time-sheet.component';
import { TimeSheetPopupComponent } from './time-sheet-dialog.component';

@Injectable()
export class TimeSheetResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '20';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'data,desc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const timeSheetRoute: Routes = [
    {
        path: 'time-sheet',
        component: TimeSheetComponent,
        resolve: {
            'pagingParams': TimeSheetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeSheets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'time-sheet/:id',
        component: TimeSheetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeSheets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timeSheetPopupRoute: Routes = [
    {
        path: 'time-sheet/:id/view',
        component: TimeSheetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeSheets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
