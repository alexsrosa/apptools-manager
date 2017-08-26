import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Consultor } from './consultor.model';
import { ConsultorService } from './consultor.service';

@Injectable()
export class ConsultorPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private consultorService: ConsultorService

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
                this.consultorService.find(id).subscribe((consultor) => {
                    if (consultor.dataprimeiroregistro) {
                        consultor.dataprimeiroregistro = {
                            year: consultor.dataprimeiroregistro.getFullYear(),
                            month: consultor.dataprimeiroregistro.getMonth() + 1,
                            day: consultor.dataprimeiroregistro.getDate()
                        };
                    }
                    if (consultor.dataultimoregistro) {
                        consultor.dataultimoregistro = {
                            year: consultor.dataultimoregistro.getFullYear(),
                            month: consultor.dataultimoregistro.getMonth() + 1,
                            day: consultor.dataultimoregistro.getDate()
                        };
                    }
                    this.ngbModalRef = this.consultorModalRef(component, consultor);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.consultorModalRef(component, new Consultor());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    consultorModalRef(component: Component, consultor: Consultor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.consultor = consultor;
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
