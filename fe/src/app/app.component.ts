import { Component, OnInit } from '@angular/core';
import { AppServiceService } from './app-service.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'fe';

  assignment = {
    task: '',
    skill: ''
  };
  submitted = false;

  constructor(private taskService: AppServiceService) {}
  ngOnInit(): void {
  }

  saveTutorial(): void {
    const data = {
      task: this.assignment.task,
      skill: this.assignment.skill
    };

    this.taskService.create(data)
      .subscribe(
        response => {
          console.log(response);
          this.submitted = true;
        },
        error => {
          console.log(error);
        });
  }

  newTask(): void {
    this.submitted = false;
    this.assignment = {
      task: '',
      skill: ''
    };
  }
}
