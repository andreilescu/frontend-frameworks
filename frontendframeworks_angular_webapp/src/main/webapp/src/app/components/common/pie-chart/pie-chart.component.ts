import {Component, Input, OnInit} from '@angular/core';
import {Chart} from 'chart.js';

@Component({
  selector: 'pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

  @Input() id: string
  @Input() type: string
  @Input() result: Promise<any>
  @Input() dataSets: any

  constructor() {
  }

  ngOnInit(): void {

    this.result.then(result => {

      const {labels, dataSets} = this.mapResults(result)
      new Chart(this.id, {
        type: this.type,
        data: {
          labels: labels,
          datasets: dataSets
        },
        options: {
          maintainAspectRatio: false
        }
      });
    })
  }

  private mapResults(result) {
    const {data, labels} = result;
    let dataSets = [{
      backgroundColor: this.dataSets,
      data: data
    }]
    return {labels, dataSets};
  }

}
