import { Component, OnInit, ViewChild } from '@angular/core';
import { Phone } from './phone';
import { PhoneService } from '../phone.service';
import { MessageService } from '../message.service';
import { PhoneResponse } from './phoneresponse';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-phoneinput',
  templateUrl: './phoneinput.component.html',
  styleUrls: ['./phoneinput.component.css']
})
export class PhoneinputComponent implements OnInit {

  phoneEntity: PhoneResponse = {totalHits: 0, phoneNumberCombinations: []};
  
  phoneNumberToUse: Phone;

  pageIndex = 0;
  pageSize:number = 5;
  pageEvent: PageEvent;
  totalPages: number = 0;
  manualPage: number;
  
  phoneNumber: Phone = {
    phoneNumber: '222-222-2222',
    alphaNumericCombination: '222-222-2222 A'
  };

  displayedColumns: string[] = ['phoneNumber', 'alphaNumericCombination'];
  dataSource = new MatTableDataSource(this.phoneEntity.phoneNumberCombinations);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private phoneService: PhoneService, private messageService: MessageService, private snackBar: MatSnackBar) {
    
   }

  ngOnInit(): void {
  }

  // Generate the combinations using the phoneNumber from the html component
  onGenerateCombinations(phone:Phone) {
    this.phoneNumberToUse = phone;
    this.phoneService.generateCombinations(this.phoneNumberToUse.phoneNumber).subscribe(() => {
      this.getCombinations(this.pageSize, this.pageIndex);
    },
    err => {
      this.snackBar.open(err.error.message, 'OK', { duration: 5000 });
    });
  }

  // This method is used initially in combination with the generation of combinations to allow generation of combinations and also 
  // retrieve the generated combinations to show the first page of results
  getCombinations(pageSize:number, pageNumber:number): void {
    if (this.phoneNumberToUse != null) {
      this.phoneService.getCombinations(this.phoneNumberToUse.phoneNumber, pageNumber, pageSize)
        .subscribe(phones => {
          this.phoneEntity = phones;
          this.dataSource = new MatTableDataSource(this.phoneEntity.phoneNumberCombinations);
        },
        err => {
          this.snackBar.open(err.error.message, 'OK', { duration: 5000 });
        });
    }
  }

  // This handles the paging event to retrieve the next page
  getNextPage(event: PageEvent):PageEvent {
    this.phoneService.getCombinations(this.phoneNumberToUse.phoneNumber, event.pageIndex, event.pageSize)
        .subscribe(phones => {
          this.phoneEntity = phones;
          this.dataSource = new MatTableDataSource(this.phoneEntity.phoneNumberCombinations);
          this.pageIndex = event.pageIndex;
        },
        err => {
          this.snackBar.open(err.error.message, 'OK', { duration: 5000 });
        });
        return event;
  }
}
