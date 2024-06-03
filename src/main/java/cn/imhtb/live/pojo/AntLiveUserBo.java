package cn.imhtb.live.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @author PinTeh
 * @since 2020/5/7
 */
@Setter
@Getter
@NoArgsConstructor
public class AntLiveUserBo extends User implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private BigDecimal balance;
    private List<Integer> roleIds;
    private Collection<? extends GrantedAuthority> authorities;

    public AntLiveUserBo(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
