package se.iths.auktionera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class AuktioneraApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuktioneraApplication.class, args);

    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//
//    @Configuration
//    @EnableWebSecurity
//    public class DemoConfigurer extends WebSecurityConfigurerAdapter {
//        private AuthenticationEntryPoint restAuthenticationEntryPoint;
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.inMemoryAuthentication()
//                  //  .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
//                   // .and()
//                    .withUser("user").password(encoder().encode("userPass")).roles("USER");
//        }
//
//        @Bean
//        public PasswordEncoder encoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(restAuthenticationEntryPoint)
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/api/account").authenticated()
//                   //.antMatchers("/api/admin/**").hasRole("ADMIN")
//                    .and()
//                    .formLogin()
////                    .successHandler(mySuccessHandler)
////                    .failureHandler(myFailureHandler)
//                    .and()
//                    .logout();
//        }
////        @Override
////        public void configure(HttpSecurity http) throws Exception {
////            http.authorizeRequests().antMatchers("/version").permitAll();
////            super.configure(http);
////        }
//    }



}
