import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Consultores } from './consultores.model';
import { ConsultoresService } from './consultores.service';

@Injectable()
export class ConsultoresPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private consultoresService: ConsultoresService

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
                this.consultoresService.find(id).subscribe((consultores) => {
                    if (consultores.dataprimeiroregistro) {
                        consultores.dataprimeiroregistro = {
                            year: consultores.dataprimeiroregistro.getFullYear(),
                            month: consultores.dataprimeiroregistro.getMonth() + 1,
                            day: consultores.dataprimeiroregistro.getDate()
                        };
                    }
                    if (consultores.dataultimoregistro) {
                        consultores.dataultimoregistro = {
                            year: consultores.dataultimoregistro.getFullYear(),
                            month: consultores.dataultimoregistro.getMonth() + 1,
                            day: consultores.dataultimoregistro.getDate()
                        };
                    }
                    this.ngbModalRef = this.consultoresModalRef(component, consultores);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.consultoresModalRef(component, new Consultores());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    consultoresModalRef(component: Component, consultores: Consultores): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.consultores = consultores;
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
