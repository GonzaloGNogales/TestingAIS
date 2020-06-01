package es.codeurjc.shop.rest.purchase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.purchase.Purchase;
import es.codeurjc.shop.domain.purchase.PurchaseNotFoundException;
import es.codeurjc.shop.domain.purchase.PurchaseService;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseRestController {

	@Autowired
	PurchaseService purchaseService;

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	List<GetPurchaseResponse> listPurchases() {
		return purchaseService.getPurchases().stream()
				.map(c -> new GetPurchaseResponse(c.getId(), c.getCustomerId(), c.getProductId()))
				.collect(Collectors.toList());
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CreatePurchaseResponse> newPurchase(@RequestBody CreatePurchaseRequest createPurchaseRequest)
			throws ShopException {

		try {

			Purchase purchase = purchaseService.createPurchase(createPurchaseRequest.getCustomerId(),
					createPurchaseRequest.getProductId());

			return new ResponseEntity<>(new CreatePurchaseResponse(purchase.getId()), HttpStatus.OK);

		} catch (ShopException e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e);
		}
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<GetPurchaseResponse> getPurchase(@PathVariable long id) {
		try {
			Purchase purchase = purchaseService.getPurchase(id);
			GetPurchaseResponse getPurchaseResponse = new GetPurchaseResponse(purchase.getId(),
					purchase.getCustomerId(), purchase.getProductId());
			return new ResponseEntity<>(getPurchaseResponse, HttpStatus.OK);
		} catch (PurchaseNotFoundException purchaseNotFoundException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
