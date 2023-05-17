package com.harriet.shopiify.auth.model;
import com.harriet.shopiify.cart.model.Cart;
import com.harriet.shopiify.order.model.Order;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="shopiify_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(unique = true, name = "email")
    private String email;

//    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.LAZY)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
    // TODO: added enum validation
    @Column(name = "role")
    private String role;

//    @OneToOne
//    @JoinColumn(name="cart_id")
//    private Cart cart;

    // TODO check if should remove this association to make the association unidirection instead
//    @OneToMany(mappedBy="user")
//    private List<Order> orders = new ArrayList<>();

    public User() {
    }
    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
//        this.cart = cart;
//        this.orders = orders;
    }

    public Long getId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public Cart getCart() {
//        return cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }



}

