package com.bless.config;

import com.bless.Security.JwtAuthenticationEntryPoint;
import com.bless.Filter.JwtAuthorizationTokenFilter;
import com.bless.Service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by wangxi on 18/8/8.
 */
@Configuration
//@ComponentScan({"com.bless.Security"}) //可以解决无法使用Autowired的问题
// 原因是 我们手动的在 @Configuration 注解下面添加 @ComponentScan 注解并指定所需model类的包地址就可以解决整个问题了。
//原因估计是因为在项目的启动的最初阶段，IDE 还没有扫描到model类，无法发现对应的 bean ，于是就需要我们手动的给其指定需要扫描的包了
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,jsr250Enabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.header}")
    private String tokenHeader;

//    @Autowired
//    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    protected JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter(){
        return new JwtAuthorizationTokenFilter(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                //统一异常处理
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint()).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/search/**").permitAll()
                .antMatchers("/webSocket/**").permitAll()
                .antMatchers("/socket.io/**").permitAll()


                //swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(jwtAuthorizationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity
                .headers()
                .cacheControl();
    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//       auth.inMemoryAuthentication()
//               .passwordEncoder(new BCryptPasswordEncoder())
//               .withUser("root")
//               .password(new BCryptPasswordEncoder().encode("root"))
//               .roles("USER")
//            .and()
//               .withUser("admin")
//               .password(new BCryptPasswordEncoder().encode("admin"))
//               .roles("ADMIN", "USER");
        //AuthenticationManager使用我们的 MyUserDetailService 来获取用户信息

        auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return null;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (rawPassword.equals(encodedPassword)){
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {//忽略静态库
        web
                .ignoring()
                .antMatchers("/resources/**")
        ;
    }

    //覆盖写userDetailsService方法 (1)
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
//        return super.userDetailsService();
        return new MyUserDetailService();
    }

}
