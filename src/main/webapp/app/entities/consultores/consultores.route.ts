import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConsultoresComponent } from './consultores.component';
import { ConsultoresDetailComponent } from './consultores-detail.component';
import { ConsultoresPopupComponent } from './consultores-dialog.component';
import { ConsultoresDeletePopupComponent } from './consultores-delete-dialog.component';

@Injectable()
export class ConsultoresResolvePagingParams implements Resolve<any> {

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

export const consultoresRoute: Routes = [
    {
        path: 'consultores',
        component: ConsultoresComponent,
        resolve: {
            'pagingParams': ConsultoresResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultores'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'consultores/:id',
        component: ConsultoresDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultores'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consultoresPopupRoute: Routes = [
    {
        path: 'consultores-new',
        component: ConsultoresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultores'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultores/:id/edit',
        component: ConsultoresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultores'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultores/:id/delete',
        component: ConsultoresDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consultores'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
