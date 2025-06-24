package FitStore.TpoGrupo10.unit.utils;

import FitStore.TpoGrupo10.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secretKeyRaw", "EstaEsUnaClaveSecretaJwtDeAlMenos32Caracteres!");
        jwtUtil.init();
    }

    @Test
    void generateToken_deberiaRetornarTokenValido() {
        String token = jwtUtil.generateToken("agus", 123L);

        assertNotNull(token);
        assertTrue(token.length() > 20); // Largo estimado
    }

    @Test
    void extractUsername_conTokenValido_devuelveUsername() {
        String token = jwtUtil.generateToken("agus", 123L);

        String username = jwtUtil.extractUsername(token);

        assertEquals("agus", username);
    }

    @Test
    void extractUserId_conTokenValido_devuelveId() {
        String token = jwtUtil.generateToken("agus", 123L);

        Long id = jwtUtil.extractUserId(token);

        assertEquals(123L, id);
    }

    @Test
    void extractUsername_conTokenInvalido_devuelveNull() {
        String tokenInvalido = "esto.no.es.un.token";

        String result = jwtUtil.extractUsername(tokenInvalido);

        assertNull(result);
    }

    @Test
    void isTokenValid_conTokenValido_devuelveTrue() {
        String token = jwtUtil.generateToken("agus", 123L);

        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    void isTokenValid_conTokenInvalido_devuelveFalse() {
        String tokenInvalido = "abc.def.ghi";

        assertFalse(jwtUtil.isTokenValid(tokenInvalido));
    }
}