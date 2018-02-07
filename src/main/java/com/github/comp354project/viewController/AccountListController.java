package com.github.comp354project.viewController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.comp354project.MyMoneyApplication;
import com.github.comp354project.service.account.Account;
import com.github.comp354project.service.account.IAccountService;
import com.github.comp354project.service.account.exceptions.AccountDoesNotExistException;
import com.github.comp354project.service.account.exceptions.AccountExistsException;
import com.github.comp354project.service.account.remote.GetRemoteAccountRequest;
import com.github.comp354project.service.account.remote.RemoteTransaction;
import com.github.comp354project.service.auth.SessionManager;
import com.github.comp354project.service.exceptions.ValidationException;
import com.github.comp354project.viewController.helper.AlertHelper;
import com.github.comp354project.viewController.helper.StageManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.Data;

import javax.inject.Inject;


public class AccountListController implements Initializable {

	@FXML private Label usernameLbl;
	@FXML private TextField accountIdTxt;
	@FXML private TableView<AccountDisplayModel> accountsTable;
	@FXML private TableColumn<AccountDisplayModel, Integer>  idCol;
	@FXML private TableColumn<AccountDisplayModel, String>  bankNameCol;
	@FXML private TableColumn<AccountDisplayModel, String>  typeCol;
	@FXML private TableColumn<AccountDisplayModel, Double>  balanceCol;

	private List<Account> accounts = new ArrayList<>();
	private ObservableList<AccountDisplayModel> tableData = FXCollections.observableArrayList();

	@Inject
	SessionManager sessionManager;

	@Inject
	IAccountService accountService;

	public AccountListController() {
		MyMoneyApplication.application.getComponent().inject(this);
	}

	public void initialize(URL url, ResourceBundle rb) {
		idCol.setCellValueFactory(new PropertyValueFactory<AccountDisplayModel,Integer>("id"));
		bankNameCol.setCellValueFactory(new PropertyValueFactory<AccountDisplayModel,String>("bankName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<AccountDisplayModel,String>("type"));
		balanceCol.setCellValueFactory(new PropertyValueFactory<AccountDisplayModel,Double>("balance"));
	}

	public void setAccounts(List<Account> accounts){
		List<AccountDisplayModel> tableData = new ArrayList<>();
		for(Account account : accounts){
			tableData.add(new AccountDisplayModel(account));
		}
	}

	@FXML
	public void selectAccount(MouseEvent event) throws IOException {
	    if (event.getClickCount() == 2) {
            MyMoneyApplication.application.displayAccountDetails(accounts.get(accountsTable.getSelectionModel().getSelectedIndex()));
	    }
	}

	@FXML
	public void viewAllAccounts(ActionEvent event) throws IOException {
        StageManager.switchToAccount("Account");
	}

	@FXML
    public void logout(ActionEvent event) throws IOException {
		this.sessionManager.logout();
        StageManager.switchToLogin();
    }

	@FXML
	public void addAccount() {
		try {
			GetRemoteAccountRequest remoteAccountRequest = GetRemoteAccountRequest.builder()
					.accountID(this.accountIdFromString())
					.build();
			this.accountService.addAccount(remoteAccountRequest, this.sessionManager.getUser());
		} catch (ValidationException e) {
			AlertHelper.generateErrorAlert("Creation error", "Error validating account", e)
					.showAndWait();
		} catch(RuntimeException e) {
			AlertHelper.generateErrorAlert("Creation error", "Error validating account", e.getMessage())
					.showAndWait();
		}
	}

    @Data
	private static class AccountDisplayModel {
		private final SimpleIntegerProperty id;
		private final SimpleStringProperty bankName;
		private final SimpleStringProperty type;
		private final SimpleDoubleProperty balance;

		public AccountDisplayModel(Account account) {
			this.id = new SimpleIntegerProperty(account.getID());
			this.bankName = new SimpleStringProperty(account.getBankName());
			this.type = new SimpleStringProperty(account.getType());
			this.balance = new SimpleDoubleProperty(account.getBalance());
		}
	}

	private Integer accountIdFromString() throws RuntimeException {
		try {
			return Integer.parseInt(this.accountIdTxt.getText());
		} catch(NumberFormatException e) {
			throw new RuntimeException("Account ID is not a number");
		}
	}
}