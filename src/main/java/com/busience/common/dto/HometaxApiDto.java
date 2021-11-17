package com.busience.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HometaxApiDto {
	private String tax_Invoice_Type; 
	private String tax_Invoice_Date; 
	private String supplier_Registration_No; 
	private String supplier_Registration_No_2; 
	private String supplier_Company_Name; 
	private String supplier_Name; 
	private String supplier_Business_Address; 
	private String supplier_Business_Type; 
	private String supplier_Business_Item; 
	private String supplier_Email; 
	private String receiver_Registration_No; 
	private String receiver_Registration_No_2; 
	private String receiver_Company_Name; 
	private String receiver_Name; 
	private String receiver_Business_Address; 
	private String receiver_Business_Type; 
	private String receiver_Business_Item; 
	private String receiver_Email_1; 
	private String receiver_Email_2; 
	private int supply_Value; 
	private int tax_Amount; 
	private String remarks; 
	private String date_1; 
	private String item_1; 
	private String standard_1; 
	private int quantity_1; 
	private int unit_Price_1; 
	private int supply_Value_1; 
	private int tax_Amount_1; 
	private String item_Remarks_1; 
	private String date_2; 
	private String item_2; 
	private String standard_2; 
	private int quantity_2;
	private int unit_Price_2; 
	private int supply_Value_2; 
	private int tax_Amount_2; 
	private String item_Remarks_2; 
	private String date_3; 
	private String item_3;
	private String standard_3; 
	private int quantity_3; 
	private int unit_Price_3; 
	private int supply_Value_3; 
	private int tax_Amount_3; 
	private String item_Remarks_3; 
	private String date_4; 
	private String item_4; 
	private String standard_4; 
	private int quantity_4; 
	private int unit_Price_4; 
	private int supply_Value_4; 
	private int tax_Amount_4; 
	private String item_Remarks_4; 
	private int cash; 
	private int accounts_Check; 
	private int note; 
	private int accounts_Receivable; 
	private String classification;
}
