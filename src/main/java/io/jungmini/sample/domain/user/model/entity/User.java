package io.jungmini.sample.domain.user.model.entity;

import java.util.ArrayList;
import java.util.List;

import io.jungmini.sample.domain.auth.model.Role;
import io.jungmini.sample.domain.auth.model.entity.Oauth;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = true) // 소셜로 처음 가입 한 사람은 없을 수도 있음
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Oauth> oauthAccounts = new ArrayList<>();

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public void addOauth(Oauth oauth) {
		oauth.setUser(this);
		this.oauthAccounts.add(oauth);
	}
}