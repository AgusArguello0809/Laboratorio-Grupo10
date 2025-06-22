package FitStore.TpoGrupo10.unit.security;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import FitStore.TpoGrupo10.security.CustomUserDetailsService;
import FitStore.TpoGrupo10.security.JwtRequestFilter;
import FitStore.TpoGrupo10.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtRequestFilterTest {

    static class TestableJwtRequestFilter extends JwtRequestFilter {
        public TestableJwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
            super(jwtUtil, userDetailsService);
        }
        public void callDoFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
            super.doFilterInternal(request, response, chain);
        }
    }

    @InjectMocks
    private TestableJwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter = new TestableJwtRequestFilter(jwtUtil, userDetailsService);
    }

    @Test
    void doFilterInternal_conTokenValido_autenticaYContinuaCadena() throws Exception {
        String token = "valid.token.jwt";
        String username = "agus";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        jwtRequestFilter.callDoFilterInternal(request, response, filterChain);

        verify(jwtUtil).extractUsername(token);
        verify(userDetailsService).loadUserByUsername(username);
        verify(jwtUtil).isTokenValid(token);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_sinAuthorizationHeader_noHaceNada() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.callDoFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtUtil);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilterInternal_conTokenInvalido_noAutentica() throws Exception {
        String token = "invalid.token.jwt";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn("agus");
        when(jwtUtil.isTokenValid(token)).thenReturn(false);

        jwtRequestFilter.callDoFilterInternal(request, response, filterChain);

        verify(jwtUtil).extractUsername(token);
        verify(jwtUtil).isTokenValid(token);
        verify(filterChain).doFilter(request, response);
    }
}
