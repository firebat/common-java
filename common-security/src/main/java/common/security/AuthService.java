package common.security;

import org.springframework.security.core.userdetails.UserDetails;

// 生成凭证, 验证凭证
public interface AuthService {

    String generateToken(UserDetails details);

    String parseUsername(String token);

    boolean validate(String token);
}
