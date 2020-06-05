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
    this.getAccountSnapshotsAsBar();
    this.getAccountSnapshotsAsPie();
  }

  getAccountSnapshotsAsLine() {
    this.accountSnapshotService.getAccountSnapshotsAsLine().then(result => {

      const labels = result.dateLabels;
      const dataSets = new Array;
      const mapObjectToMap = (obj => {
        const mp = new Map;
        Object.keys(obj).forEach(k => {
          mp.set(k, obj[k])
        });
        return mp;
      });
      const map = mapObjectToMap(result.snapshotsByUser)
      map.forEach((value, key) => {
        dataSets.push({label: key, data: value});
      })

      const dataStyles = [
        {
          fill: false,
          borderColor: "#3cba9f"
        },
        {
          fill: true,
          borderColor: "#afdcff",
          backgroundColor: "#3e95cd"
        },
        {
          fill: true,
          borderColor: "#ca9ddc",
          backgroundColor: "#8e5ea2"
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
        },
        options: {
          maintainAspectRatio: false
        }
      });
    });
  }

  getAccountSnapshotsAsBar() {

    this.accountSnapshotService.getAccountSnapshotsAsBar().then(result => {

      const labels = result.dateLabels;
      const dataSets = new Array;
      const mapObjectToMap = (obj => {
        const mp = new Map;
        Object.keys(obj).forEach(k => {
          mp.set(k, obj[k])
        });
        return mp;
      });
      const map = mapObjectToMap(result.snapshotsByUser)
      map.forEach((value, key) => {
        dataSets.push({label: key, data: value});
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
        },
        options: {
          maintainAspectRatio: false
        }
      });
    });
  }

  getAccountSnapshotsAsPie() {

    this.accountSnapshotService.getAccountSnapshotsAsPie().then(result => {

      new Chart(document.getElementById("pie"), {
        type: 'pie',
        data: {
          labels: result.labels,
          datasets: [{
            backgroundColor: ["#fdd700", "#8e5ea2", "#f36e3a", "#3cba9f"],
            data: result.data
          }]
        },
        options: {
          maintainAspectRatio: false
        }
      });
    });
  }
}
