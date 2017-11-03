package py.org.fundacionparaguaya.pspserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import py.org.fundacionparaguaya.pspserver.common.dtos.ErrorDTO;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
     	 auth.jdbcAuthentication().dataSource(dataSource)
    	  .usersByUsernameQuery(
    	   "select username, pass as password, active as enabled from security.user where username=?")
    	  .authoritiesByUsernameQuery(
    	   "select b.username, a.role from security.user_x_role a join security.user b on b.user_id = a.user_id where b.username = ?");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll()
                .antMatchers("/swagger/**", "/swagger.json").permitAll()
                .antMatchers("/oauth/token/revokeById/**").permitAll()
		        .antMatchers("/tokens/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and().csrf().disable();
    }

    /**
     * Maneja los errores de autenticación.
     * <p>
     * Y retorna un json con información del error, con
     * estados 401.
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            ErrorDTO errorDTO = ErrorDTO.fromCode("forbidden", authException.getMessage());

            ObjectMapper mapper = new ObjectMapper();

            response.setHeader("Content-type", "application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            mapper.writeValue(out, errorDTO);
        };

    }

}