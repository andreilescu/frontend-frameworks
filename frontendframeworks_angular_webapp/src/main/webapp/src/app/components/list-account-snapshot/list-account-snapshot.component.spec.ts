import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAccountSnapshotComponent } from './list-account-snapshot.component';

describe('ListAccountSnapshotComponent', () => {
  let component: ListAccountSnapshotComponent;
  let fixture: ComponentFixture<ListAccountSnapshotComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListAccountSnapshotComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListAccountSnapshotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
