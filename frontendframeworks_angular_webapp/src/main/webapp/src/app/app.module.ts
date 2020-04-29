import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

import {AppComponent} from './components/app.component';
import {ListNoteComponent} from './components/list-note/list-note.component';
import {NoteComponent} from './components/note/note.component';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./router/app-routing.module";

@NgModule({
  declarations: [
    AppComponent,
    ListNoteComponent,
    NoteComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
