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
  private AS_LINE = "/asLine";
  private BY_YEAR = "/years";
  private ACCOUNT_SNAPSHOTS_SUMMED_BY_ASSERTS = this.ACCOUNT_SNAPSHOTS + this.SUMMED_BY_ASSERTS;
  private ACCOUNT_SNAPSHOTS_AS_LINE = this.ACCOUNT_SNAPSHOTS + this.AS_LINE;
  private ACCOUNT_SNAPSHOTS_BY_YEAR = this.ACCOUNT_SNAPSHOTS + this.BY_YEAR;

  url = this.PROXY + this.ACCOUNT_SNAPSHOTS;
  urlSummedByDate = this.PROXY + this.ACCOUNT_SNAPSHOTS_SUMMED_BY_ASSERTS;
  urlAsLine = this.PROXY + this.ACCOUNT_SNAPSHOTS_AS_LINE;
  urlByYear = this.PROXY + this.ACCOUNT_SNAPSHOTS_BY_YEAR;

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

  public getAccountSnapshotsByYear(): Promise<any> {
    return this.httpClient.get(this.urlByYear)
      .toPromise()
      .then(response => {
        return response;
      })
  }

}
