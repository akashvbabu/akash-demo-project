import { Phone } from './phone';
// This is a enclosing Object entity to represent a response from the server 
// which contains totalHits and the phone records
export interface PhoneResponse {
    totalHits: number;
    phoneNumberCombinations: Phone[];

}