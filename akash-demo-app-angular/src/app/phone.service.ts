import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient } from '@angular/common/http';
import { PhoneResponse } from './phoneinput/phoneresponse';

@Injectable({
  providedIn: 'root'
})
export class PhoneService {

  private getPhoneCombinationUrl = "http://localhost:8080/phoneNumber/combinations";
  private generateCombinationUrl = "http://localhost:8080/phoneNumber/process";

  constructor(private messageService: MessageService, 
              private httpClient: HttpClient) { }

  
  generateCombinations(phoneNumber: string): Observable<any> {
    const fullUrl = this.generateCombinationUrl + "?phone=" + phoneNumber;
    return this.httpClient.post(fullUrl, null);
  }

  getCombinations(phoneNumber: string, pageNumber: number, pageSize: number): Observable<PhoneResponse> {
    console.log("page number: " + pageNumber);
    const fullUrl = this.getPhoneCombinationUrl + "?phone=" + phoneNumber + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize; 
    return this.httpClient.get<PhoneResponse>(fullUrl)
  }
}
