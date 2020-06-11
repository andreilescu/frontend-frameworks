import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {HashLocationStrategy, LocationStrategy} from '@angular/common';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./router/app-routing.module";

import {AppComponent} from './components/app.component';
import {ChartComponent} from './components/common/chart/chart.component';
import {PieChartComponent} from './components/common/pie-chart/pie-chart.component';
import {ListNoteComponent} from './components/list-note/list-note.component';
import {ListAccountSnapshotComponent} from './components/list-account-snapshot/list-account-snapshot.component';
import {NoteComponent} from './components/note/note.component';

import {MatCardModule} from '@angular/material/card';
import {MatExpansionModule} from '@angular/material/expansion';

@NgModule({
  declarations: [
    AppComponent,
    ListNoteComponent,
    NoteComponent,
    ListAccountSnapshotComponent,
    ChartComponent,
    PieChartComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatExpansionModule
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
