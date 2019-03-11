## Example

    class SecurityConfig extends WebSecurityConfigurerAdapter {
        
        @Resource UserDetailsService userDetailsService; 
        @Resource AuthService authService;
        
        @Bean JwtConfig jwtConfig() {
            // token超时设置等  
            return new JwtConfig();
        }
        
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean JwtAuthenticationTokenFilter authenticationTokenFilter() {
            return new JwtAuthenticationTokenFilter(jwtConfig(), userDetailsService, authService);
        }

        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        protected void configure(HttpSecurity http) throws Exception {
    
            http.csrf().disable().cors()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().authorizeRequests()

                    .anyRequest().authenticated()
                    .and().addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        }
    }
