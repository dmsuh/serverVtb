//package dev.fixiki.hahaton.configuration
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
//import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
//import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
//import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.web.SecurityFilterChain
//
//
//@Configuration
//@EnableWebSecurity
//class WebSecurityConfig {
//    @Bean @Throws(Exception::class)
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
//        http
//            .authorizeHttpRequests { requests ->
//                requests
//                    .requestMatchers("/api", "/admin").permitAll()
//                    .anyRequest().anonymous()
//                    // TODO
//            }
////            .formLogin { form: FormLoginConfigurer<HttpSecurity?> ->
////                form
////                    .loginPage("/login")
////                    .permitAll()
////            }
////            .logout { logout: LogoutConfigurer<HttpSecurity?> -> logout.permitAll() }
//        return http.build()
//    }
//
//}