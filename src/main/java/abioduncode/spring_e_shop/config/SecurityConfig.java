package abioduncode.spring_e_shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import abioduncode.spring_e_shop.features.notAuthenticated.UserPrincipalService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserPrincipalService userPrincipalService;

  private final JwtConfig jwtConfig;

  public SecurityConfig(UserPrincipalService userPrincipalService, JwtConfig jwtConfig){
    this.userPrincipalService = userPrincipalService;
    this.jwtConfig = jwtConfig;
  }
  
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return http.csrf(Customizer -> Customizer.disable())
      .authorizeHttpRequests(request -> request
      .requestMatchers("/auth/**").permitAll()
      .requestMatchers("/api/admin/**").hasRole("ADMIN")
      .anyRequest().authenticated())
      .httpBasic(Customizer.withDefaults())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .cors(Customizer.withDefaults())
      .addFilterBefore(jwtConfig, UsernamePasswordAuthenticationFilter.class)
      .cors(Customizer.withDefaults())
      .build();
  }

  @Bean
  AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    provider.setUserDetailsService(userPrincipalService);
    return provider;
  }

  @Bean
  AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception{
    return authConfig.getAuthenticationManager();
  }
}
