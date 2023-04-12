import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class EntityService<T> {

  private apiUrl = environment.apiBaseUrl;

  public resource?: string;

  constructor(private http: HttpClient) { }

  public get(): Observable<T[]> {
    return this.http.get<T[]>(`${this.apiUrl}/${this.resource}`);
  }

  public getById(id: string): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}/${this.resource}/${id}`);
  }

  public add(item: T): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${this.resource}`, item);
  }

  public update(id: string, item: T): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${this.resource}/${id}`, item);
  }

  public delete(id: string): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}/${this.resource}/${id}`);
  }
}
