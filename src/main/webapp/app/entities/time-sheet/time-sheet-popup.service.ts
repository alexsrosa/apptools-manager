import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TimeSheet } from './time-sheet.model';
import { TimeSheetService } from './time-sheet.service';

@Injectable()
export class TimeSheetPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private timeSheetService: TimeSheetService

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
                this.timeSheetService.find(id).subscribe((timeSheet) => {
                    if (timeSheet.data) {
                        timeSheet.data = {
                            year: timeSheet.data.getFullYear(),
                            month: timeSheet.data.getMonth() + 1,
                            day: timeSheet.data.getDate()
                        };
                    }
                    if (timeSheet.datainclusao) {
                        timeSheet.datainclusao = {
                            year: timeSheet.datainclusao.getFullYear(),
                            month: timeSheet.datainclusao.getMonth() + 1,
                            day: timeSheet.datainclusao.getDate()
                        };
                    }
                    if (timeSheet.dataultimaatualizacao) {
                        timeSheet.dataultimaatualizacao = {
                            year: timeSheet.dataultimaatualizacao.getFullYear(),
                            month: timeSheet.dataultimaatualizacao.getMonth() + 1,
                            day: timeSheet.dataultimaatualizacao.getDate()
                        };
                    }
                    this.ngbModalRef = this.timeSheetModalRef(component, timeSheet);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.timeSheetModalRef(component, new TimeSheet());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    timeSheetModalRef(component: Component, timeSheet: TimeSheet): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.timeSheet = timeSheet;
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
