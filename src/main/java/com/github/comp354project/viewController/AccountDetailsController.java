package com.github.comp354project.viewController;

import com.github.comp354project.MyMoneyApplication;
import com.github.comp354project.model.account.Account;
import com.github.comp354project.model.account.Transaction;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AccountDetailsController implements Initializable {
    @FXML
    private Parent transactionTableView;
    @FXML
    private TransactionTableController transactionTableViewController;
    @FXML
    private Label accountBalance;
    @FXML
    private Label accountDescription;
    @FXML
    private Label accountType;

    @FXML
    private TextField categoryTextField;

    @Getter
    private Account account;

    public void initialize(URL url, ResourceBundle rb) {
        this.categoryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.transactionTableViewController.setCategoryFilter(newValue);
        });
    }

    @FXML
    public void gotoAccountList() {
        MyMoneyApplication.application.displayAccounts();
    }

    public void setAccount(Account account) {
        this.account = account;

        List<Transaction> transactions = new ArrayList<>(account.getTransactions());
        this.transactionTableViewController.setTransactions(transactions);
        this.transactionTableViewController.hideAccountIDColumn();

        accountBalance.setText("$" + account.getBalance());
        accountDescription.setText(account.getID() + ": " + account.getBankName());
        accountType.setText(account.getType());
    }

}