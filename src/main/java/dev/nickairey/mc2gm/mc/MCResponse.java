package dev.nickairey.mc2gm.mc;

import java.util.List;

public class MCResponse {

	public List<EmailInfo> members;
	
	public Integer total_items;
	
	public static class EmailInfo {

		public String email_address;

	}
}
