import { NgModule } from "@angular/core";
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';









const matModules: any [] = [
    MatToolbarModule, MatIconModule, MatButtonModule,
    MatSnackBarModule, MatCardModule, MatInputModule

]

@NgModule({
    imports: matModules,
    exports: matModules
})

export class MaterialModule {
  
}