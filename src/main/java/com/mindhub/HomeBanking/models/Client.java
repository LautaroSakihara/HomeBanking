package com.mindhub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity//relaciona lo que crees abajo se va a generar en la base de datos como una tabla,
//por cada propiedad se hace una columna en la base de datos
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName,lastName,password,email;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER) //Indica que la relación debe de ser cargada al momento de cargar la entidad.
    //LAZY indica que la relación solo se cargará cuando la propiedad sea leída por primera vez.

    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)

    private Set<ClientLoan> loans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)

    private Set<Card> cards = new HashSet<>();

    //sobrecarga de metodos, constructor vacio y el constructor con datos
    public Client(){}

    public Client(String first, String last, String email,String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Set<Loan> getLoans() {
        return loans.stream().map(clientLoan -> clientLoan.getLoan()).collect(toSet());
    }
    public Set<ClientLoan> getClientLoan() {
        return loans;
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.loans = loans;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
