package common.security;

// 生成凭证, 验证凭证
public interface AuthorizationService {

    /**
     * 解析用户名
     *
     * @param accessToken 访问令牌
     * @return 无效时返回null
     */
    String parseUsername(String accessToken);

    /**
     * 令牌是否有效
     *
     * @param accessToken 访问令牌
     * @return 是否有效
     */
    boolean validate(String accessToken);
}
