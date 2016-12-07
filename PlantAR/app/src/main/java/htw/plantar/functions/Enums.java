package htw.plantar.functions;

public class Enums 
{
	public enum CategoryEnum
	{
		Nothing("Wähle Kategorie..."),
		Gas("Gasförmig"),
		Liquid("Flüssig"),
		Hard("Fest");
		
		private String m_Category;
		
		CategoryEnum(String category)
		{
			m_Category = category;
		}

		@Override
		public String toString() 
		{
			return m_Category;
		}
	}
	
	public enum TypeEnum
	{
		Nothing("Wähle Type..."),
		Input("Input"),
		Output("Output");
		
		private String m_Type;
		
		TypeEnum(String type)
		{
			m_Type = type;
		}
		
		@Override
		public String toString() 
		{
			return m_Type;
		}
	}
	
	public enum RiskEnum
	{
		Low("Niedrig"),
		Medium("Mittel"),
		High("Hoch");
		
		private String m_Risk;
		
		private RiskEnum(String risk) 
		{
			m_Risk = risk;
		}
		
		@Override
		public String toString() 
		{
			return m_Risk;
		}
	}
}
