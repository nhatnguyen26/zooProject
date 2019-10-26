package com.zoo.zooApplication.validator;

import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.CourtUserRoleDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.dao.repository.CourtUserRoleRepository;
import com.zoo.zooApplication.exception.ForbiddenAccessException;
import com.zoo.zooApplication.exception.InvalidRequestException;
import com.zoo.zooApplication.firebaseadaptor.IFirebaseAuth;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.type.CourtRoleEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CourtManagementValidatorTest {

	@Mock
	private CourtUserRoleRepository courtUserRoleRepository;

	@InjectMocks
	private CourtManagementValidator validator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testValidateCreateFieldRequestNoCourt() {
		try {
			validator.validateCreateFieldRequest(Optional.empty(), null);
			fail();
		} catch (InvalidRequestException ire) {
			assertEquals(1000, ire.getErrorId());
		}
	}

	@Test
	public void testValidateCreateFieldRequestUnauthorized() {
		try {
			CreateFieldRequest request = new CreateFieldRequest();
			IFirebaseAuth auth = mock(IFirebaseAuth.class);
			when(auth.getUid()).thenReturn("123");
			request.setFirebaseAuth(auth);
			CourtDO courtDO = mock(CourtDO.class);
			when(courtDO.getId()).thenReturn(1L);
			List<CourtUserRoleDO> courtRoleList = new ArrayList<>();
			CourtUserRoleDO courtOwner = mock(CourtUserRoleDO.class);
			when(courtOwner.getCourtId()).thenReturn(1L);
			when(courtOwner.getCourtRole()).thenReturn(CourtRoleEnum.ADMIN);
			courtRoleList.add(courtOwner);
			when(courtUserRoleRepository.findByUid("123")).thenReturn(courtRoleList);
			validator.validateCreateFieldRequest(Optional.of(courtDO), request);
			fail();
		} catch (ForbiddenAccessException fae) {
			assertEquals(1001, fae.getErrorId());
		}
	}

	@Test
	public void testValidateCreateFieldRequestUnauthorizedNoRole() {
		try {
			CreateFieldRequest request = new CreateFieldRequest();
			IFirebaseAuth auth = mock(IFirebaseAuth.class);
			when(auth.getUid()).thenReturn("123");
			request.setFirebaseAuth(auth);
			CourtDO courtDO = mock(CourtDO.class);
			when(courtDO.getId()).thenReturn(1L);
			List<CourtUserRoleDO> courtRoleList = new ArrayList<>();
			when(courtUserRoleRepository.findByUid("123")).thenReturn(courtRoleList);
			validator.validateCreateFieldRequest(Optional.of(courtDO), request);
			fail();
		} catch (ForbiddenAccessException fae) {
			assertEquals(1001, fae.getErrorId());
		}
	}

	@Test
	public void testValidateCreateFieldInvalidSubFieldNumber() {
		try {
			CreateFieldRequest request = new CreateFieldRequest();
			IFirebaseAuth auth = mock(IFirebaseAuth.class);
			when(auth.getUid()).thenReturn("123");
			request.setFirebaseAuth(auth);
			CourtDO courtDO = mock(CourtDO.class);
			when(courtDO.getId()).thenReturn(1L);
			List<CourtUserRoleDO> courtRoleList = new ArrayList<>();
			CourtUserRoleDO courtOwner = mock(CourtUserRoleDO.class);
			when(courtOwner.getCourtId()).thenReturn(1L);
			when(courtOwner.getCourtRole()).thenReturn(CourtRoleEnum.OWNER);
			courtRoleList.add(courtOwner);
			when(courtUserRoleRepository.findByUid("123")).thenReturn(courtRoleList);

			List<FieldRequest> fieldRequestList = new ArrayList<>();
			FieldRequest fieldRequest = new FieldRequest();
			fieldRequest.setSubFieldIds(List.of(1L));
			fieldRequestList.add(fieldRequest);
			request.setFieldRequests(fieldRequestList);
			validator.validateCreateFieldRequest(Optional.of(courtDO), request);
			fail();
		} catch (InvalidRequestException ire) {
			assertEquals(1002, ire.getErrorId());
		}
	}

	@Test
	public void testValidateCreateFieldInvalidSubFieldNumberInvalid() {
		try {
			CourtDO courtDO = mock(CourtDO.class);
			when(courtDO.getId()).thenReturn(1L);
			FieldDO field1 = mock(FieldDO.class);
			when(field1.getId()).thenReturn(1L);
			FieldDO field2= mock(FieldDO.class);
			when(field2.getId()).thenReturn(2L);
			when(courtDO.getFields()).thenReturn(Set.of(field1, field2));

			CreateFieldRequest request = new CreateFieldRequest();
			IFirebaseAuth auth = mock(IFirebaseAuth.class);
			when(auth.getUid()).thenReturn("123");
			request.setFirebaseAuth(auth);

			List<CourtUserRoleDO> courtRoleList = new ArrayList<>();
			CourtUserRoleDO courtOwner = mock(CourtUserRoleDO.class);
			when(courtOwner.getCourtId()).thenReturn(1L);
			when(courtOwner.getCourtRole()).thenReturn(CourtRoleEnum.OWNER);
			courtRoleList.add(courtOwner);
			when(courtUserRoleRepository.findByUid("123")).thenReturn(courtRoleList);

			FieldRequest fieldRequest = new FieldRequest();
			fieldRequest.setSubFieldIds(List.of(3L, 4L));
			request.setFieldRequests(List.of(fieldRequest));
			validator.validateCreateFieldRequest(Optional.of(courtDO), request);
			fail();
		} catch (InvalidRequestException ire) {
			assertEquals(1003, ire.getErrorId());
		}
	}

	@Test
	public void testValidateCreateFieldNormal() {
		CourtDO courtDO = mock(CourtDO.class);
		when(courtDO.getId()).thenReturn(1L);
		FieldDO field1 = mock(FieldDO.class);
		when(field1.getId()).thenReturn(3L);
		FieldDO field2= mock(FieldDO.class);
		when(field2.getId()).thenReturn(4L);
		when(courtDO.getFields()).thenReturn(Set.of(field1, field2));

		CreateFieldRequest request = new CreateFieldRequest();
		IFirebaseAuth auth = mock(IFirebaseAuth.class);
		when(auth.getUid()).thenReturn("123");
		request.setFirebaseAuth(auth);

		List<CourtUserRoleDO> courtRoleList = new ArrayList<>();
		CourtUserRoleDO courtOwner = mock(CourtUserRoleDO.class);
		when(courtOwner.getCourtId()).thenReturn(1L);
		when(courtOwner.getCourtRole()).thenReturn(CourtRoleEnum.OWNER);
		courtRoleList.add(courtOwner);
		when(courtUserRoleRepository.findByUid("123")).thenReturn(courtRoleList);

		FieldRequest fieldRequest = new FieldRequest();
		fieldRequest.setSubFieldIds(List.of(3L, 4L));
		request.setFieldRequests(List.of(fieldRequest));
		validator.validateCreateFieldRequest(Optional.of(courtDO), request);
	}
}