package ru.sberTechSobes.parkingProject.aop;

import org.aspectj.lang.Aspects;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.*;

class ServiceExceptionAspectTest {

    interface TestService {
        String ok();
        String fail();
    }

    static class TestServiceImpl implements TestService {
        public String ok() { return "ok"; }
        public String fail() { throw new IllegalStateException("bad"); }
    }

    @Test
    void aspectConvertsExceptions() {
        ServiceExceptionAspect aspect = new ServiceExceptionAspect();
        ProxyFactory factory = new ProxyFactory(new TestServiceImpl());
        factory.addAspect(aspect);
        TestService proxy = (TestService) factory.getProxy();

        assertEquals("ok", proxy.ok());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, proxy::fail);
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("bad", ex.getReason());
    }
}
