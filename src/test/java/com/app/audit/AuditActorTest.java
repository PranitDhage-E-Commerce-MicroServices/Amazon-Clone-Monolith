//package com.app.audit;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(MockitoJUnitRunner.class)
//class AuditActorTest {
//
//    AuditActor auditActor;
//
//    @Before
//    public void setUp() throws Exception {
//        auditActor = new AuditActor("12344");
//    }
//
//    @Test
//    public final void testAuditActor() {
//        assertNotNull(auditActor.toString());
//    }
//
//    @Test
//    public final void testAuditActorGet() {
//        assertNotNull(auditActor.getLoggedInUser());
//    }
//
//}