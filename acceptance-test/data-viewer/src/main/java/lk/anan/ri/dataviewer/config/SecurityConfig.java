package lk.anan.ri.dataviewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, LdapAuthenticationProvider authProvider)
            throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/error", "/h2-console/**")
                .permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated())
                .formLogin(withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .authenticationProvider(authProvider)
                .httpBasic(withDefaults())
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BaseLdapPathContextSource contextSource() {
        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(
                "ldap://localhost:8389/dc=springframework,dc=org");
        contextSource.setUserDn("uid=admin,ou=people,dc=springframework,dc=org");
        contextSource.setPassword("admin");
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider(BaseLdapPathContextSource contextSource) {
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch("ou=people", "(uid={0})", contextSource);
        BindAuthenticator authenticator = new BindAuthenticator(contextSource);
        authenticator.setUserSearch(userSearch);
        authenticator.afterPropertiesSet();
        return new LdapAuthenticationProvider(authenticator);
    }

    @SuppressWarnings("unused")
    private static RequestMatcher toH2Console() {
        return new AntPathRequestMatcher("/h2-console/**");
    }
}//