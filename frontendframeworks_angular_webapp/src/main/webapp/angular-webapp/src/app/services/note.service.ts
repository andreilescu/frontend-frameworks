import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class NoteService {

    HOST = "http://localhost";
    PORT = "8081";
    PROXY = this.HOST + ":" + this.PORT;
    NOTES = "/notes";
    url = this.PROXY + this.NOTES;

    constructor(private httpClient: HttpClient) {
    }

    public getNotes(): Promise<any> {
        return this.httpClient.get(this.url)
            .toPromise()
            .then(response => {
                return response;
            });
    }


    public getNote(noteId): Promise<any> {
        return this.httpClient.get(this.url + '/' + noteId)
            .toPromise()
            .then(result => result);
    }

    public addNote(note): Promise<any> {
        return this.httpClient.post(this.url, note)
            .toPromise()
            .then(response => response);
    }

    public deleteNote(noteId): Promise<any> {
        return this.httpClient.delete(this.url + '/' + noteId)
            .toPromise()
            .then(response => response);
    }

    public updateNote(note) {
        return this.httpClient.put(this.url + '/' + note.id, note)
            .toPromise()
            .then(result => result);
    }
}
