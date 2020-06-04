import { Injectable } from '@angular/core';
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
  private PROXY = this.HOST + ":" + this.PORT;
  private ACCOUNT_SNAPSHOTS = "/accountSnapshots";
  private SUMMED_BY_ASSERTS = "/summedByAsserts";
  private YEARS_AS_LINE = "/years/asLine";
  private YEARS_BY_BAR = "/years/asBar";
  private LATEST_AS_PIE = "/latest/asPie";
  private ACCOUNT_SNAPSHOTS_SUMMED_BY_ASSERTS = this.ACCOUNT_SNAPSHOTS + this.SUMMED_BY_ASSERTS;
  private ACCOUNT_SNAPSHOTS_AS_LINE = this.ACCOUNT_SNAPSHOTS + this.YEARS_AS_LINE;
  private ACCOUNT_SNAPSHOTS_YEARS_AS_BAR = this.ACCOUNT_SNAPSHOTS + this.YEARS_BY_BAR;
  private ACCOUNT_SNAPSHOTS_LATEST_AS_PIE = this.ACCOUNT_SNAPSHOTS + this.LATEST_AS_PIE;

  url = this.PROXY + this.ACCOUNT_SNAPSHOTS;
  urlSummedByDate = this.PROXY + this.ACCOUNT_SNAPSHOTS_SUMMED_BY_ASSERTS;
  urlAsLine = this.PROXY + this.ACCOUNT_SNAPSHOTS_AS_LINE;
  urlAsBar = this.PROXY + this.ACCOUNT_SNAPSHOTS_YEARS_AS_BAR;
  urlAsPie = this.PROXY + this.ACCOUNT_SNAPSHOTS_LATEST_AS_PIE;

    constructor(private httpClient: HttpClient) {
  }

  public getAccountSnapshotsSummedByAsserts(): Promise<any> {
    return this.httpClient.get(this.urlSummedByDate)
      .toPromise()
      .then(response => {
        return response;
      })
  }

  public getAccountSnapshotsAsLine(): Promise<any> {
    return this.httpClient.get(this.urlAsLine)
      .toPromise()
      .then(response => {
        return response;
      })
  }

  public getAccountSnapshotsAsBar(): Promise<any> {
    return this.httpClient.get(this.urlAsBar)
      .toPromise()
      .then(response => {
        return response;
      })
  }

  public getAccountSnapshotsAsPie(): Promise<any> {
    return this.httpClient.get(this.urlAsPie)
      .toPromise()
      .then(response => {
        return response;
      })
  }
}
