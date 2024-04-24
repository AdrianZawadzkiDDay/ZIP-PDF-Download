import { Component } from '@angular/core';
import { DownloadService } from '../service/download.service'; 

@Component({
  selector: 'app-pdf',
  templateUrl: './pdf.component.html',
  styleUrls: ['./pdf.component.scss']
})
export class PdfComponent {

  constructor(private downloadService: DownloadService) { }
  
  ngOnInit(): void {
  }

  downloadPdf(): void {
    console.log("WORKS")
    this.downloadService.downloadZip();
  }
}
