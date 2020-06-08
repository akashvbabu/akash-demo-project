import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient } from '@angular/common/http';
import { PhoneResponse } from './phoneinput/phoneresponse';

// This service is used to interact with the /phoneNumber endpoints
@Injectable({
  providedIn: 'root'
})
export class PhoneService {

  private getPhoneCombinationUrl = "http://localhost:8080/phoneNumber/combinations";
  private generateCombinationUrl = "http://localhost:8080/phoneNumber/process";

  constructor(private messageService: MessageService, 
              private httpClient: HttpClient) { }

  
  // Generate the combinations by making POST call to the /process endpoint
  generateCombinations(phoneNumber: string): Observable<any> {
    const fullUrl = this.generateCombinationUrl + "?phone=" + phoneNumber;
    return this.httpClient.post(fullUrl, null);
  }

  // Retrieve the combinations by making a GET call to /combinations endpoint with the pageNumber and pageSize
  getCombinations(phoneNumber: string, pageNumber: number, pageSize: number): Observable<PhoneResponse> {
    console.log("page number: " + pageNumber);
    const fullUrl = this.getPhoneCombinationUrl + "?phone=" + phoneNumber + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize; 
    return this.httpClient.get<PhoneResponse>(fullUrl)
  }
}
