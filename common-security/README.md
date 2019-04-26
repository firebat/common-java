## Usage

    class AppSecurityConfig extends WebSecurityConfigurerAdapter {
        
        @Resource UserDetailsService userDetailsService; 
        @Resource AuthService authService;
        
        @Bean SecurityConfig securityConfig() {
            SecurityConfig config = new SecurityConfig();
            // config.setSignature("xxxx");
            return config;
        }
        
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean JwtAuthorizationTokenFilter authorizationTokenFilter() {
            return new JwtAuthorizationTokenFilter(securityConfig(), userDetailsService, authService);
        }

        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        protected void configure(HttpSecurity http) throws Exception {
    
            http.csrf().disable().cors()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().authorizeRequests()

                    .anyRequest().authenticated()
                    .and().addFilterBefore(authorizationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        }
    }
