package io.jungmini.sample.domain.auth.model.entity;

import io.jungmini.sample.domain.auth.model.OAuthProvider;
import io.jungmini.sample.domain.user.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
	name = "oauth",      // provider, providerId 는 유일 해야 함
	uniqueConstraints = @UniqueConstraint(name = "uk_provider_providerid", columnNames = {"provider", "providerId"}))
public class Oauth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OAuthProvider provider;

	@Column(nullable = false)
	private String providerId; // 넘버 아니게 주는 곳 있음

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Oauth(OAuthProvider provider, String providerId) {
		this.provider = provider;
		this.providerId = providerId;
	}

	public void setUser(User user) {
		this.user = user;
	}
}