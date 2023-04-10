import { TestBed } from '@angular/core/testing';

import { AuthenticaionServicesService } from './authenticaion-services.service';

describe('AuthenticaionServicesService', () => {
  let service: AuthenticaionServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthenticaionServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
