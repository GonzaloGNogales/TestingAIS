package es.codeurjc.testing;

import java.util.List;
import java.util.Arrays;

public class Request {
	// jUnit Tests Attributes
	private long idCustomer;
	private long idProduct;
	private long productCost;
	private int getProductCostValue;
	private int withdrawProductValue;
	private int reserveCreditValue;
	private int notifyValue;
	private int saveValue;
	private String message;

	// API Tests Attributes
	private int statusAPI;
	private String idAPI;
	private int idValue;
	private List<String> messagesAPI;

	// Constructors

	public Request() {

	}

	// Constructor with auto message
	public Request(long idCustomer, long idProduct, long productCost, int getProductCostValue, int withdrawProductValue,
			int reserveCreditValue, int notifyValue, int saveValue) {
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.productCost = productCost;
		this.getProductCostValue = getProductCostValue;
		this.withdrawProductValue = withdrawProductValue;
		this.reserveCreditValue = reserveCreditValue;
		this.notifyValue = notifyValue;
		this.saveValue = saveValue;
		this.message = "Purchase: customer=" + idCustomer + ", product=" + idProduct;
	}

	// Constructor with given message
	public Request(long idCustomer, long idProduct, long productCost, int getProductCostValue, int withdrawProductValue,
			int reserveCreditValue, int notifyValue, int saveValue, String message) {
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.productCost = productCost;
		this.getProductCostValue = getProductCostValue;
		this.withdrawProductValue = withdrawProductValue;
		this.reserveCreditValue = reserveCreditValue;
		this.notifyValue = notifyValue;
		this.saveValue = saveValue;
		this.message = message;
	}

	// API Request Constructor
	public Request(long idCustomer, long idProduct, int statusAPI, String idAPI, int idValue, String message,
			String messageAPI) {
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.statusAPI = statusAPI;
		this.idAPI = idAPI;
		this.idValue = idValue;
		this.messagesAPI = Arrays.asList(message, messageAPI);
	}

	// Getters and Setters

	public long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(long idProduct) {
		this.idProduct = idProduct;
	}

	public long getProductCost() {
		return productCost;
	}

	public void setProductCost(long productCost) {
		this.productCost = productCost;
	}

	public int getGetProductCostValue() {
		return getProductCostValue;
	}

	public void setGetProductCostValue(int getProductCostValue) {
		this.getProductCostValue = getProductCostValue;
	}

	public int getWithdrawProductValue() {
		return withdrawProductValue;
	}

	public void setWithdrawProductValue(int withdrawProductValue) {
		this.withdrawProductValue = withdrawProductValue;
	}

	public int getReserveCreditValue() {
		return reserveCreditValue;
	}

	public void setReserveCreditValue(int reserveCreditValue) {
		this.reserveCreditValue = reserveCreditValue;
	}

	public int getNotifyValue() {
		return notifyValue;
	}

	public void setNotifyValue(int notifyValue) {
		this.notifyValue = notifyValue;
	}

	public int getSaveValue() {
		return saveValue;
	}

	public void setSaveValue(int saveValue) {
		this.saveValue = saveValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusAPI() {
		return statusAPI;
	}

	public void setStatusAPI(int statusAPI) {
		this.statusAPI = statusAPI;
	}

	public String getIdAPI() {
		return idAPI;
	}

	public void setIdAPI(String idAPI) {
		this.idAPI = idAPI;
	}

	public int getIdValue() {
		return idValue;
	}

	public void setIdValue(int idValue) {
		this.idValue = idValue;
	}

	public List<String> getMessagesAPI() {
		return messagesAPI;
	}

	public void setMessagesAPI(List<String> messagesAPI) {
		this.messagesAPI = messagesAPI;
	}

}
