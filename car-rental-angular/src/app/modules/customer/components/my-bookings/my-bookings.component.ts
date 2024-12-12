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
  changeBookingStatus(bookingId: number, status: string): void {
      this.isSpinning = true;
      this.service.updateBookingStatus(bookingId, status).subscribe(
        () => {
          this.getBookingsByUserId(); // Refresh the bookings after the status change
          this.isSpinning = false;
        },
        (error) => {
          console.error('Error updating booking status:', error);
          this.isSpinning = false;
        }
      );
    }

   deleteBooking(bookingId: number): void {
      if (confirm('Are you sure you want to delete this booking?')) {
        this.isSpinning = true;
        this.service.deleteBooking(bookingId).subscribe(
          () => {
            this.getBookingsByUserId(); // Refresh the bookings after deletion
            this.isSpinning = false;
          },
          (error) => {
            console.error('Error deleting booking:', error);
            this.isSpinning = false;
          }
        );
      }
    }
}
