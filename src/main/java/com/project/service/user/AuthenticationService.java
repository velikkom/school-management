package com.project.service.user;

import com.project.entity.concretes.user.User;
import com.project.exception.BadRequestexception;
import com.project.payload.authentication.LoginRequest;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.business.UpdatePasswordRequest;
import com.project.payload.response.authentication.AuthResponse;
import com.project.repository.user.UserRepository;
import com.project.security.jwt.JwtUtils;
import com.project.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public final JwtUtils jwtUtils;
    public final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
        // requestten gelen bilgileri username ve password değişkenlerime atadım
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        //AuthenticationManager üzerinden kullanıcıyı valide edcez
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        //valide edilen kullanıcı contexte atılıyor
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //jwt token oluşturuluyor
        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

        //login işlemi gercekleştirilen kullanıcıya ulaşılıyor
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //response olarak login işlemi yapan kullanıcıyı donecegiz gerekli fieldlar setleniyor
        //Grand Authority turundeki role yapısını string turune cevriliyor

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        //bir kullanıcının birden fazla rolu olmayacagı için ilk indekli elemanı arıyoruz
        Optional<String> role = roles.stream().findFirst();
        //burada login işlemini gercekleştiren kullanıcı bilgileri responseolaraj gondereceğimiz için gerekli bilgiler setleniyor
        AuthResponse.AuthResponseBuilder authResponse
                = AuthResponse.builder();

        authResponse.username(userDetails.getUsername());
        authResponse.token(token.substring(7));
        authResponse.name(userDetails.getName());
        authResponse.ssn(userDetails.getSsn());

        //rolebilgisi  varsa response nesnesindeki değişkene setleniyor
        role.ifPresent(authResponse::role);

        return ResponseEntity.ok(authResponse.build());

    }
//TODO 23.51
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest, HttpServletRequest request)
    {
        String userName = (String) request.getAttribute("username");
        User user = userRepository.findByUsername(userName);
        // !!! Builtin attribute: Datalarının Değişmesi istenmeyen bir objenin builtIn değeri true olur.
        if(Boolean.TRUE.equals(user.getBuilt_in())) { // null değerleriyle çalışırken güvenli bir yöntemdir. Boolean.TRUE.equals
            throw new BadRequestexception(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        // !!! Eski sifre bilgisi dogrumu kontrolu
        if(!passwordEncoder.matches(updatePasswordRequest.getOldPassword(),user.getPassword())) {
            throw new BadRequestexception(ErrorMessages.PASSWORD_NOT_MATCHED);
        }
        // !!! yeni sifre hashlenerek Kaydediliyor
        String hashedPassword=  passwordEncoder.encode(updatePasswordRequest.getNewPassword());

        user.setPassword(hashedPassword);
        userRepository.save(user);
    }


}