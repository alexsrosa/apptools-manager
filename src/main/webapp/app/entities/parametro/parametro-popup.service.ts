import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Parametro } from './parametro.model';
import { ParametroService } from './parametro.service';

@Injectable()
export class ParametroPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private parametroService: ParametroService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.parametroService.find(id).subscribe((parametro) => {
                    if (parametro.datainclusao) {
                        parametro.datainclusao = {
                            year: parametro.datainclusao.getFullYear(),
                            month: parametro.datainclusao.getMonth() + 1,
                            day: parametro.datainclusao.getDate()
                        };
                    }
                    if (parametro.dataultimaatualizacao) {
                        parametro.dataultimaatualizacao = {
                            year: parametro.dataultimaatualizacao.getFullYear(),
                            month: parametro.dataultimaatualizacao.getMonth() + 1,
                            day: parametro.dataultimaatualizacao.getDate()
                        };
                    }
                    this.ngbModalRef = this.parametroModalRef(component, parametro);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.parametroModalRef(component, new Parametro());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    parametroModalRef(component: Component, parametro: Parametro): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.parametro = parametro;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
