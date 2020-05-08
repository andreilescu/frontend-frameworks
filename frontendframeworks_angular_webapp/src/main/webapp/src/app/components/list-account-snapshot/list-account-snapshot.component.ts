import {Component, OnInit} from '@angular/core';
import {AccountSnapshotService} from "../../services/account-snapshot.service";
import {Chart} from "chart.js"

@Component({
  selector: 'app-list-account-snapshot',
  templateUrl: './list-account-snapshot.component.html',
  styleUrls: ['./list-account-snapshot.component.css']
})
export class ListAccountSnapshotComponent implements OnInit {

  constructor(private accountSnapshotService: AccountSnapshotService) {
  }

  ngOnInit(): void {
    this.getAccountSnapshotsAsLine();
    this.getAccountSnapshotAsBar();
  }

  getAccountSnapshotsAsLine() {
    this.accountSnapshotService.getAccountSnapshotsAsLine().then(result => {

      const labels = result.dateLabels;
      const dataSets = new Array;
      const mapObjectToMap = ( obj => {
        const mp = new Map;
        Object.keys ( obj ). forEach (k => { mp.set(k, obj[k]) });
        return mp;
      });
      const map = mapObjectToMap(result.snapshotsByUser)
      map.forEach((value, key) => {
          dataSets.push({label: key, data: value});
        })

      const dataStyles = [
        {
          fill: false,
          borderColor: "#d5d5d5"
        },
        {
          fill: true,
          borderColor: "#afdcff",
          backgroundColor: "#cfeeff"
        },
        {
          fill: true,
          borderColor: "#c45850",
          backgroundColor: "#fcb5af"
        }
      ];

      for (let i = 0; i < dataSets.length; i++) {
        Object.assign(dataSets[i], dataStyles[i])
      }

      const myLineChart = new Chart('line', {
        type: 'line',
        data: {
          labels: labels,
          datasets: dataSets
        }
      });
    });
  }

  getAccountSnapshotAsBar() {

    this.accountSnapshotService.getAccountSnapshotsByYear().then(result => {

      const labels = result.dateLabels;
      const dataSets = new Array;
      const mapObjectToMap = ( obj => {
        const mp = new Map;
        Object.keys ( obj ). forEach (k => { mp.set(k, obj[k]) });
        return mp;
      });
      const map = mapObjectToMap(result.snapshotsByUser)
      map.forEach((value, key) => {
        dataSets.push({label: key, data: value, backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"]});
      })

      const dataStyles = [
        {
          backgroundColor: "#3e95cd"
        },
        {
          backgroundColor: "#8e5ea2"
        },
        {
          backgroundColor: "#3cba9f"
        }
      ];

      for (let i = 0; i < dataSets.length; i++) {
        Object.assign(dataSets[i], dataStyles[i])
      }

      const myBarChart = new Chart('bar', {
        type: 'bar',
        data: {
          labels: labels,
          datasets: dataSets
        }
      });
    });
  }
}
