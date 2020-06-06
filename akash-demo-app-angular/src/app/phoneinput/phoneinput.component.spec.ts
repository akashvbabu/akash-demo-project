import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhoneinputComponent } from './phoneinput.component';

describe('PhoneinputComponent', () => {
  let component: PhoneinputComponent;
  let fixture: ComponentFixture<PhoneinputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhoneinputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhoneinputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
