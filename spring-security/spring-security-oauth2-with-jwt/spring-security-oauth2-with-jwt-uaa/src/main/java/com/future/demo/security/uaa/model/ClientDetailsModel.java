package com.future.demo.security.uaa.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
@TableName(value = "oauth2_client_details")
public class ClientDetailsModel implements ClientDetails {
    @TableId(value = "id")
    private Integer id;
    private String clientId;
    private String clientSecret;
    private String resourceIds;
    private String scopes;
    private String authorizedGrantTypes;
    private String redirectUris;
    private String authorities;
    private Date createTime;

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        Set<String> setReturn = new HashSet<>();
        if(StringUtils.isEmpty(this.scopes)) {
            return setReturn;
        }
        return Arrays.stream(this.scopes.split(",")).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        Set<String> setReturn = new HashSet<>();
        if(StringUtils.isEmpty(this.redirectUris)) {
            return setReturn;
        }
        return Arrays.stream(this.redirectUris.split(",")).collect(Collectors.toSet());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<String> setTemporary = new HashSet<>();
        if(!StringUtils.isEmpty(this.authorities)) {
            setTemporary = Arrays.stream(this.authorities.split(",")).collect(Collectors.toSet());
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(!CollectionUtils.isEmpty(setTemporary)) {
            setTemporary.forEach((authority)->{
                grantedAuthorities.add(new SimpleGrantedAuthority(authority));
            });
        }
        return grantedAuthorities;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> setReturn = new HashSet<>();
        if(StringUtils.isEmpty(this.authorizedGrantTypes)) {
            return setReturn;
        }
        return Arrays.stream(this.authorizedGrantTypes.split(",")).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> setReturn = new HashSet<>();
        if(StringUtils.isEmpty(this.resourceIds)) {
            return setReturn;
        }
        return Arrays.stream(this.resourceIds.split(",")).collect(Collectors.toSet());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 3600;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 3600;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
