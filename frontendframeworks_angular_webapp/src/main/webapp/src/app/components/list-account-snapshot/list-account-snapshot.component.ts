import {Component, OnInit} from '@angular/core';
import {AccountSnapshotService} from "../../services/account-snapshot.service";
import {Chart} from "chart.js"

@Component({
  selector: 'app-list-account-snapshot',
  templateUrl: './list-account-snapshot.component.html',
  styleUrls: ['./list-account-snapshot.component.css']
})
export class ListAccountSnapshotComponent implements OnInit {
  line: string = 'line';
  bar: string = 'bar';
  pie: string = 'pie'
  monthlyGrowthLine: string = 'monthlyGrowthLine';
  lineDataSets = [
    {
      fill: false,
      borderColor: "#3cba9f"
    },
    {
      fill: true,
      backgroundColor: "#3e95cd"
    },
    {
      fill: true,
      backgroundColor: "#8e5ea2"
    }
  ];
  monthlyGrowthLineDataSets = [
    {
      fill: false,
      borderColor: "#3cba9f"
    },
    {
      fill: false,
      borderColor: "#3e95cd"
    },
    {
      fill: false,
      borderColor: "#8e5ea2"
    }
  ];
  barDataSets = [
    {
      backgroundColor: "#3cba9f"
    },
    {
      backgroundColor: "#3e95cd"
    },
    {
      backgroundColor: "#8e5ea2"
    }
  ];
  pieDataSets = ["#fdd700", "#8e5ea2", "#f36e3a", "#3cba9f"]

  lineResult: Promise<any>
  monthlyGrowthLineResult: Promise<any>
  barResult: Promise<any>
  pieResult: Promise<any>

  constructor(private accountSnapshotService: AccountSnapshotService) {
  }

  ngOnInit(): void {
    this.getAccountSnapshotsAsLine();
    this.getAccountSnapshotsMonthlyGrowthAsLine();
    this.getAccountSnapshotsAsBar();
    this.getAccountSnapshotsAsPie();
  }

  getAccountSnapshotsAsLine() {
    this.lineResult = this.accountSnapshotService.getAccountSnapshotsAsLine();
  }

  getAccountSnapshotsMonthlyGrowthAsLine() {
    this.monthlyGrowthLineResult = this.accountSnapshotService.getAccountSnapshotsMonthlyGrowthAsLine();
  }

  getAccountSnapshotsAsBar() {
    this.barResult = this.accountSnapshotService.getAccountSnapshotsAsBar();
  }

  getAccountSnapshotsAsPie() {
    this.pieResult = this.accountSnapshotService.getAccountSnapshotsAsPie();
  }

}
