package nl.abnamro.recipebook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}wordPass").roles("USER")
                .and()
                .withUser("admin").password("{noop}wordPass").roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()

                .antMatchers(HttpMethod.GET, "/allRecipes").authenticated()
                .antMatchers(HttpMethod.POST, "/createRecipe").authenticated()
                .antMatchers(HttpMethod.DELETE, "/deleteRecipe/{name}").authenticated()
                .and()
                .formLogin().defaultSuccessUrl("/dashboard.html", true)
                .and()
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrf().disable()
              // .and()
                .headers().frameOptions().sameOrigin()
        ;

    }

}