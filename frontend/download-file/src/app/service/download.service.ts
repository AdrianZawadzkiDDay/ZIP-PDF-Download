import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { saveAs } from 'file-saver';
@Injectable({
  providedIn: 'root'
})
export class DownloadService {

  private pdfUrl = 'http://localhost:8080/app/pdf';
  constructor(private http: HttpClient) { }

  downloadPdf(): void {
    this.http.get(this.pdfUrl, { responseType: 'blob' }).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const fileName = 'example.pdf';
      saveAs(blob, fileName);
    }, error => {
      console.error('Błąd podczas pobierania pliku PDF:', error);
    });
  }

  private zipUrl = 'http://localhost:8080/app/pdf/zip';

  downloadZip(): void {
    this.http.get(this.zipUrl, { responseType: 'blob' }).subscribe(response => {
      const blob = new Blob([response], { type: 'application/zip' });
      const fileName = 'example.zip';
      saveAs(blob, fileName);
    }, error => {
      console.error('Błąd podczas pobierania pliku ZIP:', error);
    });
  }

}
