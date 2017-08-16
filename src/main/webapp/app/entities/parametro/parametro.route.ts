import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ParametroComponent } from './parametro.component';
import { ParametroDetailComponent } from './parametro-detail.component';
import { ParametroPopupComponent } from './parametro-dialog.component';
import { ParametroDeletePopupComponent } from './parametro-delete-dialog.component';

@Injectable()
export class ParametroResolvePagingParams implements Resolve<any> {

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

export const parametroRoute: Routes = [
    {
        path: 'parametro',
        component: ParametroComponent,
        resolve: {
            'pagingParams': ParametroResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Parametros'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parametro/:id',
        component: ParametroDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Parametros'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametroPopupRoute: Routes = [
    {
        path: 'parametro-new',
        component: ParametroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Parametros'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametro/:id/edit',
        component: ParametroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Parametros'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametro/:id/delete',
        component: ParametroDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Parametros'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
