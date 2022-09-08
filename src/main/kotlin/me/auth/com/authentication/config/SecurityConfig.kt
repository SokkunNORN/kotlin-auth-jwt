package me.auth.com.authentication.config

import me.auth.com.authentication.service.auth.impl.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.security.sasl.AuthenticationException


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = listOf("*")
        corsConfiguration.allowedMethods = listOf("*")
        corsConfiguration.allowedHeaders = listOf("*")
        corsConfiguration.exposedHeaders = listOf("Authorization,Link,X-Total-Count")
        corsConfiguration.allowCredentials = false
        corsConfiguration.maxAge = 1800
        source.registerCorsConfiguration("/**", corsConfiguration)

        http
            .addFilterBefore(CorsFilter(source), UsernamePasswordAuthenticationFilter::class.java)
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin()
            .disable()
            .httpBasic()
            .disable()
            .authorizeRequests{ authorize -> authorize
                .antMatchers("/api/v1/login").permitAll()
                .anyRequest().authenticated()
            }
            .sessionManagement()

        return http.build()
    }

    @Bean
    @Throws(AuthenticationException::class)
    fun userDetailService(): UserDetailsService {
        val users = User.builder()
        val manager = InMemoryUserDetailsManager()
        manager.createUser(users.username("username").password("password").roles("USER").build())

        return manager
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        return AuthenticationManager { authentication -> authentication }
    }
}

