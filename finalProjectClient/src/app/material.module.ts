import { NgModule } from "@angular/core";
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBarModule} from '@angular/material/snack-bar';







const matModules: any [] = [
    MatToolbarModule, MatIconModule, MatButtonModule,
    MatSnackBarModule

]

@NgModule({
    imports: matModules,
    exports: matModules
})

export class MaterialModule {}