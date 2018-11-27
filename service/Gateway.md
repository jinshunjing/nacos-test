# 开放平台

## 授权
* Spring Security OAuth
* servlet filters
* authorization server
* resource server
* grant_type: authorization_code, client_credentials, password, implicit

### authorization server
* client details: JDBC
* token store: Redis
* password encoder: BCrypt, SCrypt

### resource server
* authorize requests ant matchers
* scope
* authorities

### authorization_code
* auth_code
* access_token
* refresh_token

### 踩过的坑
* filter order: security.oauth2.resource.filter-order=3
* form based authentication: cURL POST command -F 'param=val'
