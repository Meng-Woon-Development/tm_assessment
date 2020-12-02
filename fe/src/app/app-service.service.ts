import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const baseUrl = 'http://localhost:8080/api/assignment/save';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  constructor(private http: HttpClient) { }


  create(data: any): Observable<any> {
    let headers = new HttpHeaders().set('access-control-allow-origin', 'http://localhost:8080/'); 
    console.log('post data========>', data);
    return this.http.post(baseUrl, data);
  }
}
