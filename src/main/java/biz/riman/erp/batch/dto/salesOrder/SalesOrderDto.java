package biz.riman.erp.batch.dto.salesOrder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SalesOrderDto {
    @JsonProperty(value = "SalesOrderType")
	private String SalesOrderType;
    @JsonProperty(value = "SalesOrganization")
	private String SalesOrganization;
    @JsonProperty(value = "OrganizationDivision")
	private String OrganizationDivision;
    @JsonProperty(value = "DistributionChannel")
	private String DistributionChannel;
    @JsonProperty(value = "SoldToParty")
	private String SoldToParty;
    @JsonProperty(value = "PurchaseOrderByCustomer")
	private String PurchaseOrderByCustomer;
    @JsonProperty(value = "RequestedDeliveryDate")
	private String RequestedDeliveryDate;
    @JsonProperty(value = "CustomerPurchaseOrderDate")
	private String CustomerPurchaseOrderDate;
    @JsonProperty(value = "SalesOrderDate")
	private String SalesOrderDate;
    @JsonProperty(value = "to_PricingElement")
	private List<PricingElementDto> to_PricingElement;
    @JsonProperty(value = "to_Item")
	private List<ItemDto> to_Item;
    @JsonProperty(value = "to_Partner")
	private List<PartnerDto> to_Partner;
	
	public SalesOrderDto(String salesOrderType, String salesOrganization, String organizationDivision,
			String distributionChannel, String soldToParty, String purchaseOrderByCustomer,
			String requestedDeliveryDate, String customerPurchaseOrderDate, String salesOrderDate,
			List<PricingElementDto> to_PricingElement, List<ItemDto> to_Item, List<PartnerDto> to_Partner) {
		super();
		this.SalesOrderType = salesOrderType;
		this.SalesOrganization = salesOrganization;
		this.OrganizationDivision = organizationDivision;
		this.DistributionChannel = distributionChannel;
		this.SoldToParty = soldToParty;
		this.PurchaseOrderByCustomer = purchaseOrderByCustomer;
		this.RequestedDeliveryDate = requestedDeliveryDate;
		this.CustomerPurchaseOrderDate = customerPurchaseOrderDate;
		this.SalesOrderDate = salesOrderDate;
		this.to_PricingElement = to_PricingElement;
		this.to_Item = to_Item;
		this.to_Partner = to_Partner;
	}
}
