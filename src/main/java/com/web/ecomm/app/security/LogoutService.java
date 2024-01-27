package com.web.ecomm.app.security;

import com.web.ecomm.app.token.Token;
import com.web.ecomm.app.token.TokenRepository;
import com.web.ecomm.app.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            @NonNull HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(Constants.TOKEN_HEADER);
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith(Constants.TOKEN_PREFIX)) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            Token token = tokenRepository.save(storedToken);
//            tokenRepository.delete(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}