import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";

import {ListNoteComponent} from "../components/list-note/list-note.component";
import {NoteComponent} from "../components/note/note.component";
import {ListAccountSnapshotComponent} from "../components/list-account-snapshot/list-account-snapshot.component";


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
        path: 'accountSnapshots',
        component: ListAccountSnapshotComponent
    },
    {
        path: '',
        redirectTo: 'accountSnapshots',
        pathMatch: 'full'
    }
];


@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        RouterModule.forRoot(routes,
            {
                enableTracing: false
            })
    ],
    exports: [
        RouterModule
    ]
})
export class AppRoutingModule {
}
