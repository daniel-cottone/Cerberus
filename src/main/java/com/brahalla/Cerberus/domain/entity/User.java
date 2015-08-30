package com.brahalla.Cerberus.domain.entity;

import com.brahalla.Cerberus.domain.base.DomainBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends DomainBase {

  private static final long serialVersionUID = 2353528370345499815L;
  private Long id;
  private String username;
  private String password;
  private String role;

  public User() {
    super();
  }

  public User(String username, String password, String role) {
    this.setUsername(username);
    this.setPassword(password);
    this.setRole(role);
  }

  @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
	@SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "username")
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(name = "password")
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "role")
  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
