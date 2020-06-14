import {Component, Input, OnInit} from '@angular/core';
import {Chart} from 'chart.js';

@Component({
  selector: 'chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  @Input() id: string
  @Input() type: string
  @Input() result: Promise<any>
  @Input() dataSets: any

  constructor() {
  }

  ngOnInit(): void {

    this.result.then(result => {

      const {labels, dataSets} = this.mapResults(result, this.dataSets);
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

  private mapResults(result, dataStyles) {
    let labels = result.dateLabels;
    let dataSets = new Array;

    const mapObjectToMap = (obj => {
      const mp = new Map;
      Object.keys(obj).forEach(k => {
        mp.set(k, obj[k])
      });
      return mp;
    });
    const map = mapObjectToMap(result.data)
    map.forEach((value, key) => {
      dataSets.push({label: key, data: value});
    })
    for (let i = 0; i < dataSets.length; i++) {
      Object.assign(dataSets[i], dataStyles[i])
    }

    return {labels, dataSets};
  }

}
