import { TestBed } from '@angular/core/testing';

import { AccountSnapshotService } from './account-snapshot.service';

describe('AccountSnapshotService', () => {
  let service: AccountSnapshotService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountSnapshotService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
