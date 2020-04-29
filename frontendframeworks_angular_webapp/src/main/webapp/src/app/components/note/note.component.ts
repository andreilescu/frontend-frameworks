import {Component, OnInit} from '@angular/core';
import {NoteService} from "../../services/note.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'note',
    templateUrl: './note.component.html',
    styleUrls: ['./note.component.css'],
    providers: [NoteService]
})
export class NoteComponent implements OnInit {

    note = {title: '', description: ''};
    private noteId: string;

    constructor(private noteService: NoteService,
                private router: Router,
                private route: ActivatedRoute) {

        route.params.subscribe(p => {
            this.noteId = p['id'];
        });
    }

    navigateBack() {
        this.router.navigate(['/notes']);
    }

    getNote() {
        this.noteService.getNote(this.noteId)
            .then(result => this.note = result);
    }

    editNote() {
        this.noteService.updateNote(this.note);
    }

    ngOnInit() {
        this.getNote();
    }

}
