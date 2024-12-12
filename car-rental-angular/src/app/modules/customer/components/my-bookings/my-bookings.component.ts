import { Component } from '@angular/core'
import { CustomerService } from '../../services/customer.service'
import { ActivatedRoute } from '@angular/router'
import { StorageService } from '../../../../auth/components/services/storage/storage.service'

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrl: './my-bookings.component.scss'
})
export class MyBookingsComponent {
  constructor(private service: CustomerService) {}

  bookings: any[] = []
  isSpinning = false

  ngOnInit() {
    this.getBookingsByUserId()
  }

  private getBookingsByUserId(): void {
      this.isSpinning = true;

      this.service.getBookingsByUserId().subscribe(
        (data) => {
          console.log('Bookings:', data); // Debug to ensure carName and price are present
          this.bookings = data;
          this.isSpinning = false;
        },
        (error) => {
          console.error('Error fetching bookings:', error);
          this.isSpinning = false;
      }
    )
  }
}
