import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConsultorComponent } from './consultor.component';
import { ConsultorDetailComponent } from './consultor-detail.component';
import { ConsultorPopupComponent } from './consultor-dialog.component';
import { ConsultorDeletePopupComponent } from './consultor-delete-dialog.component';

@Injectable()
export class ConsultorResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const consultorRoute: Routes = [
    {
        path: 'consultor',
        component: ConsultorComponent,
        resolve: {
            'pagingParams': ConsultorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultors'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'consultor/:id',
        component: ConsultorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consultorPopupRoute: Routes = [
    {
        path: 'consultor-new',
        component: ConsultorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultor/:id/edit',
        component: ConsultorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultor/:id/delete',
        component: ConsultorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
