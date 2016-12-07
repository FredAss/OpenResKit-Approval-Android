package Models;

import java.io.Serializable;

public class Settings implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6539769936338029802L;
	
	//Class that build the Settings
	
		// Properties
		private String URL;
		private String Serverport;
		private String Username;
		private String Password;
		
		
		//Getters and Setters
		
		public String getURL(){			
			return URL;			
		}
	
		public void setURL(String URL){
			this.URL = URL;
		}
		
		
		public String getServerport(){			
			return Serverport;			
		}
	
		public void setServerport(String Serverport){
			this.Serverport = Serverport;
		}
		
		
		public String getUsername(){			
			return Username;			
		}
	
		public void setUsername(String Username){
			this.Username = Username;
		}
		
		
		public String getPassword(){			
			return Password;			
		}
	
		public void setPassword(String Password){
			this.Password = Password;
		}
	
	

}
