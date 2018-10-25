import {Component, OnInit} from '@angular/core';
import {Location, LocationStrategy, PathLocationStrategy} from '@angular/common';
import {NoteService} from "../../services/note.service";
import {Router} from "@angular/router";

@Component({
    selector: 'list-note',
    templateUrl: './list-note.component.html',
    styleUrls: ['./list-note.component.css'],
    providers: [
        Location,
        {
            provide: LocationStrategy,
            useClass: PathLocationStrategy
        }]
})
export class ListNoteComponent implements OnInit {

    note = {};
    notes = [];

    constructor(private noteService: NoteService,
                private router: Router) {
    }

    getNotes() {
        this.noteService.getNotes().then(result => this.notes = result);
    }

    addNote(note) {
        this.noteService.addNote(note)
            .then(() => {
                    this.resetNote();
                    this.getNotes();
                }
            );
    }

    deleteNote(noteId) {
        this.noteService.deleteNote(noteId)
            .then(() => {
                this.getNotes();
            });
    }

    navigateToNote(noteId) {
        this.router.navigate(['notes', noteId]);
    }

    private resetNote() {
        this.note = {};
    }

    ngOnInit() {
        this.getNotes();
    }

}
