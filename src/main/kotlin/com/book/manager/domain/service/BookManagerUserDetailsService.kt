package com.book.manager.domain.service

import com.book.manager.data.repository.User
import com.book.manager.domain.enum.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class BookManagerUserDetailsService(
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String?) =
        authenticationService.findUser(username ?: "")
            ?.let { BookManagerUserDetails(it) }
            ?: throw UsernameNotFoundException("User not found")
}

data class BookManagerUserDetails(
    val id: Long,
    val email: String,
    val pass: String,
    val roleType: RoleType
) : UserDetails {
    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        pass = user.password,
        roleType = user.roleType
    )

    // 権限を取得する
    // 認可を必要とするリクエストに対して権限があるか判定される
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(this.roleType.toString())
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    // パスワードを取得する
    // 入力パスワードと照合される
    override fun getPassword(): String {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}