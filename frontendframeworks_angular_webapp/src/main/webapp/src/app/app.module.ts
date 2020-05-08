import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { HashLocationStrategy, LocationStrategy } from '@angular/common';

import {AppComponent} from './components/app.component';
import {ListNoteComponent} from './components/list-note/list-note.component';
import {NoteComponent} from './components/note/note.component';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./router/app-routing.module";
import {ListAccountSnapshotComponent} from './components/list-account-snapshot/list-account-snapshot.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';

@NgModule({
  declarations: [
    AppComponent,
    ListNoteComponent,
    NoteComponent,
    ListAccountSnapshotComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
