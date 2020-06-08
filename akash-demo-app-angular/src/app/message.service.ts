import { Injectable } from '@angular/core';

// This class is currently UNUSED, the initial plan was to use this 
// like a notification box but I used SnackBar instead
@Injectable({
  providedIn: 'root'
})
export class MessageService {

  messages: string[] = [];

  add(message: string) {
    this.messages.push(message);
  }

  clear() {
    this.messages = [];
  }

  constructor() { }
}
