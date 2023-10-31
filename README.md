# spring-boot-starter-passwordless

Integrated the [Bitwarden Passwordless.dev](https://bitwarden.com/products/passwordless/) into a Spring boot request filter which can be plugged into any Springboot application as a component. The filter can be enabled for any critical services which needs a passkey based authentication in the application configuration as shown below:

```
@Bean
public FilterRegistrationBean<PasswordlessAuthenticationFilter> filterRegistrationBean() {

    FilterRegistrationBean<PasswordlessAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new PasswordlessAuthenticationFilter(passwordlessClient));

    // Specify the URL patterns to which the filter should be applied
    registrationBean.addUrlPatterns("/users/login");

    return registrationBean;
}
```

