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

  summedByDateUrl = `${this.PROXY}/accountSnapshots/summedByAssert`;
  asLineUrl = `${this.PROXY}/accountSnapshots/years/asLine`;
  monthlyGrowthAsLineUrl = `${this.PROXY}/accountSnapshots/years/monthlyGrowth/asLine`;
  asBarUrl = `${this.PROXY}/accountSnapshots/years/asBar`;
  asPieUrl = `${this.PROXY}/accountSnapshots/latest/asPie`;

  constructor(private httpClient: HttpClient) {
  }

  public getAccountSnapshotsSummedByAsserts(): Promise<any> {
    return this.httpClient.get(this.summedByDateUrl)
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

  public getAccountSnapshotsAsBar(): Promise<any> {
    return this.httpClient.get(this.asBarUrl)
      .toPromise()
      .then(response => response)
  }

  public getAccountSnapshotsAsPie(): Promise<any> {
    return this.httpClient.get(this.asPieUrl)
      .toPromise()
      .then(response => response)
  }
}
