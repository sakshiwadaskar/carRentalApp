import { Component } from '@angular/core'
import { FormBuilder, FormGroup } from '@angular/forms'
import { AdminService } from '../../services/admin.service'

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrl: './search-car.component.scss'
})
export class SearchCarComponent {
  searchCarForm!: FormGroup
  listOfOption: Array<{ label: string; value: string }> = []
  listOfBrands = ['Toyota', 'Honda', 'BMW', 'Mercedes', 'Audi', 'Lexus']
  listOfType = ['Sports Car', 'Diesel', 'Crossover', 'Luxury Car']
  listOfColor = ['Red', 'Blue', 'Brown', 'Green']
  listOfTransmission = ['Manual', 'Automatic']
  isSpinning = false
  cars: any[] = []

  constructor(
    private fb: FormBuilder,
    private service: AdminService
  ) {
    this.searchCarForm = this.fb.group({
      brand: [null],
      type: [null],
      transmission: [null],
      color: [null]
    })
  }
      searchCar() {
        this.isSpinning = true
        this.cars = [] // Clear previous search results
        this.service.searchCar(this.searchCarForm.value).subscribe(
          res => {
            this.isSpinning = false
            const carDtoList = res.carDtoList

            if (carDtoList.length === 0) {
              // Show a message if no cars are available
              alert('No cars available for the selected criteria.') // Simple alert (can replace with a UI element)
            } else {
              carDtoList.forEach((car: any) => {
                car.processedImage = `data:image/jpeg;base64,${car.returnedImage}`
                this.cars.push(car)
              })
            }
          },
          err => {
            this.isSpinning = false
            alert('An error occurred while searching for cars.') // Handle errors gracefully
          }
        )
      }

}
