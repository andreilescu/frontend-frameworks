import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";

import {ListNoteComponent} from "../components/list-note/list-note.component";
import {NoteComponent} from "../components/note/note.component";


const routes: Routes = [
  {
    path: 'notes',
    component: ListNoteComponent
  },
  {
    path: 'notes/:id',
    component: NoteComponent
  },
  {
    path: '',
    redirectTo: '/notes',
    pathMatch: 'full'
  }
];


@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes,
      {
        enableTracing: true
      })
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
