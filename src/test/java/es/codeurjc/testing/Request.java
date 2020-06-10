package es.codeurjc.testing;

public class Request { // Request is an auxiliary class for tests

	// Attributes

	private long idCustomer;
	private long idProduct;
	private long productCost;
	private String message;

	// Constructors

	public Request() {

	}

	// Constructor for unit tests with doubles
	public Request(long idCustomer, long idProduct, long productCost) {
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.productCost = productCost;
		this.message = "Purchase: customer=" + idCustomer + ", product=" + idProduct;
	}

	// Constructor with given message for WEB UI System tests
	public Request(long idCustomer, long idProduct, String message) {
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
