import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AccountSnapshotService {

  /**
   * TODO extract to constant that properties are in one place
   */
  private HOST = "http://localhost";
  private PORT = "8080";
  private PROXY = `${this.HOST}:${this.PORT}`;

  // /accountSnapshots/months
  yearsUrl = `${this.PROXY}/accountSnapshots/summedByAssert`;
  // /accountSnapshots/months/asserts/asLine
  asLineUrl = `${this.PROXY}/accountSnapshots/years/asLine`;
  // /accountSnapshots/months/asserts/monthlyGrowth/asLine
  monthlyGrowthAsLineUrl = `${this.PROXY}/accountSnapshots/years/monthlyGrowth/asLine`;

  monthlyGrowthByAssertsAsLineUrl = `${this.PROXY}/accountSnapshots/months/monthlyGrowth/asserts/asLine`;

  // /accountSnapshots/years/asserts/yearlyGrowth/asLine
  yearlyGrowthAsBarUrl = `${this.PROXY}/accountSnapshots/years/asBar`;
  // /accountSnapshots/months/latest/asPie
  latestMonthAsPieUrl = `${this.PROXY}/accountSnapshots/latest/asPie`;

  constructor(private httpClient: HttpClient) {
  }

  public getAccountSnapshotsSummedByAsserts(): Promise<any> {
    return this.httpClient.get(this.yearsUrl)
      .toPromise()
      .then(response => response)
  }

  public getAccountSnapshotsAsLine(): Promise<any> {
    return this.httpClient.get(this.asLineUrl)
      .toPromise()
      .then(response => response)
  }

  public getAccountSnapshotsMonthlyGrowthAsLine(): Promise<any> {
    return this.httpClient.get(this.monthlyGrowthAsLineUrl)
      .toPromise()
      .then(response => response)
  }

  public getAccountSnapshotsMonthlyGrowthByAssertsAsLine(): Promise<any> {
    return this.httpClient.get(this.monthlyGrowthByAssertsAsLineUrl)
      .toPromise()
      .then(response => response)
  }

  public getAccountSnapshotsAsBar(): Promise<any> {
    return this.httpClient.get(this.yearlyGrowthAsBarUrl)
      .toPromise()
      .then(response => response)
  }

  public getAccountSnapshotsAsPie(): Promise<any> {
    return this.httpClient.get(this.latestMonthAsPieUrl)
      .toPromise()
      .then(response => response)
  }
}
