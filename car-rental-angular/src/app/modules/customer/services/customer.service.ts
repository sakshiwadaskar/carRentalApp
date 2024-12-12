import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { StorageService } from '../../../auth/components/services/storage/storage.service'

const BASIC_URL = 'http://localhost:8080'

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  constructor(private http: HttpClient) {}

  getAllCars(): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/customer/cars`, {
      headers: this.createAuthorizationHeader()
    })
  }

  getCarById(id: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/customer/car/${id}`, {
      headers: this.createAuthorizationHeader()
    })
  }

  bookACar(bookACar: any): Observable<any> {
    return this.http.post(`${BASIC_URL}/api/customer/car/book`, bookACar, {
      headers: this.createAuthorizationHeader()
    })
  }

  getBookingsByUserId(): Observable<any> {
    const userId = StorageService.getUserId()
      ? Number(StorageService.getUserId())
      : 0

    return this.http.get(`${BASIC_URL}/api/customer/car/bookings/${userId}`, {
      headers: this.createAuthorizationHeader()
    })
  }

  updateBookingStatus(bookingId: number, status: string): Observable<any> {
      return this.http.put(`${BASIC_URL}/api/customer/car/bookings/${bookingId}/status`, status, {
        headers: { 'Content-Type': 'application/json' },
      });
    }

  deleteBooking(bookingId: number): Observable<any> {
    return this.http.delete(`${BASIC_URL}/api/customer/car/bookings/${bookingId}`, {
      headers: this.createAuthorizationHeader(),
      responseType: 'text', // Expect plain text response
    });
  }

  private createAuthorizationHeader(): HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders()

    return authHeaders.set(
      'Authorization',
      `Bearer ${StorageService.getToken()}`
    )
  }
}
